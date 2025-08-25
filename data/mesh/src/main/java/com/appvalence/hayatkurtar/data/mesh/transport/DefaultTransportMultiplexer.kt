package com.appvalence.hayatkurtar.data.mesh.transport

import com.appvalence.hayatkurtar.core.logging.MeshLog
import com.appvalence.hayatkurtar.core.logging.MeshLogTags
import com.appvalence.hayatkurtar.core.protocol.Frame
import com.appvalence.hayatkurtar.core.result.MeshException
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.core.result.toError
import com.appvalence.hayatkurtar.core.result.toSuccess
import com.appvalence.hayatkurtar.domain.transport.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Default implementation of TransportMultiplexer that manages multiple transport strategies
 */
@Singleton
class DefaultTransportMultiplexer @Inject constructor() : TransportMultiplexer {

    private val strategies = ConcurrentHashMap<TransportType, TransportStrategy>()
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // Event aggregation
    private val eventChannel = Channel<TransportEvent>(Channel.UNLIMITED)
    private val peerChannel = Channel<Peer>(Channel.UNLIMITED)
    
    // Statistics tracking
    private val statsMap = ConcurrentHashMap<TransportType, MutableTransportStats>()
    
    // Configuration
    private var config = TransportConfig()
    private val configMutex = Mutex()
    
    // Connection management
    private val allPeers = ConcurrentHashMap<String, Peer>()
    private val connectionPreferences = ConcurrentHashMap<String, TransportType>()
    
    private var isStarted = false

    override val transportEvents: Flow<TransportEvent> = eventChannel.receiveAsFlow()

    override fun addTransport(strategy: TransportStrategy) {
        if (strategies.containsKey(strategy.transportType)) {
            MeshLog.w(MeshLogTags.TRANSPORT, "Transport ${strategy.transportType} already added, replacing")
        }
        
        strategies[strategy.transportType] = strategy
        statsMap[strategy.transportType] = MutableTransportStats(strategy.transportType)
        
        MeshLog.i(MeshLogTags.TRANSPORT, "Added transport strategy: ${strategy.name}")
        
        // If already started, start this strategy too
        if (isStarted) {
            scope.launch {
                startStrategy(strategy)
            }
        }
    }

    override fun removeTransport(transportType: TransportType) {
        val strategy = strategies.remove(transportType)
        if (strategy != null) {
            scope.launch {
                strategy.stop()
            }
            statsMap.remove(transportType)
            MeshLog.i(MeshLogTags.TRANSPORT, "Removed transport strategy: ${strategy.name}")
        }
    }

    override suspend fun start() {
        if (isStarted) return
        
        MeshLog.i(MeshLogTags.TRANSPORT, "Starting transport multiplexer with ${strategies.size} strategies")
        
        // Start all available strategies
        strategies.values.forEach { strategy ->
            scope.launch {
                startStrategy(strategy)
            }
        }
        
        // Start periodic tasks
        startPeriodicTasks()
        
        isStarted = true
    }

    override suspend fun stop() {
        if (!isStarted) return
        
        MeshLog.i(MeshLogTags.TRANSPORT, "Stopping transport multiplexer")
        
        // Stop all strategies
        strategies.values.forEach { strategy ->
            scope.launch {
                strategy.stop()
            }
        }
        
        // Cancel scope
        scope.cancel()
        
        // Clear state
        allPeers.clear()
        connectionPreferences.clear()
        
        isStarted = false
    }

    override fun discoverPeers(): Flow<Peer> {
        if (!isStarted) {
            return flowOf()
        }
        
        // Start discovery on all available strategies
        strategies.values.forEach { strategy ->
            if (strategy.isAvailable) {
                scope.launch {
                    try {
                        strategy.discoverPeers().collect { peer ->
                            handlePeerDiscovered(peer, strategy.transportType)
                        }
                    } catch (e: Exception) {
                        MeshLog.e(MeshLogTags.TRANSPORT, "Error in peer discovery for ${strategy.name}", e)
                    }
                }
            }
        }
        
        return peerChannel.receiveAsFlow()
    }

