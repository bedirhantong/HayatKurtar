package com.appvalence.hayatkurtar.core.protocol;

import com.appvalence.hayatkurtar.core.result.MeshException;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * Builder for creating frames
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0005\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0010\u001a\u00020\u0011J\u000e\u0010\u0003\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0004J\u000e\u0010\u0005\u001a\u00020\u00002\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\bJ\u000e\u0010\u0007\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\t\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0006J\u000e\u0010\f\u001a\u00020\u00002\u0006\u0010\f\u001a\u00020\rJ\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/appvalence/hayatkurtar/core/protocol/FrameBuilder;", "", "()V", "messageId", "Ljava/util/UUID;", "originId", "", "payload", "", "priority", "Lcom/appvalence/hayatkurtar/core/protocol/Priority;", "timestamp", "ttl", "", "type", "Lcom/appvalence/hayatkurtar/core/protocol/FrameType;", "build", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "text", "", "core_debug"})
public final class FrameBuilder {
    @org.jetbrains.annotations.NotNull()
    private com.appvalence.hayatkurtar.core.protocol.FrameType type = com.appvalence.hayatkurtar.core.protocol.FrameType.CHAT;
    @org.jetbrains.annotations.NotNull()
    private java.util.UUID messageId;
    private long originId = 0L;
    private byte ttl = (byte)8;
    @org.jetbrains.annotations.NotNull()
    private com.appvalence.hayatkurtar.core.protocol.Priority priority = com.appvalence.hayatkurtar.core.protocol.Priority.NORMAL;
    private long timestamp;
    @org.jetbrains.annotations.NotNull()
    private byte[] payload;
    
    public FrameBuilder() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.FrameBuilder type(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.FrameType type) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.FrameBuilder messageId(@org.jetbrains.annotations.NotNull()
    java.util.UUID messageId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.FrameBuilder originId(long originId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.FrameBuilder ttl(byte ttl) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.FrameBuilder priority(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Priority priority) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.FrameBuilder timestamp(long timestamp) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.FrameBuilder payload(@org.jetbrains.annotations.NotNull()
    byte[] payload) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.FrameBuilder payload(@org.jetbrains.annotations.NotNull()
    java.lang.String text) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.protocol.Frame build() {
        return null;
    }
}