package com.appvalence.hayatkurtar.domain.mesh;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.Peer;
import kotlinx.coroutines.flow.Flow;
import java.util.UUID;

/**
 * Represents a message in the mesh network with routing metadata
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0002\b\u0010\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 82\u00020\u0001:\u00018BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u000fJ\u0006\u0010\u001e\u001a\u00020\u001fJ\u0016\u0010 \u001a\u00020\u0003H\u00c6\u0003\u00f8\u0001\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b!\u0010\u0017J\t\u0010\"\u001a\u00020\u0005H\u00c6\u0003J\t\u0010#\u001a\u00020\u0007H\u00c6\u0003J\t\u0010$\u001a\u00020\tH\u00c6\u0003J\t\u0010%\u001a\u00020\u000bH\u00c6\u0003J\t\u0010&\u001a\u00020\u000bH\u00c6\u0003J\t\u0010'\u001a\u00020\u0005H\u00c6\u0003J\t\u0010(\u001a\u00020\u0005H\u00c6\u0003Jc\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u0005H\u00c6\u0001\u00f8\u0001\u0000\u00a2\u0006\u0004\b*\u0010+J\u0013\u0010,\u001a\u00020\u001f2\b\u0010-\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010.\u001a\u0004\u0018\u00010\u0000J\b\u0010/\u001a\u000200H\u0016J\u0006\u00101\u001a\u00020\u001fJ\u000e\u00102\u001a\u0002032\u0006\u00104\u001a\u000205J\t\u00106\u001a\u000207H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u000e\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\f\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0019\u0010\u0002\u001a\u00020\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\n\n\u0002\u0010\u0018\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0013R\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\r\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0013R\u0011\u0010\n\u001a\u00020\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0015\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b!\u00a8\u00069"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;", "", "id", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "originId", "", "content", "", "priority", "Lcom/appvalence/hayatkurtar/core/protocol/Priority;", "ttl", "", "hopCount", "timestamp", "expiresAt", "(Ljava/util/UUID;J[BLcom/appvalence/hayatkurtar/core/protocol/Priority;BBJJLkotlin/jvm/internal/DefaultConstructorMarker;)V", "getContent", "()[B", "getExpiresAt", "()J", "getHopCount", "()B", "getId-f2SR-2M", "()Ljava/util/UUID;", "Ljava/util/UUID;", "getOriginId", "getPriority", "()Lcom/appvalence/hayatkurtar/core/protocol/Priority;", "getTimestamp", "getTtl", "canForward", "", "component1", "component1-f2SR-2M", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "copy-ek7Df7M", "(Ljava/util/UUID;J[BLcom/appvalence/hayatkurtar/core/protocol/Priority;BBJJ)Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage;", "equals", "other", "forward", "hashCode", "", "isExpired", "toFrame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "frameType", "Lcom/appvalence/hayatkurtar/core/protocol/FrameType;", "toString", "", "Companion", "domain_debug"})
public final class MeshMessage {
    @org.jetbrains.annotations.NotNull()
    private final java.util.UUID id = null;
    private final long originId = 0L;
    @org.jetbrains.annotations.NotNull()
    private final byte[] content = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.core.protocol.Priority priority = null;
    private final byte ttl = 0;
    private final byte hopCount = 0;
    private final long timestamp = 0L;
    private final long expiresAt = 0L;
    public static final long MESSAGE_LIFETIME_MS = 600000L;
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.domain.mesh.MeshMessage.Companion Companion = null;
    
    private MeshMessage(java.util.UUID id, long originId, byte[] content, com.appvalence.hayatkurtar.core.protocol.Priority priority, byte ttl, byte hopCount, long timestamp, long expiresAt) {
        super();
    }
    
    public final long getOriginId() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final byte[] getContent() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.Priority getPriority() {
        return null;
    }
    
    public final byte getTtl() {
        return 0;
    }
    
    public final byte getHopCount() {
        return 0;
    }
    
    public final long getTimestamp() {
        return 0L;
    }
    
    public final long getExpiresAt() {
        return 0L;
    }
    
    /**
     * Check if message has expired
     */
    public final boolean isExpired() {
        return false;
    }
    
    /**
     * Check if message should be forwarded (TTL > 0)
     */
    public final boolean canForward() {
        return false;
    }
    
    /**
     * Create forwarded version of this message
     */
    @org.jetbrains.annotations.Nullable()
    public final com.appvalence.hayatkurtar.domain.mesh.MeshMessage forward() {
        return null;
    }
    
    /**
     * Convert to Frame for transmission
     */
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.Frame toFrame(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.FrameType frameType) {
        return null;
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
    
    public final long component2() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final byte[] component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.Priority component4() {
        return null;
    }
    
    public final byte component5() {
        return 0;
    }
    
    public final byte component6() {
        return 0;
    }
    
    public final long component7() {
        return 0L;
    }
    
    public final long component8() {
        return 0L;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/mesh/MeshMessage$Companion;", "", "()V", "MESSAGE_LIFETIME_MS", "", "domain_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}