    override suspend fun startDiscovery(): MeshResult<Unit> {
        if (!isStarted) {
            start()
        }
        
        MeshLog.i(MeshLogTags.TRANSPORT, "Starting discovery on all available transports")
        
        var successCount = 0
        var lastError: Exception? = null
        
        strategies.values.forEach { strategy ->
            if (strategy.isAvailable) {
                scope.launch {
                    try {
                        strategy.discoverPeers().collect { peer ->
                            handlePeerDiscovered(peer, strategy.transportType)
                        }
                        successCount++
                    } catch (e: Exception) {
                        lastError = e
                        MeshLog.e(MeshLogTags.TRANSPORT, "Failed to start discovery on ${strategy.name}", e)
                    }
                }
            }
        }
        
        return if (successCount > 0 || strategies.isEmpty()) {
            Unit.toSuccess()
        } else {
            MeshException.Transport.TransportNotSupported("Discovery failed on all transports: ${lastError?.message}").toError()
        }
    }

    override suspend fun stopDiscovery(): MeshResult<Unit> {
        MeshLog.i(MeshLogTags.TRANSPORT, "Stopping discovery on all transports")
        
        // For now, this stops all transport strategies
        // In a more advanced implementation, we would only stop discovery
        // while keeping existing connections alive
        
        strategies.values.forEach { strategy ->
            scope.launch {
                try {
                    // Individual strategies would have a stopDiscovery method
                    // For now, we just log it
                    MeshLog.d(MeshLogTags.TRANSPORT, "Stopping discovery on ${strategy.name}")
                } catch (e: Exception) {
                    MeshLog.e(MeshLogTags.TRANSPORT, "Error stopping discovery on ${strategy.name}", e)
                }
            }
        }
        
        return Unit.toSuccess()
    }

    override suspend fun connectToPeer(peer: Peer): MeshResult<Link> {
        val preferredTransport = connectionPreferences[peer.id] ?: peer.transport
        
        // Try preferred transport first
        val preferredStrategy = strategies[preferredTransport]
        if (preferredStrategy?.isAvailable == true) {
            val result = preferredStrategy.connectTo(peer)
            if (result.isSuccess()) {
                updateStats(preferredTransport) { it.successfulConnections.incrementAndGet() }
                return result
            }
        }
        
        // Try other available transports
        for ((transportType, strategy) in strategies) {
            if (transportType != preferredTransport && strategy.isAvailable) {
                updateStats(transportType) { it.connectionAttempts.incrementAndGet() }
                
                val result = strategy.connectTo(peer.copy(transport = transportType))
                if (result.isSuccess()) {
                    updateStats(transportType) { it.successfulConnections.incrementAndGet() }
                    connectionPreferences[peer.id] = transportType
                    return result
                }
            }
        }
        
        return MeshException.Transport.ConnectionFailed(peer.id, Exception("No transport available")).toError()
    }

    override suspend fun broadcast(frame: Frame): MeshResult<Unit> {
        val availableStrategies = strategies.values.filter { it.isAvailable }
        
        if (availableStrategies.isEmpty()) {
            return MeshException.Transport.NoTransportAvailable().toError()
        }
        
        var successCount = 0
        var lastError: Exception? = null
        
        // Broadcast on all available transports for maximum reach
        for (strategy in availableStrategies) {
            try {
                val result = strategy.broadcast(frame)
                if (result.isSuccess()) {
                    successCount++
                    updateStats(strategy.transportType) { it.messagesSent.incrementAndGet() }
                } else {
                    lastError = result.errorOrNull()
                }
            } catch (e: Exception) {
                lastError = e
                MeshLog.w(MeshLogTags.TRANSPORT, "Broadcast failed on ${strategy.name}", e)
            }
        }
        
        return if (successCount > 0) {
            Unit.toSuccess()
        } else {
            MeshException.Transport.SendFailed("All broadcasts failed", lastError).toError()
        }
    }

