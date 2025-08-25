package com.appvalence.hayatkurtar.core.protocol;

import com.appvalence.hayatkurtar.core.result.MeshException;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;
import java.util.zip.CRC32;

/**
 * Frame codec for serialization/deserialization
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0006\u001a\u00020\u0007J\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\u00042\u0006\u0010\t\u001a\u00020\u0005\u00a8\u0006\n"}, d2 = {"Lcom/appvalence/hayatkurtar/core/protocol/FrameCodec;", "", "()V", "decode", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "bytes", "", "encode", "frame", "core_debug"})
public final class FrameCodec {
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.core.protocol.FrameCodec INSTANCE = null;
    
    private FrameCodec() {
        super();
    }
    
    /**
     * Parse frame from byte array
     */
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.result.MeshResult<com.appvalence.hayatkurtar.core.protocol.Frame> decode(@org.jetbrains.annotations.NotNull()
    byte[] bytes) {
        return null;
    }
    
    /**
     * Encode frame to byte array
     */
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.core.result.MeshResult<byte[]> encode(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame) {
        return null;
    }
}