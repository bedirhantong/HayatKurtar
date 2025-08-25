package com.appvalence.hayatkurtar.domain.mesh

import com.appvalence.hayatkurtar.core.protocol.Frame
import com.appvalence.hayatkurtar.core.protocol.Priority
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.domain.transport.Peer
import kotlinx.coroutines.flow.Flow
import java.util.UUID

/**
 * Unique identifier for messages in the mesh network
 */
@JvmInline
value class MessageId(val value: UUID) {
    companion object {
        fun generate(): MessageId = MessageId(UUID.randomUUID())
        fun fromString(str: String): MessageId = MessageId(UUID.fromString(str))
    }
    
    override fun toString(): String = value.toString()
}

/**
 * Represents a message in the mesh network with routing metadata
 */
data class MeshMessage(
    val id: MessageId,
    val originId: Long,                    // Device that created this message
    val content: ByteArray,                // Message payload
    val priority: Priority = Priority.NORMAL,
    val ttl: Byte,
    val hopCount: Byte = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val expiresAt: Long = timestamp + MESSAGE_LIFETIME_MS
) {
    companion object {
        const val MESSAGE_LIFETIME_MS = 10 * 60 * 1000L // 10 minutes
    }

    /**
     * Check if message has expired
     */
    fun isExpired(): Boolean = System.currentTimeMillis() > expiresAt

    /**
     * Check if message should be forwarded (TTL > 0)
     */
    fun canForward(): Boolean = ttl > 0 && !isExpired()

    /**
     * Create forwarded version of this message
     */
    fun forward(): MeshMessage? = if (canForward()) {
        copy(
            ttl = (ttl - 1).toByte(),
            hopCount = (hopCount + 1).toByte()
        )
    } else null

    /**
     * Convert to Frame for transmission
     */
    fun toFrame(frameType: com.appvalence.hayatkurtar.core.protocol.FrameType): Frame {
        return Frame(
            type = frameType,
            messageId = id.value,
            originId = originId,
            ttl = ttl,
            hopCount = hopCount,
            priority = priority,
            timestamp = timestamp,
            payload = content
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MeshMessage

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

/**
 * Events from the mesh router
 */
sealed class MeshEvent {
    data class MessageReceived(val message: MeshMessage, val fromPeer: Peer) : MeshEvent()
    data class MessageDelivered(val messageId: MessageId, val toPeer: Peer) : MeshEvent()
    data class MessageForwarded(val messageId: MessageId, val toPeer: Peer, val hopCount: Int) : MeshEvent()
    data class MessageAcknowledged(val messageId: MessageId, val fromPeer: Peer) : MeshEvent()
    data class MessageExpired(val messageId: MessageId) : MeshEvent()
    data class MessageDropped(val messageId: MessageId, val reason: String) : MeshEvent()
    data class RouteDiscovered(val targetId: Long, val viaPeer: Peer, val hopCount: Int) : MeshEvent()
    data class RouteLost(val targetId: Long, val viaPeer: Peer) : MeshEvent()
    data class NetworkStateChanged(val connectedPeers: Int, val knownRoutes: Int) : MeshEvent()
}

/**
 * Statistics about mesh network performance
 */
data class MeshStats(
    val messagesReceived: Long = 0,
    val messagesSent: Long = 0,
    val messagesForwarded: Long = 0,
    val messagesDropped: Long = 0,
    val duplicatesBlocked: Long = 0,
    val connectedPeers: Int = 0,
    val knownRoutes: Int = 0,
    val averageLatencyMs: Double = 0.0,
    val networkReachability: Double = 0.0  // 0.0 to 1.0
)

/**
 * Core mesh router interface for message routing and handling
 */
interface MeshRouter {
    /**
     * Send a message through the mesh network
     * @param content Message content (text, binary data)
     * @param priority Message priority level
     * @param ttl Time-to-live (hop count limit)
     * @return MessageId for tracking
     */
    suspend fun sendMessage(
        content: ByteArray,
        priority: Priority = Priority.NORMAL,
        ttl: Byte = Frame.DEFAULT_TTL
    ): MeshResult<MessageId>

    /**
     * Handle incoming frame from transport layer
     * @param frame Received frame
     * @param fromPeer Peer that sent the frame
     */
    suspend fun handleIncomingFrame(frame: Frame, fromPeer: Peer)

    /**
     * Acknowledge message receipt
     * @param messageId Message to acknowledge
     * @param toPeer Peer to send ACK to
     */
    suspend fun acknowledgeMessage(messageId: MessageId, toPeer: Peer): MeshResult<Unit>

    /**
     * Get mesh events flow for UI updates
     */
    val events: Flow<MeshEvent>

    /**
     * Get current mesh statistics
     */
    fun getStats(): MeshStats

    /**
     * Get all known routes in the network
     */
    fun getKnownRoutes(): Map<Long, RouteInfo>

    /**
     * Start the mesh router
     */
    suspend fun start()

    /**
     * Stop the mesh router and cleanup
     */
    suspend fun stop()
}

/**
 * Information about a route to a specific node
 */
data class RouteInfo(
    val targetId: Long,           // Target device ID
    val nextHop: Peer,            // Next peer in route
    val hopCount: Int,            // Number of hops to target
    val lastUpdated: Long,        // When route was last updated
    val reliability: Double = 1.0 // 0.0 to 1.0, based on success rate
) {
    /**
     * Check if route is still fresh
     */
    fun isFresh(maxAgeMs: Long = 60_000): Boolean {
        return System.currentTimeMillis() - lastUpdated < maxAgeMs
    }
}

/**
 * Interface for managing message storage and deduplication
 */
interface MessageStore {
    /**
     * Check if message has been seen before
     */
    suspend fun hasSeenMessage(messageId: MessageId): Boolean

    /**
     * Mark message as seen to prevent duplicates
     */
    suspend fun markMessageSeen(messageId: MessageId)

    /**
     * Store outgoing message for retry/tracking
     */
    suspend fun storeOutgoingMessage(message: MeshMessage, targetPeer: Peer?)

    /**
     * Get pending messages for a specific peer
     */
    suspend fun getPendingMessages(peerId: String): List<MeshMessage>

    /**
     * Remove message from pending queue
     */
    suspend fun removePendingMessage(messageId: MessageId)

    /**
     * Get messages pending acknowledgment
     */
    suspend fun getPendingAcknowledgments(): List<MeshMessage>

    /**
     * Mark message as acknowledged
     */
    suspend fun markMessageAcknowledged(messageId: MessageId)

    /**
     * Cleanup expired messages and seen entries
     */
    suspend fun cleanup()

    /**
     * Get storage statistics
     */
    suspend fun getStorageStats(): StorageStats
}

/**
 * Storage statistics
 */
data class StorageStats(
    val seenMessagesCount: Int,
    val pendingMessagesCount: Int,
    val pendingAcksCount: Int,
    val storageUsageBytes: Long
)