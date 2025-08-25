package com.appvalence.hayatkurtar.domain.usecase;

import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.mesh.MessageId;
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter;
import javax.inject.Inject;

/**
 * Use case for connecting to a specific peer
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\tH\u0086B\u00a2\u0006\u0002\u0010\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/usecase/ConnectToPeerUseCase;", "", "transportMultiplexer", "Lcom/appvalence/hayatkurtar/domain/transport/TransportMultiplexer;", "(Lcom/appvalence/hayatkurtar/domain/transport/TransportMultiplexer;)V", "invoke", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "Lcom/appvalence/hayatkurtar/domain/transport/Link;", "peer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "(Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public final class ConnectToPeerUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer transportMultiplexer = null;
    
    @javax.inject.Inject()
    public ConnectToPeerUseCase(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer transportMultiplexer) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object invoke(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.Peer peer, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<? extends com.appvalence.hayatkurtar.domain.transport.Link>> $completion) {
        return null;
    }
}