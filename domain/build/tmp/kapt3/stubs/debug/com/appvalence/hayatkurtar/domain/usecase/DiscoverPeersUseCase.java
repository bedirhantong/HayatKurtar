package com.appvalence.hayatkurtar.domain.usecase;

import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.mesh.MessageId;
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter;
import javax.inject.Inject;

/**
 * Use case for discovering and managing network peers
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/usecase/DiscoverPeersUseCase;", "", "transportMultiplexer", "Lcom/appvalence/hayatkurtar/domain/transport/TransportMultiplexer;", "(Lcom/appvalence/hayatkurtar/domain/transport/TransportMultiplexer;)V", "execute", "Lkotlinx/coroutines/flow/Flow;", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "domain_debug"})
public final class DiscoverPeersUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer transportMultiplexer = null;
    
    @javax.inject.Inject()
    public DiscoverPeersUseCase(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer transportMultiplexer) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.transport.Peer> execute() {
        return null;
    }
}