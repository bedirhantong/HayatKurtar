package com.appvalence.hayatkurtar.presentation.system;

import android.content.Intent;
import android.provider.Settings;
import androidx.activity.ComponentActivity;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.ViewModel;
import com.appvalence.hayatkurtar.domain.system.*;
import com.appvalence.hayatkurtar.data.permissions.AndroidPermissionManager;
import com.appvalence.hayatkurtar.domain.permissions.MeshPermission;
import com.appvalence.hayatkurtar.presentation.permissions.PermissionViewModel;
import com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState;
import com.appvalence.hayatkurtar.presentation.permissions.PermissionUiEvent;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u0011\u001a\u00020\u0012J\u0006\u0010\u0013\u001a\u00020\u0012J\u0006\u0010\u0014\u001a\u00020\u0012J\u0006\u0010\u0015\u001a\u00020\u0012J\u0006\u0010\u0016\u001a\u00020\u0012R\u0019\u0010\u0005\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00070\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001d\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\tR\u0019\u0010\u000e\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000f0\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/system/SystemStatusViewModel;", "Landroidx/lifecycle/ViewModel;", "systemStatusManager", "Lcom/appvalence/hayatkurtar/domain/system/SystemStatusManager;", "(Lcom/appvalence/hayatkurtar/domain/system/SystemStatusManager;)V", "deviceVisibility", "Lkotlinx/coroutines/flow/StateFlow;", "Lcom/appvalence/hayatkurtar/domain/system/DeviceVisibilityStatus;", "getDeviceVisibility", "()Lkotlinx/coroutines/flow/StateFlow;", "discoveredPeers", "", "Lcom/appvalence/hayatkurtar/domain/system/DiscoveredPeer;", "getDiscoveredPeers", "systemStatus", "Lcom/appvalence/hayatkurtar/domain/system/SystemStatus;", "getSystemStatus", "enableServices", "", "refreshStatus", "requestPermissions", "startDiscovery", "stopDiscovery", "presentation_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SystemStatusViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.system.SystemStatusManager systemStatusManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.appvalence.hayatkurtar.domain.system.SystemStatus> systemStatus = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.util.List<com.appvalence.hayatkurtar.domain.system.DiscoveredPeer>> discoveredPeers = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.appvalence.hayatkurtar.domain.system.DeviceVisibilityStatus> deviceVisibility = null;
    
    @javax.inject.Inject()
    public SystemStatusViewModel(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.system.SystemStatusManager systemStatusManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.appvalence.hayatkurtar.domain.system.SystemStatus> getSystemStatus() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.util.List<com.appvalence.hayatkurtar.domain.system.DiscoveredPeer>> getDiscoveredPeers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.appvalence.hayatkurtar.domain.system.DeviceVisibilityStatus> getDeviceVisibility() {
        return null;
    }
    
    public final void requestPermissions() {
    }
    
    public final void enableServices() {
    }
    
    public final void startDiscovery() {
    }
    
    public final void stopDiscovery() {
    }
    
    public final void refreshStatus() {
    }
}