package com.appvalence.hayatkurtar.domain.usecase;

import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.mesh.MessageId;
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter;
import javax.inject.Inject;

/**
 * Use case for acknowledging received messages
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J)\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bH\u0086B\u00f8\u0001\u0000\u00a2\u0006\u0004\b\f\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0007\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u000e"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/usecase/AcknowledgeMessageUseCase;", "", "meshRouter", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshRouter;", "(Lcom/appvalence/hayatkurtar/domain/mesh/MeshRouter;)V", "invoke", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "", "messageId", "Lcom/appvalence/hayatkurtar/domain/mesh/MessageId;", "toPeer", "Lcom/appvalence/hayatkurtar/domain/transport/Peer;", "invoke-mNZjwBM", "(Ljava/util/UUID;Lcom/appvalence/hayatkurtar/domain/transport/Peer;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public final class AcknowledgeMessageUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.mesh.MeshRouter meshRouter = null;
    
    @javax.inject.Inject()
    public AcknowledgeMessageUseCase(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.mesh.MeshRouter meshRouter) {
        super();
    }
}