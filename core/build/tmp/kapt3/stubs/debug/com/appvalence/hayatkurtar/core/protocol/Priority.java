package com.appvalence.hayatkurtar.core.protocol;

import com.appvalence.hayatkurtar.core.result.MeshException;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * Message priority levels
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u0005\n\u0002\b\b\b\u0086\u0081\u0002\u0018\u0000 \n2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\nB\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\t\u00a8\u0006\u000b"}, d2 = {"Lcom/appvalence/hayatkurtar/core/protocol/Priority;", "", "value", "", "(Ljava/lang/String;IB)V", "getValue", "()B", "LOW", "NORMAL", "HIGH", "Companion", "core_debug"})
public enum Priority {
    /*public static final*/ LOW /* = new LOW(0) */,
    /*public static final*/ NORMAL /* = new NORMAL(0) */,
    /*public static final*/ HIGH /* = new HIGH(0) */;
    private final byte value = 0;
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.core.protocol.Priority.Companion Companion = null;
    
    Priority(byte value) {
    }
    
    public final byte getValue() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.appvalence.hayatkurtar.core.protocol.Priority> getEntries() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0005\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/core/protocol/Priority$Companion;", "", "()V", "fromByte", "Lcom/appvalence/hayatkurtar/core/protocol/Priority;", "value", "", "core_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final com.appvalence.hayatkurtar.core.protocol.Priority fromByte(byte value) {
            return null;
        }
    }
}