package com.appvalence.hayatkurtar.domain.permissions.usecases;

import com.appvalence.hayatkurtar.domain.permissions.*;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

/**
 * Use case to check if all mesh networking permissions are granted
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0005\u001a\u00020\u0006H\u0086\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/usecases/CheckAllPermissionsGrantedUseCase;", "", "permissionManager", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionManager;", "(Lcom/appvalence/hayatkurtar/domain/permissions/PermissionManager;)V", "invoke", "", "domain_debug"})
public final class CheckAllPermissionsGrantedUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.permissions.PermissionManager permissionManager = null;
    
    @javax.inject.Inject()
    public CheckAllPermissionsGrantedUseCase(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.permissions.PermissionManager permissionManager) {
        super();
    }
    
    public final boolean invoke() {
        return false;
    }
}