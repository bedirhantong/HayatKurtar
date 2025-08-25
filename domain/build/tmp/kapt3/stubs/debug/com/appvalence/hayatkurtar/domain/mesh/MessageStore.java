package com.appvalence.hayatkurtar.domain.mesh;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.Peer;
import kotlinx.coroutines.flow.Flow;
import java.util.UUID;

/**
 * Interface for managing message storage and deduplication
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\t\u001a\u00020\nH\u00a6@\u00a2\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\rH\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001b\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0015\u0010\u0013J\u001b\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0017\u0010\u0013J\u001b\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0011H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0004\b\u0019\u0010\u0013J \u0010\u001a\u001a\u00020\u00032\u0006\u0010\u001b\u001a\u00020\u00072\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u00a6@\u00a2\u0006\u0002\u0010\u001e\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u001f"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MessageStore;", "", "cleanup", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPendingAcknowledgments", "", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;", "getPendingMessages", "peerId", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getStorageStats", "Lcom/appvalence/hayatkurtar/domain/mesh/StorageStats;", "hasSeenMessage", "", "messageId", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "hasSeenMessage-meG8Gq0", "(Ljava/util/UUID;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "markMessageAcknowledged", "markMessageAcknowledged-meG8Gq0", "markMessageSeen", "markMessageSeen-meG8Gq0", "removePendingMessage", "removePendingMessage-meG8Gq0", "storeOutgoingMessage", "message", "targetPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public abstract interface MessageStore {
    
    /**
     * Store outgoing message for retry/tracking
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object storeOutgoingMessage(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.mesh.MeshMessage message, @org.jetbrains.annotations.Nullable()
    com.appvalence.hayatkurtar.domain.transport.Peer targetPeer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Get pending messages for a specific peer
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingMessages(@org.jetbrains.annotations.NotNull()
    java.lang.String peerId, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.appvalence.hayatkurtar.domain.mesh.MeshMessage>> $completion);
    
    /**
     * Get messages pending acknowledgment
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getPendingAcknowledgments(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.appvalence.hayatkurtar.domain.mesh.MeshMessage>> $completion);
    
    /**
     * Cleanup expired messages and seen entries
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object cleanup(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Get storage statistics
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object getStorageStats(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.domain.mesh.StorageStats> $completion);
}