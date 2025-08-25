package com.appvalence.hayatkurtar.presentation.permissions;

import android.os.Build;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModel;
import com.appvalence.hayatkurtar.data.permissions.AndroidPermissionManager;
import com.appvalence.hayatkurtar.domain.permissions.*;
import com.appvalence.hayatkurtar.domain.permissions.usecases.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

/**
 * UI events for permission management
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0007\u0003\u0004\u0005\u0006\u0007\b\tB\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0007\n\u000b\f\r\u000e\u000f\u0010\u00a8\u0006\u0011"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent;", "", "()V", "AllPermissionsDenied", "AllPermissionsGranted", "NavigatedToSettings", "PermissionsSkipped", "ShowError", "ShowSnackbar", "SomePermissionsDenied", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$AllPermissionsDenied;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$AllPermissionsGranted;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$NavigatedToSettings;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$PermissionsSkipped;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$ShowError;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$ShowSnackbar;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$SomePermissionsDenied;", "presentation_debug"})
public abstract class PermissionUiEvent {
    
    private PermissionUiEvent() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$AllPermissionsDenied;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent;", "()V", "presentation_debug"})
    public static final class AllPermissionsDenied extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent.AllPermissionsDenied INSTANCE = null;
        
        private AllPermissionsDenied() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$AllPermissionsGranted;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent;", "()V", "presentation_debug"})
    public static final class AllPermissionsGranted extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent.AllPermissionsGranted INSTANCE = null;
        
        private AllPermissionsGranted() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$NavigatedToSettings;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent;", "()V", "presentation_debug"})
    public static final class NavigatedToSettings extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent.NavigatedToSettings INSTANCE = null;
        
        private NavigatedToSettings() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$PermissionsSkipped;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent;", "()V", "presentation_debug"})
    public static final class PermissionsSkipped extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent.PermissionsSkipped INSTANCE = null;
        
        private PermissionsSkipped() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$ShowError;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "presentation_debug"})
    public static final class ShowError extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        
        public ShowError(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent.ShowError copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$ShowSnackbar;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "presentation_debug"})
    public static final class ShowSnackbar extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        
        public ShowSnackbar(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getMessage() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent.ShowSnackbar copy(@org.jetbrains.annotations.NotNull()
        java.lang.String message) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent$SomePermissionsDenied;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiEvent;", "deniedPermissions", "", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "(Ljava/util/Set;)V", "getDeniedPermissions", "()Ljava/util/Set;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "presentation_debug"})
    public static final class SomePermissionsDenied extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent {
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> deniedPermissions = null;
        
        public SomePermissionsDenied(@org.jetbrains.annotations.NotNull()
        java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> deniedPermissions) {
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
        public final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent.SomePermissionsDenied copy(@org.jetbrains.annotations.NotNull()
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