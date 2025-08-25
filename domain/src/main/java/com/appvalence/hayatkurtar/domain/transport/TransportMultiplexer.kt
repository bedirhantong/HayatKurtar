package com.appvalence.hayatkurtar.domain.transport

import com.appvalence.hayatkurtar.core.protocol.Frame
import com.appvalence.hayatkurtar.core.result.MeshResult
import kotlinx.coroutines.flow.Flow

/**
 * Manages multiple transport strategies and orchestrates connections
 * Implements connection orchestrator pattern for handling multiple transport types
 */
interface TransportMultiplexer {
    /**
     * Add a transport strategy to the multiplexer
     */
    fun addTransport(strategy: TransportStrategy)

    /**
     * Remove a transport strategy
     */
    fun removeTransport(transportType: TransportType)

    /**
     * Start all available transport strategies
     */
    suspend fun start()

    /**
     * Stop all transport strategies
     */
    suspend fun stop()

    /**
     * Discover peers across all transport strategies
     */
    fun discoverPeers(): Flow<Peer>

    /**
     * Start discovery and advertising across all transports
     */
    suspend fun startDiscovery(): MeshResult<Unit>

    /**
     * Stop discovery and advertising across all transports
     */
    suspend fun stopDiscovery(): MeshResult<Unit>

    /**
     * Connect to a peer using the best available transport
     */
    suspend fun connectToPeer(peer: Peer): MeshResult<Link>

    /**
     * Broadcast frame to all connected peers across all transports
     */
    suspend fun broadcast(frame: Frame): MeshResult<Unit>

    /**
     * Send frame to specific peer using the best available connection
     */
    suspend fun sendToPeer(peer: Peer, frame: Frame): MeshResult<Unit>

    /**
     * Get all connected peers across all transports
     */
    fun getConnectedPeers(): List<Peer>

    /**
     * Get transport events from all strategies
     */
    val transportEvents: Flow<TransportEvent>

    /**
     * Get current transport configuration
     */
    fun getConfig(): TransportConfig

    /**
     * Update transport configuration
     */
    suspend fun updateConfig(config: TransportConfig)

    /**
     * Get statistics for all transports
     */
    fun getTransportStats(): Map<TransportType, TransportStats>
}

/**
 * Statistics for a specific transport
 */
data class TransportStats(
    val transportType: TransportType,
    val isActive: Boolean,
    val connectedPeers: Int,
    val discoveredPeers: Int,
    val messagesSent: Long,
    val messagesReceived: Long,
    val connectionAttempts: Long,
    val successfulConnections: Long,
    val averageLatencyMs: Double,
    val lastActivityTime: Long
)