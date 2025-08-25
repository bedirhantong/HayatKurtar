package com.appvalence.hayatkurtar.domain.usecase;

import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.appvalence.hayatkurtar.domain.mesh.MessageId;
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter;
import javax.inject.Inject;

/**
 * Use case for getting mesh network statistics
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0005\u001a\u00020\u0006H\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/usecase/GetMeshStatsUseCase;", "", "meshRouter", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshRouter;", "(Lcom/appvalence/hayatkurtar/domain/mesh/MeshRouter;)V", "invoke", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshStats;", "domain_debug"})
public final class GetMeshStatsUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.mesh.MeshRouter meshRouter = null;
    
    @javax.inject.Inject()
    public GetMeshStatsUseCase(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.mesh.MeshRouter meshRouter) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.mesh.MeshStats invoke() {
        return null;
    }
}