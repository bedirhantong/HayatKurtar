package com.appvalence.hayatkurtar.data.mesh.store

import androidx.room.*
import com.appvalence.hayatkurtar.core.logging.MeshLog
import com.appvalence.hayatkurtar.core.logging.MeshLogTags
import com.appvalence.hayatkurtar.domain.mesh.MessageId
import com.appvalence.hayatkurtar.domain.mesh.MessageStore
import com.appvalence.hayatkurtar.domain.mesh.MeshMessage
import com.appvalence.hayatkurtar.domain.mesh.StorageStats
import com.appvalence.hayatkurtar.domain.transport.Peer
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Room entity for seen messages (deduplication)
 */
@Entity(tableName = "seen_messages")
data class SeenMessageEntity(
    @PrimaryKey val messageId: String,
    val firstSeenAt: Long,
    val lastSeenAt: Long = firstSeenAt
)

/**
 * Room entity for pending outgoing messages
 */
@Entity(tableName = "pending_messages")
data class PendingMessageEntity(
    @PrimaryKey val messageId: String,
    val originId: Long,
    val content: ByteArray,
    val priority: Int,
    val ttl: Int,
    val hopCount: Int,
    val timestamp: Long,
    val expiresAt: Long,
    val targetPeerId: String?,
    val retryCount: Int = 0,
    val lastRetryAt: Long = 0,
    val acknowledged: Boolean = false
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PendingMessageEntity

        return messageId == other.messageId
    }

    override fun hashCode(): Int {
        return messageId.hashCode()
    }
}

/**
 * Room DAO for message storage operations
 */
@Dao
interface MessageStoreDao {
    @Query("SELECT COUNT(*) FROM seen_messages WHERE messageId = :messageId")
    suspend fun getSeenMessageCount(messageId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeenMessage(message: SeenMessageEntity)

    @Query("DELETE FROM seen_messages WHERE firstSeenAt < :cutoffTime")
    suspend fun deleteOldSeenMessages(cutoffTime: Long)

    @Query("SELECT COUNT(*) FROM seen_messages")
    suspend fun getSeenMessagesCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPendingMessage(message: PendingMessageEntity)

    @Query("SELECT * FROM pending_messages WHERE targetPeerId = :peerId AND acknowledged = 0 ORDER BY priority DESC, timestamp ASC")
    suspend fun getPendingMessagesForPeer(peerId: String): List<PendingMessageEntity>

    @Query("SELECT * FROM pending_messages WHERE acknowledged = 0 ORDER BY priority DESC, timestamp ASC")
    suspend fun getAllPendingMessages(): List<PendingMessageEntity>

    @Query("SELECT * FROM pending_messages WHERE acknowledged = 0 AND lastRetryAt < :cutoffTime ORDER BY priority DESC, timestamp ASC")
    suspend fun getPendingAcknowledgments(cutoffTime: Long = System.currentTimeMillis() - 30000): List<PendingMessageEntity>

    @Query("UPDATE pending_messages SET acknowledged = 1 WHERE messageId = :messageId")
    suspend fun markMessageAcknowledged(messageId: String)

    @Query("UPDATE pending_messages SET retryCount = :retryCount, lastRetryAt = :retryTime WHERE messageId = :messageId")
    suspend fun updateRetryInfo(messageId: String, retryCount: Int, retryTime: Long)

    @Query("DELETE FROM pending_messages WHERE messageId = :messageId")
    suspend fun deletePendingMessage(messageId: String)

    @Query("DELETE FROM pending_messages WHERE expiresAt < :currentTime")
    suspend fun deleteExpiredMessages(currentTime: Long = System.currentTimeMillis())

    @Query("SELECT COUNT(*) FROM pending_messages WHERE acknowledged = 0")
    suspend fun getPendingMessagesCount(): Int

    @Query("SELECT COUNT(*) FROM pending_messages WHERE acknowledged = 0 AND lastRetryAt < :cutoffTime")
    suspend fun getPendingAcksCount(cutoffTime: Long = System.currentTimeMillis() - 30000): Int
}

/**
 * LRU Cache for frequently accessed seen messages
 */
class SeenMessageCache(private val maxSize: Int = 1000) {
    private val cache = LinkedHashMap<String, Long>(maxSize, 0.75f, true)
    private val mutex = Mutex()

    suspend fun contains(messageId: String): Boolean = mutex.withLock {
        cache.containsKey(messageId)
    }

    suspend fun put(messageId: String, timestamp: Long = System.currentTimeMillis()) = mutex.withLock {
        if (cache.size >= maxSize) {
            val eldest = cache.entries.iterator().next()
            cache.remove(eldest.key)
        }
        cache[messageId] = timestamp
    }

    suspend fun size(): Int = mutex.withLock { cache.size }

