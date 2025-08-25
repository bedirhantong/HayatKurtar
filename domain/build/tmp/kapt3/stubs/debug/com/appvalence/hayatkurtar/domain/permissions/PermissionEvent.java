package com.appvalence.hayatkurtar.domain.permissions;

/**
 * Events that can occur during permission management
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f\u00a8\u0006\r"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent;", "", "()V", "AllPermissionsGranted", "PermissionDenied", "PermissionPermanentlyDenied", "ShouldOpenSettings", "ShouldShowRationale", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$AllPermissionsGranted;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$PermissionDenied;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$PermissionPermanentlyDenied;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$ShouldOpenSettings;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$ShouldShowRationale;", "domain_debug"})
public abstract class PermissionEvent {
    
    private PermissionEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$AllPermissionsGranted;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent;", "()V", "domain_debug"})
    public static final class AllPermissionsGranted extends com.appvalence.hayatkurtar.domain.permissions.PermissionEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.domain.permissions.PermissionEvent.AllPermissionsGranted INSTANCE = null;
        
        private AllPermissionsGranted() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$PermissionDenied;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent;", "permission", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "(Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;)V", "getPermission", "()Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class PermissionDenied extends com.appvalence.hayatkurtar.domain.permissions.PermissionEvent {
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.permissions.MeshPermission permission = null;
        
        public PermissionDenied(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.permissions.MeshPermission permission) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.permissions.MeshPermission getPermission() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.permissions.MeshPermission component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.permissions.PermissionEvent.PermissionDenied copy(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.permissions.MeshPermission permission) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$PermissionPermanentlyDenied;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent;", "permission", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "(Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;)V", "getPermission", "()Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "domain_debug"})
    public static final class PermissionPermanentlyDenied extends com.appvalence.hayatkurtar.domain.permissions.PermissionEvent {
        @org.jetbrains.annotations.NotNull()
        private final com.appvalence.hayatkurtar.domain.permissions.MeshPermission permission = null;
        
        public PermissionPermanentlyDenied(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.permissions.MeshPermission permission) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.permissions.MeshPermission getPermission() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.permissions.MeshPermission component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.domain.permissions.PermissionEvent.PermissionPermanentlyDenied copy(@org.jetbrains.annotations.NotNull()
        com.appvalence.hayatkurtar.domain.permissions.MeshPermission permission) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$ShouldOpenSettings;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent;", "()V", "domain_debug"})
    public static final class ShouldOpenSettings extends com.appvalence.hayatkurtar.domain.permissions.PermissionEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.domain.permissions.PermissionEvent.ShouldOpenSettings INSTANCE = null;
        
        private ShouldOpenSettings() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent$ShouldShowRationale;", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionEvent;", "()V", "domain_debug"})
    public static final class ShouldShowRationale extends com.appvalence.hayatkurtar.domain.permissions.PermissionEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.domain.permissions.PermissionEvent.ShouldShowRationale INSTANCE = null;
        
        private ShouldShowRationale() {
        }
    }
}