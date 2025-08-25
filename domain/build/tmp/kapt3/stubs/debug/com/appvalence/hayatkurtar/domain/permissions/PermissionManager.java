package com.appvalence.hayatkurtar.domain.permissions;

import kotlinx.coroutines.flow.Flow;

/**
 * Domain interface for permission management following Clean Architecture principles.
 * This interface abstracts permission handling from Android-specific implementations.
 *
 * Based on research from:
 * - Android Architecture Guidelines
 * - Clean Architecture principles by Robert C. Martin
 * - Modern Android permission best practices with Activity Result API
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u000e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H&J\u000e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bH&J\u0010\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0006H&J\u0010\u0010\f\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0006H&J\u000e\u0010\r\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\u0006H\u00a6@\u00a2\u0006\u0002\u0010\u0011J\u001c\u0010\u0012\u001a\u00020\u00102\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0014\u00a8\u0006\u0015"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionManager;", "", "areAllPermissionsGranted", "", "getMissingPermissions", "", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "getPermissionState", "Lkotlinx/coroutines/flow/Flow;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionState;", "isPermissionGranted", "permission", "isPermissionPermanentlyDenied", "openAppSettings", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requestPermission", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult;", "(Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "requestPermissions", "permissions", "(Ljava/util/Set;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "domain_debug"})
public abstract interface PermissionManager {
    
    /**
     * Get current state of all required permissions
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.permissions.PermissionState> getPermissionState();
    
    /**
     * Request a single permission
     * @param permission The permission to request
     * @return Result of the permission request
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object requestPermission(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.permissions.MeshPermission permission, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.domain.permissions.PermissionResult> $completion);
    
    /**
     * Request multiple permissions
     * @param permissions The permissions to request
     * @return Result of the permission request
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object requestPermissions(@org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> permissions, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.domain.permissions.PermissionResult> $completion);
    
    /**
     * Check if a specific permission is granted
     * @param permission The permission to check
     * @return true if granted, false otherwise
     */
    public abstract boolean isPermissionGranted(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.permissions.MeshPermission permission);
    
    /**
     * Check if all mesh networking permissions are granted
     * @return true if all required permissions are granted
     */
    public abstract boolean areAllPermissionsGranted();
    
    /**
     * Get list of missing permissions
     * @return Set of missing permissions
     */
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> getMissingPermissions();
    
    /**
     * Check if permission is permanently denied (user selected "Don't ask again")
     * @param permission The permission to check
     * @return true if permanently denied
     */
    public abstract boolean isPermissionPermanentlyDenied(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.permissions.MeshPermission permission);
    
    /**
     * Open app settings for manual permission management
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object openAppSettings(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
}