    override suspend fun sendToPeer(peer: Peer, frame: Frame): MeshResult<Unit> {
        // Choose best transport for this peer
        val transportType = chooseBestTransport(peer, frame)
        val strategy = strategies[transportType]
            ?: return MeshException.Transport.TransportNotSupported(transportType.name).toError()
        
        if (!strategy.isAvailable) {
            return MeshException.Transport.NoTransportAvailable().toError()
        }
        
        updateStats(transportType) { it.messagesSent.incrementAndGet() }
        return strategy.sendTo(peer, frame)
    }

    override fun getConnectedPeers(): List<Peer> {
        return strategies.values
            .filter { it.isAvailable }
            .flatMap { it.getConnectedPeers() }
            .distinctBy { it.id }
    }

    override fun getConfig(): TransportConfig = config

    override suspend fun updateConfig(config: TransportConfig) = configMutex.withLock {
        this.config = config
        MeshLog.i(MeshLogTags.TRANSPORT, "Updated transport configuration")
    }

    override fun getTransportStats(): Map<TransportType, TransportStats> {
        return statsMap.mapValues { (transportType, mutableStats) ->
            val strategy = strategies[transportType]
            TransportStats(
                transportType = transportType,
                isActive = strategy?.isAvailable == true,
                connectedPeers = strategy?.getConnectedPeers()?.size ?: 0,
                discoveredPeers = allPeers.values.count { it.transport == transportType },
                messagesSent = mutableStats.messagesSent.get(),
                messagesReceived = mutableStats.messagesReceived.get(),
                connectionAttempts = mutableStats.connectionAttempts.get(),
                successfulConnections = mutableStats.successfulConnections.get(),
                averageLatencyMs = mutableStats.averageLatencyMs.get().toDouble(),
                lastActivityTime = mutableStats.lastActivityTime.get()
            )
        }
    }

    private suspend fun startStrategy(strategy: TransportStrategy) {
        if (!strategy.isAvailable) {
            MeshLog.w(MeshLogTags.TRANSPORT, "Transport ${strategy.name} not available, skipping")
            return
        }
        
        MeshLog.i(MeshLogTags.TRANSPORT, "Starting transport strategy: ${strategy.name}")
        
        try {
            strategy.start(scope).collect { event ->
                handleTransportEvent(event, strategy.transportType)
            }
        } catch (e: Exception) {
            MeshLog.e(MeshLogTags.TRANSPORT, "Error in transport strategy ${strategy.name}", e)
            eventChannel.trySend(TransportEvent.Error(e))
        }
    }

    private suspend fun handleTransportEvent(event: TransportEvent, transportType: TransportType) {
        when (event) {
            is TransportEvent.PeerDiscovered -> {
                handlePeerDiscovered(event.peer, transportType)
            }
            is TransportEvent.FrameReceived -> {
                updateStats(transportType) { 
                    it.messagesReceived.incrementAndGet()
                    it.lastActivityTime.set(System.currentTimeMillis())
                }
            }
            is TransportEvent.Connected -> {
                allPeers[event.link.peer.id] = event.link.peer
                connectionPreferences[event.link.peer.id] = transportType
            }
            is TransportEvent.Disconnected -> {
                // Don't remove from allPeers as peer might be reachable via other transport
                connectionPreferences.remove(event.peerId)
            }
            else -> {
                // Forward other events as-is
            }
        }
        
        // Forward event to subscribers
        eventChannel.trySend(event)
    }

