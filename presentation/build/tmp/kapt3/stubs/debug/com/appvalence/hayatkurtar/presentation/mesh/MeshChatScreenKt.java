package com.appvalence.hayatkurtar.presentation.mesh;

import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.ExperimentalComposeUiApi;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.platform.LocalSoftwareKeyboardController;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.ViewModel;
import com.appvalence.hayatkurtar.core.protocol.Priority;
import com.appvalence.hayatkurtar.domain.mesh.*;
import com.appvalence.hayatkurtar.domain.transport.Peer;
import com.appvalence.hayatkurtar.domain.usecase.*;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.*;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000:\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a&\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0003\u001a*\u0010\u0007\u001a\u00020\u00012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a\"\u0010\n\u001a\u00020\u00012\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0003\u001a*\u0010\u000e\u001a\u00020\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0007\u001a\"\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\f2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0003\u001aD\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00042\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\t2\u0006\u0010\u0019\u001a\u00020\f2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0003\u00a8\u0006\u001a"}, d2 = {"EmergencyActionsCard", "", "onSendEmergency", "Lkotlin/Function1;", "", "modifier", "Landroidx/compose/ui/Modifier;", "EmergencyMessageDialog", "onDismiss", "Lkotlin/Function0;", "EmptyStateCard", "isConnected", "", "isServiceRunning", "MeshChatScreen", "onNavigateToSettings", "viewModel", "Lcom/appvalence/hayatkurtar/presentation/mesh/MeshChatViewModel;", "MessageCard", "message", "Lcom/appvalence/hayatkurtar/presentation/mesh/UiMessage;", "isFromSelf", "MessageInput", "onMessageChange", "onSendMessage", "isEnabled", "presentation_debug"})
public final class MeshChatScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.ui.ExperimentalComposeUiApi.class})
    @androidx.compose.runtime.Composable()
    public static final void MeshChatScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateToSettings, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.presentation.mesh.MeshChatViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmergencyActionsCard(kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSendEmergency, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmergencyMessageDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onSendEmergency) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MessageCard(com.appvalence.hayatkurtar.presentation.mesh.UiMessage message, boolean isFromSelf, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyStateCard(boolean isConnected, boolean isServiceRunning, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MessageInput(java.lang.String message, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onMessageChange, kotlin.jvm.functions.Function0<kotlin.Unit> onSendMessage, boolean isEnabled, androidx.compose.ui.Modifier modifier) {
    }
}