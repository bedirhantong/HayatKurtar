package com.appvalence.hayatkurtar.domain.permissions;

/**
 * Result of permission request operation
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0004\u0003\u0004\u0005\u0006B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0004\u0007\b\t\n\u00a8\u0006\u000b"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult;", "", "()V", "AllDenied", "AllGranted", "Error", "PartiallyGranted", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult$AllDenied;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult$AllGranted;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult$Error;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult$PartiallyGranted;", "domain_debug"})
public abstract class PermissionResult {
    
    private PermissionResult() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult$AllDenied;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult;", "()V", "domain_debug"})
    public static final class AllDenied extends com.appvalence.hayatkurtar.domain.permissions.PermissionResult {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.domain.permissions.PermissionResult.AllDenied INSTANCE = null;
        
        private AllDenied() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult$AllGranted;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult;", "()V", "domain_debug"})
    public static final class AllGranted extends com.appvalence.hayatkurtar.domain.permissions.PermissionResult {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.domain.permissions.PermissionResult.AllGranted INSTANCE = null;
        
        private AllGranted() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult$Error;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult;", "exception", "", "(Ljava/lang/Throwable;)V", "getException", "()Ljava/lang/Throwable;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class Error extends com.appvalence.hayatkurtar.domain.permissions.PermissionResult {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.Throwable exception = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
        java.lang.Throwable exception) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.Throwable getException() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.Throwable component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.permissions.PermissionResult.Error copy(@org.jetbrains.annotations.NotNull()
        java.lang.Throwable exception) {
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
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0006J\u000f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J)\u0010\f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003J\t\u0010\u0011\u001a\u00020\u0012H\u00d6\u0001J\t\u0010\u0013\u001a\u00020\u0014H\u00d6\u0001R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b\u00a8\u0006\u0015"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult$PartiallyGranted;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionResult;", "grantedPermissions", "", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "deniedPermissions", "(Ljava/util/Set;Ljava/util/Set;)V", "getDeniedPermissions", "()Ljava/util/Set;", "getGrantedPermissions", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class PartiallyGranted extends com.appvalence.hayatkurtar.domain.permissions.PermissionResult {
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> grantedPermissions = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> deniedPermissions = null;
        
        public PartiallyGranted(@org.jetbrains.annotations.NotNull()
        java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> grantedPermissions, @org.jetbrains.annotations.NotNull()
        java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> deniedPermissions) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> getGrantedPermissions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> getDeniedPermissions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.permissions.PermissionResult.PartiallyGranted copy(@org.jetbrains.annotations.NotNull()
        java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> grantedPermissions, @org.jetbrains.annotations.NotNull()
        java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> deniedPermissions) {
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
    }
}