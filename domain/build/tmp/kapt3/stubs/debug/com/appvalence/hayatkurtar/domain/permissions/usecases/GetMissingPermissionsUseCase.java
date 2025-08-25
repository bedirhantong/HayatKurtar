package com.appvalence.hayatkurtar.domain.permissions.usecases;

import com.appvalence.hayatkurtar.domain.permissions.*;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

/**
 * Use case to get missing permissions
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/usecases/GetMissingPermissionsUseCase;", "", "permissionManager", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionManager;", "(Lcom/appvalence/hayatkurtar/domain/permissions/PermissionManager;)V", "invoke", "", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "domain_debug"})
public final class GetMissingPermissionsUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.permissions.PermissionManager permissionManager = null;
    
    @javax.inject.Inject()
    public GetMissingPermissionsUseCase(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.permissions.PermissionManager permissionManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> invoke() {
        return null;
    }
}