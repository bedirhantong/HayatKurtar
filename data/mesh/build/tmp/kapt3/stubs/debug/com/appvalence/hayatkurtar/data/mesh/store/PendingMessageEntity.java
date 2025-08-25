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
 * Room entity for pending outgoing messages
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b$\b\u0087\b\u0018\u00002\u00020\u0001Bm\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u000b\u001a\u00020\t\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u000f\u001a\u00020\t\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\u0002\u0010\u0013J\t\u0010%\u001a\u00020\u0003H\u00c6\u0003J\t\u0010&\u001a\u00020\tH\u00c6\u0003J\t\u0010'\u001a\u00020\u0005H\u00c6\u0003J\t\u0010(\u001a\u00020\u0012H\u00c6\u0003J\t\u0010)\u001a\u00020\u0005H\u00c6\u0003J\t\u0010*\u001a\u00020\u0007H\u00c6\u0003J\t\u0010+\u001a\u00020\tH\u00c6\u0003J\t\u0010,\u001a\u00020\tH\u00c6\u0003J\t\u0010-\u001a\u00020\tH\u00c6\u0003J\t\u0010.\u001a\u00020\u0005H\u00c6\u0003J\t\u0010/\u001a\u00020\u0005H\u00c6\u0003J\u000b\u00100\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0083\u0001\u00101\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\t2\b\b\u0002\u0010\u000b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00052\n\b\u0002\u0010\u000e\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u000f\u001a\u00020\t2\b\b\u0002\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\u0012H\u00c6\u0001J\u0013\u00102\u001a\u00020\u00122\b\u00103\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u00104\u001a\u00020\tH\u0016J\t\u00105\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0011\u001a\u00020\u0012\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\r\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u000b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\u0010\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0019R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0019R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0011\u0010\u000f\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001eR\u0011\u0010\f\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0019R\u0011\u0010\n\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b$\u0010\u001b\u00a8\u00066"}, d2 = {"Lcom/appvalence/hayatkurtar/data/mesh/store/PendingMessageEntity;", "", "messageId", "", "originId", "", "content", "", "priority", "", "ttl", "hopCount", "timestamp", "expiresAt", "targetPeerId", "retryCount", "lastRetryAt", "acknowledged", "", "(Ljava/lang/String;J[BIIIJJLjava/lang/String;IJZ)V", "getAcknowledged", "()Z", "getContent", "()[B", "getExpiresAt", "()J", "getHopCount", "()I", "getLastRetryAt", "getMessageId", "()Ljava/lang/String;", "getOriginId", "getPriority", "getRetryCount", "getTargetPeerId", "getTimestamp", "getTtl", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "mesh_debug"})
@androidx.room.Entity(tableName = "pending_messages")
public final class PendingMessageEntity {
    @androidx.room.PrimaryKey()
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String messageId = null;
    private final long originId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final byte[] content = null;
    private final int priority = 0;
    private final int ttl = 0;
    private final int hopCount = 0;
    private final long timestamp = 0L;
    private final long expiresAt = 0L;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String targetPeerId = null;
    private final int retryCount = 0;
    private final long lastRetryAt = 0L;
    private final boolean acknowledged = false;
    
    public PendingMessageEntity(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, long originId, @org.jetbrains.annotations.NotNull()
    byte[] content, int priority, int ttl, int hopCount, long timestamp, long expiresAt, @org.jetbrains.annotations.Nullable()
    java.lang.String targetPeerId, int retryCount, long lastRetryAt, boolean acknowledged) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getMessageId() {
        return null;
    }
    
    public final long getOriginId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final byte[] getContent() {
        return null;
    }
    
    public final int getPriority() {
        return 0;
    }
    
    public final int getTtl() {
        return 0;
    }
    
    public final int getHopCount() {
        return 0;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    public final long getExpiresAt() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getTargetPeerId() {
        return null;
    }
    
    public final int getRetryCount() {
        return 0;
    }
    
    public final long getLastRetryAt() {
        return 0L;
    }
    
    public final boolean getAcknowledged() {
        return false;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component1() {
        return null;
    }
    
    public final int component10() {
        return 0;
    }
    
    public final long component11() {
        return 0L;
    }
    
    public final boolean component12() {
        return false;
    }
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final byte[] component3() {
        return null;
    }
    
    public final int component4() {
        return 0;
    }
    
    public final int component5() {
        return 0;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final long component7() {
        return 0L;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.data.mesh.store.PendingMessageEntity copy(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, long originId, @org.jetbrains.annotations.NotNull()
    byte[] content, int priority, int ttl, int hopCount, long timestamp, long expiresAt, @org.jetbrains.annotations.Nullable()
    java.lang.String targetPeerId, int retryCount, long lastRetryAt, boolean acknowledged) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}