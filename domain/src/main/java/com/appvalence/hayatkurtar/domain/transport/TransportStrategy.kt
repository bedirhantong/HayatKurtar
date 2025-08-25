package com.appvalence.hayatkurtar.domain.transport

import com.appvalence.hayatkurtar.core.protocol.Frame
import com.appvalence.hayatkurtar.core.result.MeshResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

/**
 * Represents a discovered peer in the mesh network
 */
data class Peer(
    val id: String,                    // Unique identifier (MAC address, etc.)
    val name: String?,                 // Human-readable name
    val transport: TransportType,      // How this peer was discovered
    val linkQuality: LinkQuality = LinkQuality.UNKNOWN,
    val lastSeen: Long = System.currentTimeMillis(),
    val distance: Double? = null,      // Estimated distance in meters
    val metadata: Map<String, Any> = emptyMap()
) {
    /**
     * Check if peer is recently active
     */
    fun isActive(timeoutMs: Long = 30_000): Boolean {
        return System.currentTimeMillis() - lastSeen < timeoutMs
    }
}

/**
 * Link quality indicators
 */
enum class LinkQuality {
    EXCELLENT,  // Strong signal
    GOOD,       // Adequate signal
    POOR,       // Weak signal
    UNKNOWN     // Quality not determined
}

/**
 * Types of transport mechanisms
 */
enum class TransportType {
    BLUETOOTH_CLASSIC,
    WIFI_DIRECT,
    UNKNOWN
}

/**
 * Represents an active connection/link to a peer
 */
interface Link {
    val peer: Peer
    val isConnected: Boolean
    val linkQuality: LinkQuality
    
    suspend fun send(frame: Frame): MeshResult<Unit>
    suspend fun disconnect(): MeshResult<Unit>
    fun observeConnection(): Flow<Boolean>
}

/**
 * Events from transport layer
 */
sealed class TransportEvent {
    data class PeerDiscovered(val peer: Peer) : TransportEvent()
    data class PeerLost(val peerId: String) : TransportEvent()
    data class Connected(val link: Link) : TransportEvent()
    data class Disconnected(val peerId: String, val reason: String?) : TransportEvent()
    data class FrameReceived(val frame: Frame, val fromPeer: Peer) : TransportEvent()
    data class LinkQualityChanged(val peerId: String, val quality: LinkQuality) : TransportEvent()
    data class Error(val error: Exception, val peerId: String? = null) : TransportEvent()
}

/**
 * Strategy interface for different transport mechanisms (Bluetooth, Wi-Fi Direct)
 * Implements Strategy pattern for pluggable transport implementations
 */
interface TransportStrategy {
    /**
     * Name of this transport strategy
     */
    val name: String
    
    /**
     * Transport type this strategy handles
     */
    val transportType: TransportType
    
    /**
     * Whether this transport is currently available/enabled
     */
    val isAvailable: Boolean
    
    /**
     * Start the transport strategy and return events flow
     * @param scope Coroutine scope for background operations
     * @return Flow of transport events
     */
    fun start(scope: CoroutineScope): Flow<TransportEvent>
    
    /**
     * Discover peers using this transport
     * @return Flow of discovered peers
     */
    fun discoverPeers(): Flow<Peer>
    
    /**
     * Connect to a specific peer
     * @param peer Target peer to connect to
     * @return Link if successful
     */
    suspend fun connectTo(peer: Peer): MeshResult<Link>
    
    /**
     * Broadcast frame to all connected peers
     * @param frame Frame to broadcast
     * @return Success or error result
     */
    suspend fun broadcast(frame: Frame): MeshResult<Unit>
    
    /**
     * Send frame to specific peer
     * @param peer Target peer
     * @param frame Frame to send
     * @return Success or error result
     */
    suspend fun sendTo(peer: Peer, frame: Frame): MeshResult<Unit>
    
    /**
     * Get all currently connected peers
     */
    fun getConnectedPeers(): List<Peer>
    
    /**
     * Get specific link by peer ID
     */
    fun getLink(peerId: String): Link?
    
    /**
     * Stop the transport strategy and cleanup resources
     */
    suspend fun stop()
}

/**
 * Configuration for transport strategies
 */
data class TransportConfig(
    val enableBluetoothClassic: Boolean = true,
    val enableWiFiDirect: Boolean = true,
    val scanIntervalMs: Long = 5000,           // How often to scan for peers
    val scanDurationMs: Long = 12000,          // How long each scan lasts
    val connectionTimeoutMs: Long = 10000,     // Timeout for connection attempts
    val maxConcurrentConnections: Int = 5,     // Max simultaneous connections
    val retryAttempts: Int = 3,                // Number of retry attempts
    val retryDelayMs: Long = 1000              // Delay between retries
)