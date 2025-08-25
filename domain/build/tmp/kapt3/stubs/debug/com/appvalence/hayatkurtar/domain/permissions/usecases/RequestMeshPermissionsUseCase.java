package com.appvalence.hayatkurtar.domain.permissions.usecases;

import com.appvalence.hayatkurtar.domain.permissions.*;
import kotlinx.coroutines.flow.Flow;
import javax.inject.Inject;

/**
 * Use case to request all required permissions for mesh networking
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0086B\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/usecases/RequestMeshPermissionsUseCase;", "", "permissionManager", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionManager;", "(Lcom/appvalence/hayatkurtar/domain/permissions/PermissionManager;)V", "invoke", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult;", "sdkVersion", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public final class RequestMeshPermissionsUseCase {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.permissions.PermissionManager permissionManager = null;
    
    @javax.inject.Inject()
    public RequestMeshPermissionsUseCase(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.permissions.PermissionManager permissionManager) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object invoke(int sdkVersion, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.domain.permissions.PermissionResult> $completion) {
        return null;
    }
}