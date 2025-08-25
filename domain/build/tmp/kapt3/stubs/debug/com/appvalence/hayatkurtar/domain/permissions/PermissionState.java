package com.appvalence.hayatkurtar.domain.permissions;

/**
 * Overall state of all permissions
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\"\n\u0002\b\u0014\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 !2\u00020\u0001:\u0001!BE\u0012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\t\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\fJ\u0015\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003H\u00c6\u0003J\t\u0010\u0016\u001a\u00020\u0007H\u00c6\u0003J\u000f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\tH\u00c6\u0003J\u000f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00040\tH\u00c6\u0003J\t\u0010\u0019\u001a\u00020\u0007H\u00c6\u0003JS\u0010\u001a\u001a\u00020\u00002\u0014\b\u0002\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\t2\b\b\u0002\u0010\u000b\u001a\u00020\u0007H\u00c6\u0001J\u0013\u0010\u001b\u001a\u00020\u00072\b\u0010\u001c\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001d\u001a\u00020\u001eH\u00d6\u0001J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000b\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u001d\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\""}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionState;", "", "permissions", "", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionStatus;", "allGranted", "", "missingPermissions", "", "permanentlyDeniedPermissions", "canRequestPermissions", "(Ljava/util/Map;ZLjava/util/Set;Ljava/util/Set;Z)V", "getAllGranted", "()Z", "getCanRequestPermissions", "getMissingPermissions", "()Ljava/util/Set;", "getPermanentlyDeniedPermissions", "getPermissions", "()Ljava/util/Map;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "", "Companion", "domain_debug"})
public final class PermissionState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<com.appvalence.hayatkurtar.domain.permissions.MeshPermission, com.appvalence.hayatkurtar.domain.permissions.PermissionStatus> permissions = null;
    private final boolean allGranted = false;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> missingPermissions = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> permanentlyDeniedPermissions = null;
    private final boolean canRequestPermissions = false;
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.domain.permissions.PermissionState.Companion Companion = null;
    
    public PermissionState(@org.jetbrains.annotations.NotNull()
    java.util.Map<com.appvalence.hayatkurtar.domain.permissions.MeshPermission, ? extends com.appvalence.hayatkurtar.domain.permissions.PermissionStatus> permissions, boolean allGranted, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> missingPermissions, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> permanentlyDeniedPermissions, boolean canRequestPermissions) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<com.appvalence.hayatkurtar.domain.permissions.MeshPermission, com.appvalence.hayatkurtar.domain.permissions.PermissionStatus> getPermissions() {
        return null;
    }
    
    public final boolean getAllGranted() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> getMissingPermissions() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> getPermanentlyDeniedPermissions() {
        return null;
    }
    
    public final boolean getCanRequestPermissions() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<com.appvalence.hayatkurtar.domain.permissions.MeshPermission, com.appvalence.hayatkurtar.domain.permissions.PermissionStatus> component1() {
        return null;
    }
    
    public final boolean component2() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.permissions.PermissionState copy(@org.jetbrains.annotations.NotNull()
    java.util.Map<com.appvalence.hayatkurtar.domain.permissions.MeshPermission, ? extends com.appvalence.hayatkurtar.domain.permissions.PermissionStatus> permissions, boolean allGranted, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> missingPermissions, @org.jetbrains.annotations.NotNull()
    java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> permanentlyDeniedPermissions, boolean canRequestPermissions) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\u0003\u001a\u00020\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionState$Companion;", "", "()V", "empty", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionState;", "domain_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.permissions.PermissionState empty() {
            return null;
        }
    }
}