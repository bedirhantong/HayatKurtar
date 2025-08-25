package com.appvalence.hayatkurtar.testing.transport

import com.appvalence.hayatkurtar.core.protocol.Frame
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.core.result.toSuccess
import com.appvalence.hayatkurtar.domain.transport.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Fake transport strategy for testing mesh networking scenarios
 * Simulates network behavior including latency, packet loss, and disconnections
 */
class FakeTransportStrategy(
    private val nodeId: String,
    override val transportType: TransportType = TransportType.BLUETOOTH_CLASSIC
) : TransportStrategy {

    override val name: String = "Fake-${nodeId}"
    
    // Network simulation parameters
    var latencyMs: Long = 100
    var packetLossRate: Double = 0.0 // 0.0 to 1.0
    internal val isEnabled = AtomicBoolean(true)
    
    override val isAvailable: Boolean get() = isEnabled.get()
    
    // Connections and peers
    private val connections = ConcurrentHashMap<String, FakeLink>()
    private val discoveredPeers = ConcurrentHashMap<String, Peer>()
    private val mutex = Mutex()
    
    // Event channels
    private val eventChannel = Channel<TransportEvent>(Channel.UNLIMITED)
    private val peerChannel = Channel<Peer>(Channel.UNLIMITED)
    
    // Network coordinator for inter-node communication
    var networkCoordinator: FakeNetworkCoordinator? = null
    
    private var scope: CoroutineScope? = null

    override fun start(scope: CoroutineScope): Flow<TransportEvent> {
        this.scope = scope
        networkCoordinator?.registerNode(this)
        return eventChannel.receiveAsFlow()
    }

    override fun discoverPeers(): Flow<Peer> {
        scope?.launch {
            // Simulate peer discovery with delay
            delay(500)
            networkCoordinator?.getOtherNodes(nodeId)?.forEach { otherNode ->
                val peer = Peer(
                    id = otherNode.nodeId,
                    name = "Node-${otherNode.nodeId}",
                    transport = transportType,
                    linkQuality = LinkQuality.GOOD
                )
                discoveredPeers[peer.id] = peer
                peerChannel.trySend(peer)
                eventChannel.trySend(TransportEvent.PeerDiscovered(peer))
            }
        }
        
        return peerChannel.receiveAsFlow()
    }

    override suspend fun connectTo(peer: Peer): MeshResult<Link> = mutex.withLock {
        if (!isEnabled.get()) {
            return com.appvalence.hayatkurtar.core.result.MeshException.Transport.TransportNotSupported("Transport disabled").let { 
                com.appvalence.hayatkurtar.core.result.MeshResult.Error(it) 
            }
        }
        
        // Check if already connected
        connections[peer.id]?.let { existingLink ->
            if (existingLink.isConnected) {
                return existingLink.toSuccess()
            }
        }
        
        // Simulate connection delay
        delay(latencyMs)
        
        val link = FakeLink(peer, this)
        connections[peer.id] = link
        
        eventChannel.trySend(TransportEvent.Connected(link))
        
        // Notify the target node about incoming connection
        networkCoordinator?.getNode(peer.id)?.handleIncomingConnection(nodeId, link)
        
        return link.toSuccess()
    }

    override suspend fun broadcast(frame: Frame): MeshResult<Unit> {
        if (!isEnabled.get()) {
            return com.appvalence.hayatkurtar.core.result.MeshException.Transport.NoTransportAvailable().let { 
                com.appvalence.hayatkurtar.core.result.MeshResult.Error(it) 
            }
        }
        
        val connectedLinks = connections.values.filter { it.isConnected }
        
        var successCount = 0
        for (link in connectedLinks) {
            val result = link.send(frame)
            if (result.isSuccess()) {
                successCount++
            }
        }
        
        return Unit.toSuccess()
    }

    override suspend fun sendTo(peer: Peer, frame: Frame): MeshResult<Unit> {
        val link = connections[peer.id]
            ?: return com.appvalence.hayatkurtar.core.result.MeshException.Transport.ConnectionFailed(peer.id, Exception("Not connected")).let {
                com.appvalence.hayatkurtar.core.result.MeshResult.Error(it)
            }
        
        return link.send(frame)
    }

    override fun getConnectedPeers(): List<Peer> {
        return connections.values.filter { it.isConnected }.map { it.peer }
    }

    override fun getLink(peerId: String): Link? {
        return connections[peerId]?.takeIf { it.isConnected }
    }

    override suspend fun stop() {
        networkCoordinator?.unregisterNode(nodeId)
        connections.values.forEach { it.disconnect() }
        connections.clear()
        eventChannel.close()
        peerChannel.close()
        scope = null
    }

    /**
     * Simulate receiving a frame from another node
     */
    suspend fun receiveFrame(frame: Frame, fromNodeId: String) {
        if (!isEnabled.get()) return
        
        // Simulate packet loss
        if (Math.random() < packetLossRate) {
            return // Packet lost
        }
        
        // Simulate latency
        delay(latencyMs)
        
        val fromPeer = discoveredPeers[fromNodeId] ?: Peer(
            id = fromNodeId,
            name = "Node-$fromNodeId",
            transport = transportType
        )
        
        eventChannel.trySend(TransportEvent.FrameReceived(frame, fromPeer))
    }

    /**
     * Handle incoming connection from another node
     */
    suspend fun handleIncomingConnection(fromNodeId: String, remoteLink: FakeLink) {
        val peer = Peer(
            id = fromNodeId,
            name = "Node-$fromNodeId",
            transport = transportType,
            linkQuality = LinkQuality.GOOD
        )
        
        val localLink = FakeLink(peer, this)
        connections[fromNodeId] = localLink
        
        eventChannel.trySend(TransportEvent.Connected(localLink))
    }

    /**
     * Simulate network disconnection
     */
    suspend fun simulateDisconnection(peerId: String) {
        connections[peerId]?.let { link ->
            link.forceDisconnect()
            connections.remove(peerId)
            eventChannel.trySend(TransportEvent.Disconnected(peerId, "Simulated disconnection"))
        }
    }

    /**
     * Enable/disable the transport
     */
    fun setEnabled(enabled: Boolean) {
        isEnabled.set(enabled)
        if (!enabled) {
            // Disconnect all when disabled
            scope?.launch {
                connections.keys.toList().forEach { peerId ->
                    simulateDisconnection(peerId)
                }
            }
        }
    }
}