    private suspend fun handlePeerDiscovered(peer: Peer, transportType: TransportType) {
        val updatedPeer = peer.copy(transport = transportType)
        val existingPeer = allPeers[peer.id]
        
        // Update or add peer
        if (existingPeer == null) {
            allPeers[peer.id] = updatedPeer
            peerChannel.trySend(updatedPeer)
            updateStats(transportType) { it.discoveredPeers.incrementAndGet() }
        } else {
            // Update with better transport if available
            val updatedPeerWithBetterTransport = chooseBetterPeer(existingPeer, updatedPeer)
            if (updatedPeerWithBetterTransport != existingPeer) {
                allPeers[peer.id] = updatedPeerWithBetterTransport
                peerChannel.trySend(updatedPeerWithBetterTransport)
            }
        }
    }

    private fun chooseBetterPeer(existing: Peer, new: Peer): Peer {
        // Prefer Wi-Fi Direct over Bluetooth for better bandwidth
        return when {
            new.transport == TransportType.WIFI_DIRECT && existing.transport == TransportType.BLUETOOTH_CLASSIC -> new
            new.linkQuality.ordinal > existing.linkQuality.ordinal -> new
            new.lastSeen > existing.lastSeen -> new
            else -> existing
        }
    }

    private fun chooseBestTransport(peer: Peer, frame: Frame): TransportType {
        val preferredTransport = connectionPreferences[peer.id] ?: peer.transport
        
        // Use preferred transport if available
        if (strategies[preferredTransport]?.isAvailable == true) {
            return preferredTransport
        }
        
        // Smart transport selection based on frame characteristics
        return when {
            frame.payload.size > 100 -> {
                // Large frames prefer Wi-Fi Direct for better bandwidth
                if (strategies[TransportType.WIFI_DIRECT]?.isAvailable == true) {
                    TransportType.WIFI_DIRECT
                } else {
                    TransportType.BLUETOOTH_CLASSIC
                }
            }
            frame.priority.ordinal >= 2 -> {
                // High priority frames use most reliable transport
                connectionPreferences[peer.id] ?: peer.transport
            }
            else -> {
                // Default to peer's native transport
                peer.transport
            }
        }
    }

    private fun updateStats(transportType: TransportType, update: (MutableTransportStats) -> Unit) {
        statsMap[transportType]?.let { stats ->
            update(stats)
        }
    }

    private fun startPeriodicTasks() {
        // Peer cleanup task
        scope.launch {
            while (isActive) {
                delay(30_000) // Every 30 seconds
                cleanupInactivePeers()
            }
        }
        
        // Connection optimization task
        scope.launch {
            while (isActive) {
                delay(60_000) // Every minute
                optimizeConnections()
            }
        }
    }

    private suspend fun cleanupInactivePeers() {
        val cutoffTime = System.currentTimeMillis() - 120_000 // 2 minutes
        val inactivePeers = allPeers.values.filter { !it.isActive(120_000) }
        
        inactivePeers.forEach { peer ->
            allPeers.remove(peer.id)
            connectionPreferences.remove(peer.id)
            eventChannel.trySend(TransportEvent.PeerLost(peer.id))
        }
        
        if (inactivePeers.isNotEmpty()) {
            MeshLog.d(MeshLogTags.TRANSPORT, "Cleaned up ${inactivePeers.size} inactive peers")
        }
    }

    private suspend fun optimizeConnections() {
        // Implement connection optimization logic
        // For example, prefer Wi-Fi Direct connections over Bluetooth for high-traffic peers
        val connectedPeers = getConnectedPeers()
        
        MeshLog.d(MeshLogTags.TRANSPORT, "Connection optimization: ${connectedPeers.size} connected peers")
    }
}

/**
 * Mutable statistics for tracking transport performance
 */
private data class MutableTransportStats(
    val transportType: TransportType,
    val messagesSent: AtomicLong = AtomicLong(0),
    val messagesReceived: AtomicLong = AtomicLong(0),
    val connectionAttempts: AtomicLong = AtomicLong(0),
    val successfulConnections: AtomicLong = AtomicLong(0),
    val discoveredPeers: AtomicLong = AtomicLong(0),
    val averageLatencyMs: AtomicLong = AtomicLong(0),
    val lastActivityTime: AtomicLong = AtomicLong(System.currentTimeMillis())
)