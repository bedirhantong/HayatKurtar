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
 * Implementation of MessageStore using Room database and LRU cache
 */
@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000d\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0004\b\u0007\u0018\u0000 .2\u00020\u0001:\u0001.B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\u00020\nH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\rH\u0082@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0086@\u00a2\u0006\u0002\u0010\u000bJ\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u001c\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00100\u000f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0096@\u00a2\u0006\u0002\u0010\u0015J\u000e\u0010\u0016\u001a\u00020\u0017H\u0096@\u00a2\u0006\u0002\u0010\u000bJ\u001b\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u001b\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u001f\u0010\u001dJ\u001b\u0010 \u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0004\b!\u0010\u001dJ\u001b\u0010\"\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0004\b#\u0010\u001dJ \u0010$\u001a\u00020\n2\u0006\u0010%\u001a\u00020\u00102\b\u0010&\u001a\u0004\u0018\u00010'H\u0096@\u00a2\u0006\u0002\u0010(J#\u0010)\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020+H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0004\b,\u0010-R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006/"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/store/RoomMessageStore;", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageStore;", "dao", "Lcom/appvalence/hayatkurtar/data/mesh/store/MessageStoreDao;", "(Lcom/appvalence/hayatkurtar/data/mesh/store/MessageStoreDao;)V", "mutex", "Lkotlinx/coroutines/sync/Mutex;", "seenCache", "Lcom/appvalence/hayatkurtar/data/mesh/store/SeenMessageCache;", "cleanup", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "estimateStorageUsage", "", "getAllPendingMessages", "", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;", "getPendingAcknowledgments", "getPendingMessages", "peerId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStorageStats", "Lcom/appvalence/hayatkurtar/domain/mesh/StorageStats;", "hasSeenMessage", "", "messageId", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "hasSeenMessage-meG8Gq0", "(Ljava/util/UUID;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markMessageAcknowledged", "markMessageAcknowledged-meG8Gq0", "markMessageSeen", "markMessageSeen-meG8Gq0", "removePendingMessage", "removePendingMessage-meG8Gq0", "storeOutgoingMessage", "message", "targetPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "updateRetryInfo", "retryCount", "", "updateRetryInfo-mNZjwBM", "(Ljava/util/UUID;ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Companion", "mesh_debug"})
public final class RoomMessageStore implements com.appvalence.hayatkurtar.domain.mesh.MessageStore {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.data.mesh.store.SeenMessageCache seenCache = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.sync.Mutex mutex = null;
    private static final long SEEN_MESSAGE_RETENTION_MS = 86400000L;
    private static final int MAX_RETRY_COUNT = 3;
    private static final long RETRY_DELAY_MS = 5000L;
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.data.mesh.store.RoomMessageStore.Companion Companion = null;
    
    @javax.inject.Inject()
    public RoomMessageStore(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao dao) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object storeOutgoingMessage(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.mesh.MeshMessage message, @org.jetbrains.annotations.Nullable()
    com.appvalence.hayatkurtar.domain.transport.Peer targetPeer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getPendingMessages(@org.jetbrains.annotations.NotNull()
    java.lang.String peerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.appvalence.hayatkurtar.domain.mesh.MeshMessage>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getPendingAcknowledgments(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.appvalence.hayatkurtar.domain.mesh.MeshMessage>> $completion) {
        return null;
    }
    
    /**
     * Get all pending messages (for retry processing)
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getAllPendingMessages(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.appvalence.hayatkurtar.domain.mesh.MeshMessage>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object cleanup(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object getStorageStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.domain.mesh.StorageStats> $completion) {
        return null;
    }
    
    private final java.lang.Object estimateStorageUsage(kotlin.coroutines.Continuation<? super java.lang.Long> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/store/RoomMessageStore$Companion;", "", "()V", "MAX_RETRY_COUNT", "", "RETRY_DELAY_MS", "", "SEEN_MESSAGE_RETENTION_MS", "mesh_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}