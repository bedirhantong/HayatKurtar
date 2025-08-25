package com.appvalence.hayatkurtar.testing.transport;

import com.appvalence.hayatkurtar.core.protocol.Frame;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.transport.*;
import kotlinx.coroutines.*;
import kotlinx.coroutines.flow.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Fake link implementation for testing
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00140\u0013H\u0096@\u00a2\u0006\u0002\u0010\u0015J\u0006\u0010\u0016\u001a\u00020\u0014J\u000e\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\t0\u0018H\u0016J\u001c\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00140\u00132\u0006\u0010\u001a\u001a\u00020\u001bH\u0096@\u00a2\u0006\u0002\u0010\u001cR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2 = {"Lcom/appvalence/hayatkurtar/testing/transport/FakeLink;", "Lcom/appvalence/hayatkurtar/domain/transport/Link;", "peer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "transport", "Lcom/appvalence/hayatkurtar/testing/transport/FakeTransportStrategy;", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lcom/appvalence/hayatkurtar/testing/transport/FakeTransportStrategy;)V", "connectionState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "isConnected", "()Z", "linkQuality", "Lcom/appvalence/hayatkurtar/domain/transport/LinkQuality;", "getLinkQuality", "()Lcom/appvalence/hayatkurtar/domain/transport/LinkQuality;", "getPeer", "()Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "disconnect", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "forceDisconnect", "observeConnection", "Lkotlinx/coroutines/flow/Flow;", "send", "frame", "Lcom/appvalence/hayatkurtar/core/protocol/Frame;", "(Lcom/appvalence/hayatkurtar/core/protocol/Frame;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "testing_debug"})
public final class FakeLink implements com.appvalence.hayatkurtar.domain.transport.Link {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.transport.Peer peer = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy transport = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> connectionState = null;
    
    public FakeLink(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.testing.transport.FakeTransportStrategy transport) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.appvalence.hayatkurtar.domain.transport.Peer getPeer() {
        return null;
    }
    
    @java.lang.Override()
    public boolean isConnected() {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public com.appvalence.hayatkurtar.domain.transport.LinkQuality getLinkQuality() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object send(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.protocol.Frame frame, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object disconnect(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<kotlin.Unit>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> observeConnection() {
        return null;
    }
    
    /**
     * Force disconnection (for simulation)
     */
    public final void forceDisconnect() {
    }
}