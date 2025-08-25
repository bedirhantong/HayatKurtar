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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B'\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0002J\u000e\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018J\u0006\u0010\u0019\u001a\u00020\u0013J\u000e\u0010\u001a\u001a\u00020\u00132\u0006\u0010\u0017\u001a\u00020\u0018J\b\u0010\u001b\u001a\u00020\u0013H\u0002R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001c"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/mesh/MeshChatViewModel;", "Landroidx/lifecycle/ViewModel;", "sendMessageUseCase", "Lcom/appvalence/hayatkurtar/domain/usecase/SendMessageUseCase;", "sendEmergencyMessageUseCase", "Lcom/appvalence/hayatkurtar/domain/usecase/SendEmergencyMessageUseCase;", "getMeshStatsUseCase", "Lcom/appvalence/hayatkurtar/domain/usecase/GetMeshStatsUseCase;", "meshRouter", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshRouter;", "(Lcom/appvalence/hayatkurtar/domain/usecase/SendMessageUseCase;Lcom/appvalence/hayatkurtar/domain/usecase/SendEmergencyMessageUseCase;Lcom/appvalence/hayatkurtar/domain/usecase/GetMeshStatsUseCase;Lcom/appvalence/hayatkurtar/domain/mesh/MeshRouter;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/appvalence/hayatkurtar/presentation/mesh/MeshChatUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "handleMeshEvent", "", "event", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshEvent;", "sendEmergencyMessage", "message", "", "sendMessage", "updateCurrentMessage", "updateStats", "presentation_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MeshChatViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.usecase.SendMessageUseCase sendMessageUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.usecase.SendEmergencyMessageUseCase sendEmergencyMessageUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.usecase.GetMeshStatsUseCase getMeshStatsUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.mesh.MeshRouter meshRouter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.appvalence.hayatkurtar.presentation.mesh.MeshChatUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.appvalence.hayatkurtar.presentation.mesh.MeshChatUiState> uiState = null;
    
    @javax.inject.Inject()
    public MeshChatViewModel(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.usecase.SendMessageUseCase sendMessageUseCase, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.usecase.SendEmergencyMessageUseCase sendEmergencyMessageUseCase, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.usecase.GetMeshStatsUseCase getMeshStatsUseCase, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.mesh.MeshRouter meshRouter) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.appvalence.hayatkurtar.presentation.mesh.MeshChatUiState> getUiState() {
        return null;
    }
    
    public final void updateCurrentMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    public final void sendMessage() {
    }
    
    public final void sendEmergencyMessage(@org.jetbrains.annotations.NotNull()
    java.lang.String message) {
    }
    
    private final void handleMeshEvent(com.appvalence.hayatkurtar.domain.mesh.MeshEvent event) {
    }
    
    private final void updateStats() {
    }
}