package com.appvalence.hayatkurtar.data.mesh.store;

import androidx.room.*;
import com.appvalence.hayatkurtar.core.logging.MeshLog;
import com.appvalence.hayatkurtar.core.logging.MeshLogTags;
import com.appvalence.hayatkurtar.domain.mesh.MessageId;
import com.appvalence.hayatkurtar.domain.mesh.MessageStore;
import com.appvalence.hayatkurtar.domain.mesh.MeshMessage;
import com.appvalence.hayatkurtar.domain.mesh.StorageStats;
import com.appvalence.hayatkurtar.domain.transport.Peer;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Room DAO for message storage operations
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\u00032\u0006\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0016\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eH\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\b\b\u0002\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u0018\u0010\u0012\u001a\u00020\u00132\b\b\u0002\u0010\b\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0014\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u001c\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\u0006\u0010\u0016\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u0016\u0010\u0017\u001a\u00020\u00132\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ\u000e\u0010\u0018\u001a\u00020\u0013H\u00a7@\u00a2\u0006\u0002\u0010\u0010J\u0016\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u000fH\u00a7@\u00a2\u0006\u0002\u0010\u001bJ\u0016\u0010\u001c\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001dH\u00a7@\u00a2\u0006\u0002\u0010\u001eJ\u0016\u0010\u001f\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u00a7@\u00a2\u0006\u0002\u0010\fJ&\u0010 \u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u00132\u0006\u0010\"\u001a\u00020\u0005H\u00a7@\u00a2\u0006\u0002\u0010#\u00a8\u0006$"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/store/MessageStoreDao;", "", "deleteExpiredMessages", "", "currentTime", "", "(JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "deleteOldSeenMessages", "cutoffTime", "deletePendingMessage", "messageId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getAllPendingMessages", "", "Lcom/appvalence/hayatkurtar/data/mesh/store/PendingMessageEntity;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPendingAcknowledgments", "getPendingAcksCount", "", "getPendingMessagesCount", "getPendingMessagesForPeer", "peerId", "getSeenMessageCount", "getSeenMessagesCount", "insertPendingMessage", "message", "(Lcom/appvalence/hayatkurtar/data/mesh/store/PendingMessageEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "insertSeenMessage", "Lcom/appvalence/hayatkurtar/data/mesh/store/SeenMessageEntity;", "(Lcom/appvalence/hayatkurtar/data/mesh/store/SeenMessageEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markMessageAcknowledged", "updateRetryInfo", "retryCount", "retryTime", "(Ljava/lang/String;IJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "mesh_debug"})
@androidx.room.Dao()
public abstract interface MessageStoreDao {
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM seen_messages WHERE messageId = :messageId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSeenMessageCount(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertSeenMessage(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.data.mesh.store.SeenMessageEntity message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM seen_messages WHERE firstSeenAt < :cutoffTime")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteOldSeenMessages(long cutoffTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM seen_messages")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getSeenMessagesCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Insert(onConflict = 1)
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object insertPendingMessage(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity message, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM pending_messages WHERE targetPeerId = :peerId AND acknowledged = 0 ORDER BY priority DESC, timestamp ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingMessagesForPeer(@org.jetbrains.annotations.NotNull()
    java.lang.String peerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM pending_messages WHERE acknowledged = 0 ORDER BY priority DESC, timestamp ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getAllPendingMessages(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity>> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM pending_messages WHERE acknowledged = 0 AND lastRetryAt < :cutoffTime ORDER BY priority DESC, timestamp ASC")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingAcknowledgments(long cutoffTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity>> $completion);
    
    @androidx.room.Query(value = "UPDATE pending_messages SET acknowledged = 1 WHERE messageId = :messageId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object markMessageAcknowledged(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "UPDATE pending_messages SET retryCount = :retryCount, lastRetryAt = :retryTime WHERE messageId = :messageId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object updateRetryInfo(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, int retryCount, long retryTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM pending_messages WHERE messageId = :messageId")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deletePendingMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "DELETE FROM pending_messages WHERE expiresAt < :currentTime")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object deleteExpiredMessages(long currentTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM pending_messages WHERE acknowledged = 0")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingMessagesCount(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    @androidx.room.Query(value = "SELECT COUNT(*) FROM pending_messages WHERE acknowledged = 0 AND lastRetryAt < :cutoffTime")
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingAcksCount(long cutoffTime, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Integer> $completion);
    
    /**
     * Room DAO for message storage operations
     */
    @kotlin.Metadata(mv = {1, 9, 0}, k = 3, xi = 48)
    public static final class DefaultImpls {
    }
}