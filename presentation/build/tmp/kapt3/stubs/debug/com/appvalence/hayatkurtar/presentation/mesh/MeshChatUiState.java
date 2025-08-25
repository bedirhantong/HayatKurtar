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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0013\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BI\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n\u0012\b\b\u0002\u0010\r\u001a\u00020\u000e\u00a2\u0006\u0002\u0010\u000fJ\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00c6\u0003J\t\u0010\u001a\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u001b\u001a\u00020\bH\u00c6\u0003J\u0015\u0010\u001c\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nH\u00c6\u0003J\t\u0010\u001d\u001a\u00020\u000eH\u00c6\u0003JM\u0010\u001e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\u0014\b\u0002\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u00c6\u0001J\u0013\u0010\u001f\u001a\u00020\u000e2\b\u0010 \u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010!\u001a\u00020\"H\u00d6\u0001J\t\u0010#\u001a\u00020\u0006H\u00d6\u0001R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\r\u001a\u00020\u000e\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018\u00a8\u0006$"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/mesh/MeshChatUiState;", "", "messages", "", "Lcom/appvalence/hayatkurtar/presentation/mesh/UiMessage;", "currentMessage", "", "meshStats", "Lcom/appvalence/hayatkurtar/domain/mesh/MeshStats;", "transportStats", "", "Lcom/appvalence/hayatkurtar/domain/transport/TransportType;", "Lcom/appvalence/hayatkurtar/domain/transport/TransportStats;", "isServiceRunning", "", "(Ljava/util/List;Ljava/lang/String;Lcom/appvalence/hayatkurtar/domain/mesh/MeshStats;Ljava/util/Map;Z)V", "getCurrentMessage", "()Ljava/lang/String;", "()Z", "getMeshStats", "()Lcom/appvalence/hayatkurtar/domain/mesh/MeshStats;", "getMessages", "()Ljava/util/List;", "getTransportStats", "()Ljava/util/Map;", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "presentation_debug"})
public final class MeshChatUiState {
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.appvalence.hayatkurtar.presentation.mesh.UiMessage> messages = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String currentMessage = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.domain.mesh.MeshStats meshStats = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> transportStats = null;
    private final boolean isServiceRunning = false;
    
    public MeshChatUiState(@org.jetbrains.annotations.NotNull()
    java.util.List<com.appvalence.hayatkurtar.presentation.mesh.UiMessage> messages, @org.jetbrains.annotations.NotNull()
    java.lang.String currentMessage, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.mesh.MeshStats meshStats, @org.jetbrains.annotations.NotNull()
    java.util.Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> transportStats, boolean isServiceRunning) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.appvalence.hayatkurtar.presentation.mesh.UiMessage> getMessages() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCurrentMessage() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.mesh.MeshStats getMeshStats() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> getTransportStats() {
        return null;
    }
    
    public final boolean isServiceRunning() {
        return false;
    }
    
    public MeshChatUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.appvalence.hayatkurtar.presentation.mesh.UiMessage> component1() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.mesh.MeshStats component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> component4() {
        return null;
    }
    
    public final boolean component5() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.presentation.mesh.MeshChatUiState copy(@org.jetbrains.annotations.NotNull()
    java.util.List<com.appvalence.hayatkurtar.presentation.mesh.UiMessage> messages, @org.jetbrains.annotations.NotNull()
    java.lang.String currentMessage, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.domain.mesh.MeshStats meshStats, @org.jetbrains.annotations.NotNull()
    java.util.Map<com.appvalence.hayatkurtar.domain.transport.TransportType, com.appvalence.hayatkurtar.domain.transport.TransportStats> transportStats, boolean isServiceRunning) {
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