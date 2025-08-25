package com.appvalence.hayatkurtar.core.protocol;

import com.appvalence.hayatkurtar.core.result.MeshException;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * Frame types for mesh protocol
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u0005\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u0000 \r2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\rB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\f\u00a8\u0006\u000e"}, d2 = {"Lcom/appvalence/hayatkurtar/core/protocol/FrameType;", "", "value", "", "(Ljava/lang/String;IB)V", "getValue", "()B", "HELLO", "CHAT", "ACK", "PING", "PONG", "KEY_EXCHANGE", "Companion", "core_debug"})
public enum FrameType {
    /*public static final*/ HELLO /* = new HELLO(0) */,
    /*public static final*/ CHAT /* = new CHAT(0) */,
    /*public static final*/ ACK /* = new ACK(0) */,
    /*public static final*/ PING /* = new PING(0) */,
    /*public static final*/ PONG /* = new PONG(0) */,
    /*public static final*/ KEY_EXCHANGE /* = new KEY_EXCHANGE(0) */;
    private final byte value = 0;
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.core.protocol.FrameType.Companion Companion = null;
    
    FrameType(byte value) {
    }
    
    public final byte getValue() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.appvalence.hayatkurtar.core.protocol.FrameType> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/core/protocol/FrameType$Companion;", "", "()V", "fromByte", "Lcom/appvalence/hayatkurtar/core/protocol/FrameType;", "value", "", "core_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.appvalence.hayatkurtar.core.protocol.FrameType fromByte(byte value) {
            return null;
        }
    }
}