package com.appvalence.hayatkurtar.data.mesh.router

import com.appvalence.hayatkurtar.core.logging.MeshLog
import com.appvalence.hayatkurtar.core.logging.MeshLogTags
import com.appvalence.hayatkurtar.core.protocol.Frame
import com.appvalence.hayatkurtar.core.protocol.FrameType
import com.appvalence.hayatkurtar.core.protocol.Priority
import com.appvalence.hayatkurtar.core.result.MeshException
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.core.result.toError
import com.appvalence.hayatkurtar.core.result.toSuccess
import com.appvalence.hayatkurtar.data.mesh.store.RoomMessageStore
import com.appvalence.hayatkurtar.domain.mesh.*
import com.appvalence.hayatkurtar.domain.transport.Peer
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Default implementation of MeshRouter with controlled flood + TTL + dedup + ACK + retry
 */
@Singleton
class DefaultMeshRouter @Inject constructor(
    private val transportMultiplexer: TransportMultiplexer,
    private val messageStore: RoomMessageStore,
    private val deviceIdProvider: DeviceIdProvider
) : MeshRouter {

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val eventChannel = Channel<MeshEvent>(Channel.UNLIMITED)
    private val routingTable = mutableMapOf<Long, RouteInfo>()
    private val routingMutex = Mutex()
    
    // Statistics
    private val stats = MeshStatsTracker()
    
    // Retry management
    private val retryJobs = mutableMapOf<MessageId, Job>()
    private val retryMutex = Mutex()
    
    private var isStarted = false

    override val events: Flow<MeshEvent> = eventChannel.receiveAsFlow()

    override suspend fun start() {
        if (isStarted) return
        
        MeshLog.i(MeshLogTags.MESH_ROUTER, "Starting mesh router")
        
        // Start transport multiplexer
        transportMultiplexer.start()
        
        // Listen to transport events
        scope.launch {
            transportMultiplexer.transportEvents.collect { event ->
                handleTransportEvent(event)
            }
        }
        
        // Start periodic tasks
        startPeriodicTasks()
        
        isStarted = true
        
        eventChannel.trySend(MeshEvent.NetworkStateChanged(0, 0))
        MeshLog.i(MeshLogTags.MESH_ROUTER, "Mesh router started successfully")
    }

    override suspend fun stop() {
        if (!isStarted) return
        
        MeshLog.i(MeshLogTags.MESH_ROUTER, "Stopping mesh router")
        
        // Cancel all retry jobs
        retryMutex.withLock {
            retryJobs.values.forEach { it.cancel() }
            retryJobs.clear()
        }
        
        // Stop transport multiplexer
        transportMultiplexer.stop()
        
        // Cancel scope
        scope.cancel()
        
        isStarted = false
        MeshLog.i(MeshLogTags.MESH_ROUTER, "Mesh router stopped")
    }

    override suspend fun sendMessage(
        content: ByteArray,
        priority: Priority,
        ttl: Byte
    ): MeshResult<MessageId> {
        if (!isStarted) {
            return MeshException.Routing.RouteNotFound("Router not started").toError()
        }

        val messageId = MessageId.generate()
        val message = MeshMessage(
            id = messageId,
            originId = deviceIdProvider.getDeviceId(),
            content = content,
            priority = priority,
            ttl = ttl
        )

        MeshLog.i(MeshLogTags.MESH_ROUTER, "Sending message: $messageId, priority: $priority, ttl: $ttl")

        // Store for tracking and potential retry
        messageStore.storeOutgoingMessage(message, targetPeer = null)

        // Create and send frame
        val frame = message.toFrame(FrameType.CHAT)
        val result = transportMultiplexer.broadcast(frame)

        if (result.isSuccess()) {
            stats.incrementMessagesSent()
            scheduleRetryIfNeeded(message)
            eventChannel.trySend(MeshEvent.MessageDelivered(messageId, createBroadcastPeer()))
        } else {
            stats.incrementMessagesDropped()
            eventChannel.trySend(MeshEvent.MessageDropped(messageId, "Broadcast failed: ${result.errorOrNull()?.message}"))
        }

        return messageId.toSuccess()
    }

    override suspend fun handleIncomingFrame(frame: Frame, fromPeer: Peer) {
        MeshLog.d(MeshLogTags.MESH_ROUTER, "Handling incoming frame: ${frame.type} from ${fromPeer.id}")

        when (frame.type) {
            FrameType.CHAT -> handleChatFrame(frame, fromPeer)
            FrameType.ACK -> handleAckFrame(frame, fromPeer)
            FrameType.PING -> handlePingFrame(frame, fromPeer)
            FrameType.PONG -> handlePongFrame(frame, fromPeer)
            FrameType.HELLO -> handleHelloFrame(frame, fromPeer)
            FrameType.KEY_EXCHANGE -> handleKeyExchangeFrame(frame, fromPeer)
        }
    }

    override suspend fun acknowledgeMessage(messageId: MessageId, toPeer: Peer): MeshResult<Unit> {
        val ackFrame = Frame(
            type = FrameType.ACK,
            messageId = messageId.value,
            originId = deviceIdProvider.getDeviceId(),
            ttl = 1, // ACKs don't need to be forwarded far
            priority = Priority.HIGH, // ACKs are high priority
            timestamp = System.currentTimeMillis(),
            payload = ByteArray(0)
        )

        return transportMultiplexer.sendToPeer(toPeer, ackFrame)
    }

    override fun getStats(): MeshStats = stats.getStats()

    override fun getKnownRoutes(): Map<Long, RouteInfo> = routingTable.toMap()

    private suspend fun handleTransportEvent(event: com.appvalence.hayatkurtar.domain.transport.TransportEvent) {
        when (event) {
            is com.appvalence.hayatkurtar.domain.transport.TransportEvent.FrameReceived -> {
                handleIncomingFrame(event.frame, event.fromPeer)
            }
            is com.appvalence.hayatkurtar.domain.transport.TransportEvent.Connected -> {
                updateNetworkState()
                sendHelloFrame(event.link.peer)
            }
            is com.appvalence.hayatkurtar.domain.transport.TransportEvent.Disconnected -> {
                removeRoutesThroughPeer(event.peerId)
                updateNetworkState()
            }
            is com.appvalence.hayatkurtar.domain.transport.TransportEvent.PeerDiscovered -> {
                // Peer discovery is handled by transport layer
            }
            is com.appvalence.hayatkurtar.domain.transport.TransportEvent.PeerLost -> {
                removeRoutesThroughPeer(event.peerId)
            }
            is com.appvalence.hayatkurtar.domain.transport.TransportEvent.Error -> {
                MeshLog.w(MeshLogTags.MESH_ROUTER, "Transport error: ${event.error.message}")
            }
            else -> {
                // Handle other transport events
            }
        }
    }

    private suspend fun handleChatFrame(frame: Frame, fromPeer: Peer) {
        val messageId = MessageId(frame.messageId)
        
        // Check for duplicates
        if (messageStore.hasSeenMessage(messageId)) {
            MeshLog.d(MeshLogTags.MESH_ROUTER, "Dropping duplicate message: $messageId")
            stats.incrementDuplicatesBlocked()
            return
        }

        // Mark as seen
        messageStore.markMessageSeen(messageId)
        stats.incrementMessagesReceived()

        // Update routing table
        updateRoute(frame.originId, fromPeer, frame.hopCount.toInt())

        // Create mesh message
        val message = MeshMessage(
            id = messageId,
            originId = frame.originId,
            content = frame.payload,
            priority = frame.priority,
            ttl = frame.ttl,
            hopCount = frame.hopCount,
            timestamp = frame.timestamp
        )

        // Send ACK back to sender
        acknowledgeMessage(messageId, fromPeer)

        // Forward if TTL allows
        if (message.canForward()) {
            val forwardedMessage = message.forward()
            if (forwardedMessage != null) {
                forwardMessage(forwardedMessage, excludePeer = fromPeer)
            }
        } else if (frame.ttl <= 0) {
            MeshLog.d(MeshLogTags.MESH_ROUTER, "Message TTL expired: $messageId")
            eventChannel.trySend(MeshEvent.MessageExpired(messageId))
        }

        // Emit event for UI
        eventChannel.trySend(MeshEvent.MessageReceived(message, fromPeer))
    }

    private suspend fun handleAckFrame(frame: Frame, fromPeer: Peer) {
        val messageId = MessageId(frame.messageId)
        
        // Mark message as acknowledged
        messageStore.markMessageAcknowledged(messageId)
        
        // Cancel retry job
        retryMutex.withLock {
            retryJobs[messageId]?.cancel()
            retryJobs.remove(messageId)
        }
        
        eventChannel.trySend(MeshEvent.MessageAcknowledged(messageId, fromPeer))
        MeshLog.d(MeshLogTags.MESH_ROUTER, "Received ACK for message: $messageId from ${fromPeer.id}")
    }

    private suspend fun handlePingFrame(frame: Frame, fromPeer: Peer) {
        // Respond with PONG
        val pongFrame = Frame(
            type = FrameType.PONG,
            messageId = frame.messageId,
            originId = deviceIdProvider.getDeviceId(),
            ttl = 1,
            timestamp = System.currentTimeMillis(),
            payload = ByteArray(0)
        )
        
        transportMultiplexer.sendToPeer(fromPeer, pongFrame)
    }

    private suspend fun handlePongFrame(frame: Frame, fromPeer: Peer) {
        // Update link quality or connectivity info
        MeshLog.d(MeshLogTags.MESH_ROUTER, "Received PONG from ${fromPeer.id}")
    }

    private suspend fun handleHelloFrame(frame: Frame, fromPeer: Peer) {
        // Update routing information
        updateRoute(frame.originId, fromPeer, 1)
        
        // Send HELLO back if we haven't sent one recently
        sendHelloFrame(fromPeer)
    }

    private suspend fun handleKeyExchangeFrame(frame: Frame, fromPeer: Peer) {
        // Handle cryptographic key exchange
        MeshLog.d(MeshLogTags.MESH_ROUTER, "Received key exchange from ${fromPeer.id}")
        // Implementation would handle crypto session establishment
    }

    private suspend fun forwardMessage(message: MeshMessage, excludePeer: Peer) {
        val forwardFrame = message.toFrame(FrameType.CHAT)
        val connectedPeers = transportMultiplexer.getConnectedPeers().filter { it.id != excludePeer.id }
        
        if (connectedPeers.isEmpty()) {
            MeshLog.w(MeshLogTags.MESH_ROUTER, "No peers to forward message to")
            return
        }

        var forwardedCount = 0
        for (peer in connectedPeers) {
            val result = transportMultiplexer.sendToPeer(peer, forwardFrame)
            if (result.isSuccess()) {
                forwardedCount++
                eventChannel.trySend(MeshEvent.MessageForwarded(message.id, peer, message.hopCount.toInt()))
            }
        }

        if (forwardedCount > 0) {
            stats.incrementMessagesForwarded()
            MeshLog.d(MeshLogTags.MESH_ROUTER, "Forwarded message ${message.id} to $forwardedCount peers")
        }
    }

    private suspend fun updateRoute(originId: Long, viaPeer: Peer, hopCount: Int) = routingMutex.withLock {
        val existing = routingTable[originId]
        
        if (existing == null || hopCount < existing.hopCount || !existing.isFresh()) {
            val newRoute = RouteInfo(
                targetId = originId,
                nextHop = viaPeer,
                hopCount = hopCount,
                lastUpdated = System.currentTimeMillis()
            )
            
            routingTable[originId] = newRoute
            eventChannel.trySend(MeshEvent.RouteDiscovered(originId, viaPeer, hopCount))
            
            MeshLog.d(MeshLogTags.MESH_ROUTER, "Updated route to $originId via ${viaPeer.id} (${hopCount} hops)")
        }
    }

    private suspend fun removeRoutesThroughPeer(peerId: String) = routingMutex.withLock {
        val routesToRemove = routingTable.filter { it.value.nextHop.id == peerId }
        
        routesToRemove.forEach { (targetId, route) ->
            routingTable.remove(targetId)
            eventChannel.trySend(MeshEvent.RouteLost(targetId, route.nextHop))
        }
        
        if (routesToRemove.isNotEmpty()) {
            MeshLog.d(MeshLogTags.MESH_ROUTER, "Removed ${routesToRemove.size} routes through peer $peerId")
        }
    }

    private suspend fun sendHelloFrame(peer: Peer) {
        val helloFrame = Frame(
            type = FrameType.HELLO,
            messageId = MessageId.generate().value,
            originId = deviceIdProvider.getDeviceId(),
            ttl = 1,
            timestamp = System.currentTimeMillis(),
            payload = "HELLO".toByteArray()
        )
        
        transportMultiplexer.sendToPeer(peer, helloFrame)
    }

    private suspend fun scheduleRetryIfNeeded(message: MeshMessage) {
        if (message.priority == Priority.HIGH) {
            // High priority messages get retry logic
            val retryJob = scope.launch {
                retryMessage(message)
            }
            
            retryMutex.withLock {
                retryJobs[message.id] = retryJob
            }
        }
    }

    private suspend fun retryMessage(message: MeshMessage, attempt: Int = 1) {
        if (attempt > 3) {
            MeshLog.w(MeshLogTags.MESH_ROUTER, "Max retry attempts reached for message: ${message.id}")
            eventChannel.trySend(MeshEvent.MessageDropped(message.id, "Max retries exceeded"))
            return
        }

        // Exponential backoff: 500ms -> 2s -> 8s
        val delayMs = (500L * (1 shl (attempt - 1))).coerceAtMost(8000L)
        delay(delayMs)

        // Check if message was acknowledged while we were waiting
        val pendingAcks = messageStore.getPendingAcknowledgments()
        if (pendingAcks.none { it.id == message.id }) {
            return // Message was acknowledged
        }

        // Retry sending
        val frame = message.toFrame(FrameType.CHAT)
        val result = transportMultiplexer.broadcast(frame)
        
        if (result.isSuccess()) {
            MeshLog.d(MeshLogTags.MESH_ROUTER, "Retried message ${message.id} (attempt $attempt)")
            messageStore.updateRetryInfo(message.id, attempt)
            
            // Schedule next retry
            scope.launch {
                retryMessage(message, attempt + 1)
            }
        } else {
            eventChannel.trySend(MeshEvent.MessageDropped(message.id, "Retry failed: ${result.errorOrNull()?.message}"))
        }
    }

    private fun startPeriodicTasks() {
        // Cleanup task
        scope.launch {
            while (isActive) {
                delay(60_000) // Every minute
                messageStore.cleanup()
            }
        }
        
        // Stats update task
        scope.launch {
            while (isActive) {
                delay(5_000) // Every 5 seconds
                updateNetworkState()
            }
        }
    }

    private suspend fun updateNetworkState() {
        val connectedPeers = transportMultiplexer.getConnectedPeers().size
        val knownRoutes = routingTable.size
        
        eventChannel.trySend(MeshEvent.NetworkStateChanged(connectedPeers, knownRoutes))
    }

    private fun createBroadcastPeer(): Peer = Peer(
        id = "broadcast",
        name = "Broadcast",
        transport = com.appvalence.hayatkurtar.domain.transport.TransportType.UNKNOWN
    )
}