/**
 * Fake link implementation for testing
 */
class FakeLink(
    override val peer: Peer,
    private val transport: FakeTransportStrategy
) : Link {
    
    private val connectionState = MutableStateFlow(true)
    
    override val isConnected: Boolean
        get() = connectionState.value && transport.isEnabled.get()
    
    override val linkQuality: LinkQuality
        get() = if (isConnected) LinkQuality.GOOD else LinkQuality.POOR

    override suspend fun send(frame: Frame): MeshResult<Unit> {
        if (!isConnected) {
            return com.appvalence.hayatkurtar.core.result.MeshException.Transport.SendFailed("Link disconnected", null).let {
                com.appvalence.hayatkurtar.core.result.MeshResult.Error(it)
            }
        }
        
        // Send frame to target node via network coordinator
        transport.networkCoordinator?.sendFrame(transport.nodeId, peer.id, frame)
        
        return Unit.toSuccess()
    }

    override suspend fun disconnect(): MeshResult<Unit> {
        connectionState.value = false
        return Unit.toSuccess()
    }

    override fun observeConnection(): Flow<Boolean> = connectionState.asStateFlow()

    /**
     * Force disconnection (for simulation)
     */
    fun forceDisconnect() {
        connectionState.value = false
    }
}

/**
 * Coordinates communication between fake transport nodes for testing
 */
class FakeNetworkCoordinator {
    private val nodes = ConcurrentHashMap<String, FakeTransportStrategy>()
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
    
    fun registerNode(node: FakeTransportStrategy) {
        nodes[node.nodeId] = node
    }
    
    fun unregisterNode(nodeId: String) {
        nodes.remove(nodeId)
    }
    
    fun getNode(nodeId: String): FakeTransportStrategy? = nodes[nodeId]
    
    fun getOtherNodes(excludeNodeId: String): List<FakeTransportStrategy> {
        return nodes.values.filter { it.nodeId != excludeNodeId }
    }
    
    fun sendFrame(fromNodeId: String, toNodeId: String, frame: Frame) {
        scope.launch {
            nodes[toNodeId]?.receiveFrame(frame, fromNodeId)
        }
    }
    
    fun getAllNodes(): List<FakeTransportStrategy> = nodes.values.toList()
    
    fun shutdown() {
        scope.cancel()
        nodes.clear()
    }
    
    /**
     * Simulate network partition between two groups of nodes
     */
    fun simulateNetworkPartition(group1: Set<String>, group2: Set<String>) {
        // Implementation would modify routing to prevent communication between groups
        // For now, this is a placeholder for more advanced network simulation
    }
    
    /**
     * Create a mesh network topology for testing
     */
    fun createMeshTopology(nodeCount: Int): List<FakeTransportStrategy> {
        val createdNodes = mutableListOf<FakeTransportStrategy>()
        
        for (i in 1..nodeCount) {
            val node = FakeTransportStrategy("node-$i")
            node.networkCoordinator = this
            registerNode(node)
            createdNodes.add(node)
        }
        
        return createdNodes
    }
}

/**
 * Test utilities for mesh networking scenarios
 */
object MeshTestUtils {
    
    /**
     * Create a simple 3-node mesh network for testing A -> B -> C scenarios
     */
    fun createThreeNodeMesh(): Triple<FakeTransportStrategy, FakeTransportStrategy, FakeTransportStrategy> {
        val coordinator = FakeNetworkCoordinator()
        
        val nodeA = FakeTransportStrategy("A").apply { networkCoordinator = coordinator }
        val nodeB = FakeTransportStrategy("B").apply { networkCoordinator = coordinator }
        val nodeC = FakeTransportStrategy("C").apply { networkCoordinator = coordinator }
        
        coordinator.registerNode(nodeA)
        coordinator.registerNode(nodeB)
        coordinator.registerNode(nodeC)
        
        return Triple(nodeA, nodeB, nodeC)
    }
    
    /**
     * Wait for message propagation in test scenarios
     */
    suspend fun waitForPropagation(delayMs: Long = 500) {
        delay(delayMs)
    }
}

/**
 * Extension property to access node ID from FakeTransportStrategy
 */
val FakeTransportStrategy.nodeId: String
    get() = this.name.substringAfter("Fake-")