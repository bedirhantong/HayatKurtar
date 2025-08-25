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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000\u00aa\u0001\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u0006H\u0003\u001a\u0010\u0010\u0007\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\tH\u0003\u001a\u001e\u0010\n\u001a\u00020\u00012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0003\u001a:\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00142\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u0014H\u0003\u001a\u0010\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\rH\u0003\u001a\u0018\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0003\u001a\u001e\u0010\u001e\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u0014H\u0003\u001a4\u0010\"\u001a\u00020\u00012\u0006\u0010\u001f\u001a\u00020 2\u0006\u0010#\u001a\u00020$2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00010\u00142\f\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00010\u0014H\u0003\u001a\u0018\u0010&\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020'H\u0003\u001a$\u0010(\u001a\u00020\u00012\u0006\u0010)\u001a\u00020*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u00020\u001b\u0012\u0004\u0012\u00020\u00010,H\u0003\u001aY\u0010-\u001a\u00020\u00012\u0006\u0010.\u001a\u00020\u001b2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u0002022\u0006\u00103\u001a\u00020\u001b2\n\b\u0002\u00104\u001a\u0004\u0018\u00010\u001b2\u0010\b\u0002\u00105\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u00142\u0011\u00106\u001a\r\u0012\u0004\u0012\u00020\u00010\u0014\u00a2\u0006\u0002\b7H\u0003\u001a\u0018\u00108\u001a\u00020\u00012\u0006\u00101\u001a\u0002022\u0006\u00109\u001a\u00020\u001bH\u0003\u001a\"\u0010:\u001a\u00020\u00012\u0006\u0010;\u001a\u00020<2\u0010\b\u0002\u0010!\u001a\n\u0012\u0004\u0012\u00020\u0001\u0018\u00010\u0014H\u0003\u001a>\u0010=\u001a\u00020\u00012\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\u00142\b\b\u0002\u0010>\u001a\u00020?2\b\b\u0002\u0010@\u001a\u00020A2\b\b\u0002\u0010B\u001a\u00020C2\b\b\u0002\u0010D\u001a\u00020EH\u0007\u00a8\u0006F"}, d2 = {"ConnectionQualityIndicator", "", "quality", "Lcom/appvalence/hayatkurtar/domain/system/ConnectionQuality;", "ConnectivityStatsCard", "connectivity", "Lcom/appvalence/hayatkurtar/domain/system/ConnectivityStatus;", "DeviceVisibilityCard", "visibility", "Lcom/appvalence/hayatkurtar/domain/system/DeviceVisibilityStatus;", "DiscoveredPeersCard", "peers", "", "Lcom/appvalence/hayatkurtar/domain/system/DiscoveredPeer;", "discoveryStatus", "Lcom/appvalence/hayatkurtar/domain/system/DiscoveryStatus;", "OverallStatusCard", "status", "Lcom/appvalence/hayatkurtar/domain/system/SystemStatus;", "onStartDiscovery", "Lkotlin/Function0;", "onStopDiscovery", "onNavigateToChat", "PeerItem", "peer", "PermissionItem", "name", "", "state", "Lcom/appvalence/hayatkurtar/domain/system/PermissionState;", "PermissionStatusCard", "permissionStatus", "Lcom/appvalence/hayatkurtar/domain/system/PermissionStatus;", "onRequestPermissions", "ProfessionalPermissionStatusCard", "permissionUiState", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionUiState;", "onOpenSettings", "ServiceItem", "Lcom/appvalence/hayatkurtar/domain/system/ServiceState;", "ServiceStatusCard", "serviceStatus", "Lcom/appvalence/hayatkurtar/domain/system/ServiceStatus;", "onEnableServices", "Lkotlin/Function1;", "StatusCard", "title", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "isHealthy", "", "statusText", "actionText", "onAction", "content", "Landroidx/compose/runtime/Composable;", "StatusIndicator", "text", "SystemErrorCard", "error", "Lcom/appvalence/hayatkurtar/domain/system/SystemError;", "SystemStatusScreen", "modifier", "Landroidx/compose/ui/Modifier;", "viewModel", "Lcom/appvalence/hayatkurtar/presentation/system/SystemStatusViewModel;", "permissionViewModel", "Lcom/appvalence/hayatkurtar/presentation/permissions/PermissionViewModel;", "androidPermissionManager", "Lcom/appvalence/hayatkurtar/data/permissions/AndroidPermissionManager;", "presentation_debug"})
public final class SystemStatusScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SystemStatusScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToChat, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.presentation.system.SystemStatusViewModel viewModel, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.presentation.permissions.PermissionViewModel permissionViewModel, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.data.permissions.AndroidPermissionManager androidPermissionManager) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void OverallStatusCard(com.appvalence.hayatkurtar.domain.system.SystemStatus status, kotlin.jvm.functions.Function0<kotlin.Unit> onStartDiscovery, kotlin.jvm.functions.Function0<kotlin.Unit> onStopDiscovery, kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToChat) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ProfessionalPermissionStatusCard(com.appvalence.hayatkurtar.domain.system.PermissionStatus permissionStatus, com.appvalence.hayatkurtar.presentation.permissions.PermissionUiState permissionUiState, kotlin.jvm.functions.Function0<kotlin.Unit> onRequestPermissions, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenSettings) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PermissionStatusCard(com.appvalence.hayatkurtar.domain.system.PermissionStatus permissionStatus, kotlin.jvm.functions.Function0<kotlin.Unit> onRequestPermissions) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ServiceStatusCard(com.appvalence.hayatkurtar.domain.system.ServiceStatus serviceStatus, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onEnableServices) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DeviceVisibilityCard(com.appvalence.hayatkurtar.domain.system.DeviceVisibilityStatus visibility) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void DiscoveredPeersCard(java.util.List<com.appvalence.hayatkurtar.domain.system.DiscoveredPeer> peers, com.appvalence.hayatkurtar.domain.system.DiscoveryStatus discoveryStatus) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ConnectivityStatsCard(com.appvalence.hayatkurtar.domain.system.ConnectivityStatus connectivity) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusCard(java.lang.String title, androidx.compose.ui.graphics.vector.ImageVector icon, boolean isHealthy, java.lang.String statusText, java.lang.String actionText, kotlin.jvm.functions.Function0<kotlin.Unit> onAction, kotlin.jvm.functions.Function0<kotlin.Unit> content) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PermissionItem(java.lang.String name, com.appvalence.hayatkurtar.domain.system.PermissionState state) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ServiceItem(java.lang.String name, com.appvalence.hayatkurtar.domain.system.ServiceState state) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PeerItem(com.appvalence.hayatkurtar.domain.system.DiscoveredPeer peer) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ConnectionQualityIndicator(com.appvalence.hayatkurtar.domain.system.ConnectionQuality quality) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusIndicator(boolean isHealthy, java.lang.String text) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SystemErrorCard(com.appvalence.hayatkurtar.domain.system.SystemError error, kotlin.jvm.functions.Function0<kotlin.Unit> onRequestPermissions) {
    }
}