    suspend fun clear() = mutex.withLock { cache.clear() }
}

/**
 * Implementation of MessageStore using Room database and LRU cache
 */
@Singleton
class RoomMessageStore @Inject constructor(
    private val dao: MessageStoreDao
) : MessageStore {

    private val seenCache = SeenMessageCache()
    private val mutex = Mutex()

    companion object {
        private const val SEEN_MESSAGE_RETENTION_MS = 24 * 60 * 60 * 1000L // 24 hours
        private const val MAX_RETRY_COUNT = 3
        private const val RETRY_DELAY_MS = 5000L
    }

    override suspend fun hasSeenMessage(messageId: MessageId): Boolean {
        val id = messageId.toString()
        
        // Check cache first
        if (seenCache.contains(id)) {
            return true
        }
        
        // Check database
        val count = dao.getSeenMessageCount(id)
        val seen = count > 0
        
        // Update cache if found
        if (seen) {
            seenCache.put(id)
        }
        
        return seen
    }

    override suspend fun markMessageSeen(messageId: MessageId) {
        val id = messageId.toString()
        val timestamp = System.currentTimeMillis()
        
        // Update cache
        seenCache.put(id, timestamp)
        
        // Update database
        val entity = SeenMessageEntity(
            messageId = id,
            firstSeenAt = timestamp,
            lastSeenAt = timestamp
        )
        dao.insertSeenMessage(entity)
        
        MeshLog.d(MeshLogTags.MESH_ROUTER, "Marked message as seen: $id")
    }

    override suspend fun storeOutgoingMessage(message: MeshMessage, targetPeer: Peer?) {
        val entity = PendingMessageEntity(
            messageId = message.id.toString(),
            originId = message.originId,
            content = message.content,
            priority = message.priority.ordinal,
            ttl = message.ttl.toInt(),
            hopCount = message.hopCount.toInt(),
            timestamp = message.timestamp,
            expiresAt = message.expiresAt,
            targetPeerId = targetPeer?.id
        )
        
        dao.insertPendingMessage(entity)
        
        MeshLog.d(MeshLogTags.MESH_ROUTER, "Stored outgoing message: ${message.id} to ${targetPeer?.id ?: "broadcast"}")
    }

    override suspend fun getPendingMessages(peerId: String): List<MeshMessage> {
        val entities = dao.getPendingMessagesForPeer(peerId)
        return entities.map { it.toMeshMessage() }
    }

    override suspend fun removePendingMessage(messageId: MessageId) {
        dao.deletePendingMessage(messageId.toString())
        MeshLog.d(MeshLogTags.MESH_ROUTER, "Removed pending message: $messageId")
    }

    override suspend fun getPendingAcknowledgments(): List<MeshMessage> {
        val entities = dao.getPendingAcknowledgments()
        return entities.map { it.toMeshMessage() }
    }

    override suspend fun markMessageAcknowledged(messageId: MessageId) {
        dao.markMessageAcknowledged(messageId.toString())
        MeshLog.d(MeshLogTags.MESH_ROUTER, "Marked message acknowledged: $messageId")
    }

    /**
     * Update retry information for a message
     */
    suspend fun updateRetryInfo(messageId: MessageId, retryCount: Int) {
        dao.updateRetryInfo(messageId.toString(), retryCount, System.currentTimeMillis())
    }

    /**
     * Get all pending messages (for retry processing)
     */
    suspend fun getAllPendingMessages(): List<MeshMessage> {
        val entities = dao.getAllPendingMessages()
        return entities.map { it.toMeshMessage() }
    }

    override suspend fun cleanup() = mutex.withLock {
        val currentTime = System.currentTimeMillis()
        
        // Remove old seen messages
        val cutoffTime = currentTime - SEEN_MESSAGE_RETENTION_MS
        dao.deleteOldSeenMessages(cutoffTime)
        
        // Remove expired pending messages
        dao.deleteExpiredMessages(currentTime)
        
        // Clear cache periodically
        if (seenCache.size() > 800) {
            seenCache.clear()
        }
        
        MeshLog.d(MeshLogTags.MESH_ROUTER, "Completed message store cleanup")
    }

    override suspend fun getStorageStats(): StorageStats {
        return StorageStats(
            seenMessagesCount = dao.getSeenMessagesCount(),
            pendingMessagesCount = dao.getPendingMessagesCount(),
            pendingAcksCount = dao.getPendingAcksCount(),
            storageUsageBytes = estimateStorageUsage()
        )
    }

    private suspend fun estimateStorageUsage(): Long {
        // Rough estimation of storage usage
        val seenCount = dao.getSeenMessagesCount()
        val pendingCount = dao.getPendingMessagesCount()
        
        // Estimate bytes per record
        val bytesPerSeenMessage = 50L  // UUID + timestamps
        val bytesPerPendingMessage = 200L  // All fields + content
        
        return (seenCount * bytesPerSeenMessage) + (pendingCount * bytesPerPendingMessage)
    }
}

/**
 * Extension function to convert entity to domain model
 */
private fun PendingMessageEntity.toMeshMessage(): MeshMessage {
    return MeshMessage(
        id = MessageId.fromString(messageId),
        originId = originId,
        content = content,
        priority = com.appvalence.hayatkurtar.core.protocol.Priority.values()[priority],
        ttl = ttl.toByte(),
        hopCount = hopCount.toByte(),
        timestamp = timestamp,
        expiresAt = expiresAt
    )
}