/**
 * Thread-safe statistics tracker
 */
private class MeshStatsTracker {
    private val messagesReceived = AtomicLong(0)
    private val messagesSent = AtomicLong(0)
    private val messagesForwarded = AtomicLong(0)
    private val messagesDropped = AtomicLong(0)
    private val duplicatesBlocked = AtomicLong(0)
    
    fun incrementMessagesReceived() = messagesReceived.incrementAndGet()
    fun incrementMessagesSent() = messagesSent.incrementAndGet()
    fun incrementMessagesForwarded() = messagesForwarded.incrementAndGet()
    fun incrementMessagesDropped() = messagesDropped.incrementAndGet()
    fun incrementDuplicatesBlocked() = duplicatesBlocked.incrementAndGet()
    
    fun getStats(): MeshStats = MeshStats(
        messagesReceived = messagesReceived.get(),
        messagesSent = messagesSent.get(),
        messagesForwarded = messagesForwarded.get(),
        messagesDropped = messagesDropped.get(),
        duplicatesBlocked = duplicatesBlocked.get()
    )
}

/**
 * Provides unique device identifier
 */
interface DeviceIdProvider {
    fun getDeviceId(): Long
}

/**
 * Implementation that generates device ID from device characteristics
 */
@Singleton
class AndroidDeviceIdProvider @Inject constructor() : DeviceIdProvider {
    override fun getDeviceId(): Long {
        // Generate stable device ID (could use Android ID, MAC address hash, etc.)
        return android.provider.Settings.Secure.ANDROID_ID.hashCode().toLong()
    }
}