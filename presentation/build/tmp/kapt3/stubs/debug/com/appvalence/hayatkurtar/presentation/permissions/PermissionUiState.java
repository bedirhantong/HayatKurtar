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
 * UI state for permission screen following MVVM pattern
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0005\u0003\u0004\u0005\u0006\u0007B\u0007\b\u0004\u00a2\u0006\u0002\u0010\u0002\u0082\u0001\u0005\b\t\n\u000b\f\u00a8\u0006\r"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState;", "", "()V", "AllGranted", "Error", "Loading", "NeedsPermissions", "PermanentlyDenied", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$AllGranted;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$Error;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$Loading;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$NeedsPermissions;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$PermanentlyDenied;", "presentation_debug"})
public abstract class PermissionUiState {
    
    private PermissionUiState() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$AllGranted;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState;", "()V", "presentation_debug"})
    public static final class AllGranted extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState.AllGranted INSTANCE = null;
        
        private AllGranted() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$Error;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState;", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "presentation_debug"})
    public static final class Error extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String message = null;
        
        public Error(@org.jetbrains.annotations.NotNull()
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
        public final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState.Error copy(@org.jetbrains.annotations.NotNull()
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$Loading;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState;", "()V", "presentation_debug"})
    public static final class Loading extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState {
        @org.jetbrains.annotations.NotNull()
        public static final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState.Loading INSTANCE = null;
        
        private Loading() {
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B!\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\u0002\u0010\bJ\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0003J)\u0010\u000f\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u00c6\u0001J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003J\t\u0010\u0014\u001a\u00020\u0015H\u00d6\u0001J\t\u0010\u0016\u001a\u00020\u0017H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0018"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$NeedsPermissions;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState;", "missingPermissions", "", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "permissionDetails", "", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionDetail;", "(Ljava/util/Set;Ljava/util/List;)V", "getMissingPermissions", "()Ljava/util/Set;", "getPermissionDetails", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "presentation_debug"})
    public static final class NeedsPermissions extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> missingPermissions = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<com.appvalence.hayatkurtar.presentation.permissions.PermissionDetail> permissionDetails = null;
        
        public NeedsPermissions(@org.jetbrains.annotations.NotNull()
        java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> missingPermissions, @org.jetbrains.annotations.NotNull()
        java.util.List<com.appvalence.hayatkurtar.presentation.permissions.PermissionDetail> permissionDetails) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> getMissingPermissions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.appvalence.hayatkurtar.presentation.permissions.PermissionDetail> getPermissionDetails() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<com.appvalence.hayatkurtar.presentation.permissions.PermissionDetail> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState.NeedsPermissions copy(@org.jetbrains.annotations.NotNull()
        java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> missingPermissions, @org.jetbrains.annotations.NotNull()
        java.util.List<com.appvalence.hayatkurtar.presentation.permissions.PermissionDetail> permissionDetails) {
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
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\rH\u00d6\u0003J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0012"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState$PermanentlyDenied;", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState;", "permanentlyDeniedPermissions", "", "Lcom/appvalence/hayatkurtar/domain/permissions/MeshPermission;", "(Ljava/util/Set;)V", "getPermanentlyDeniedPermissions", "()Ljava/util/Set;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "presentation_debug"})
    public static final class PermanentlyDenied extends com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState {
        @org.jetbrains.annotations.NotNull()
        private final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> permanentlyDeniedPermissions = null;
        
        public PermanentlyDenied(@org.jetbrains.annotations.NotNull()
        java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> permanentlyDeniedPermissions) {
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> getPermanentlyDeniedPermissions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.Set<com.appvalence.hayatkurtar.domain.permissions.MeshPermission> component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState.PermanentlyDenied copy(@org.jetbrains.annotations.NotNull()
        java.util.Set<? extends com.appvalence.hayatkurtar.domain.permissions.MeshPermission> permanentlyDeniedPermissions) {
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