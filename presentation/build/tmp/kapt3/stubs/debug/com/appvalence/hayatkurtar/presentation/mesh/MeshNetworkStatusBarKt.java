package com.appvalence.hayatkurtar.presentation.mesh;

import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import com.appvalence.hayatkurtar.domain.mesh.MeshStats;
import com.appvalence.hayatkurtar.domain.transport.TransportStats;
import com.appvalence.hayatkurtar.domain.transport.TransportType;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a<\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00052\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001aD\u0010\f\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00052\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a0\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001a\"\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u00132\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001a\"\u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001a\"\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001c\u001a\u00020\u00072\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0003\u001a$\u0010\u001d\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u0005H\u0002\u001a\u001d\u0010\u001e\u001a\u00020\u001f2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0002\u00a2\u0006\u0002\u0010 \u001a\u0018\u0010!\u001a\u00020\u00132\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000eH\u0002\u001a\u0010\u0010\"\u001a\u00020\u00112\u0006\u0010\u001b\u001a\u00020\u0006H\u0002\u00a8\u0006#"}, d2 = {"ExpandedStatusDetails", "", "meshStats", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshStats;", "transportStats", "", "Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportStats;", "onStatusClick", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "MeshNetworkStatusBar", "isServiceRunning", "", "QuickActionButton", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "contentDescription", "", "onClick", "StatsRow", "label", "value", "StatusIndicator", "isConnected", "TransportStatusCard", "transportType", "stats", "getDetailsText", "getStatusColor", "Landroidx/compose/ui/graphics/Color;", "(Lcom/appvalence/hayatkurtar/domain/mesh/MeshStats;Z)J", "getStatusText", "getTransportIcon", "presentation_debug"})
public final class MeshNetworkStatusBarKt {
    
    /**
     * Network status bar showing real-time mesh network information
     */
    @androidx.compose.runtime.Composable()
    public static final void MeshNetworkStatusBar(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.mesh.MeshStats meshStats, @org.jetbrains.annotations.NotNull()
    java.util.Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> transportStats, boolean isServiceRunning, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onStatusClick, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatusIndicator(boolean isConnected, boolean isServiceRunning, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void QuickActionButton(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String contentDescription, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ExpandedStatusDetails(com.appvalence.hayatkurtar.domain.mesh.MeshStats meshStats, java.util.Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> transportStats, kotlin.jvm.functions.Function0<kotlin.Unit> onStatusClick, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void StatsRow(java.lang.String label, java.lang.String value, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TransportStatusCard(com.appvalence.hayatkurtar.domain.transport.TransportType transportType, com.appvalence.hayatkurtar.domain.transport.TransportStats stats, androidx.compose.ui.Modifier modifier) {
    }
    
    private static final long getStatusColor(com.appvalence.hayatkurtar.domain.mesh.MeshStats meshStats, boolean isServiceRunning) {
        return 0L;
    }
    
    private static final java.lang.String getStatusText(com.appvalence.hayatkurtar.domain.mesh.MeshStats meshStats, boolean isServiceRunning) {
        return null;
    }
    
    private static final java.lang.String getDetailsText(com.appvalence.hayatkurtar.domain.mesh.MeshStats meshStats, java.util.Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> transportStats) {
        return null;
    }
    
    private static final androidx.compose.ui.graphics.vector.ImageVector getTransportIcon(com.appvalence.hayatkurtar.domain.transport.TransportType transportType) {
        return null;
    }
}