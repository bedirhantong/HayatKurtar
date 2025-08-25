package com.appvalence.hayatkurtar.presentation.e2e;

import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.lifecycle.ViewModel;
import com.appvalence.hayatkurtar.core.crypto.E2EContact;
import com.appvalence.hayatkurtar.core.crypto.E2EEncryptionManager;
import com.appvalence.hayatkurtar.core.crypto.E2EIdentity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import java.util.Base64;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\u0006\u0010\u0014\u001a\u00020\rJ\u000e\u0010\u0015\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0016\u001a\u00020\rJ\u0006\u0010\u0017\u001a\u00020\rR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0018"}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/e2e/E2EViewModel;", "Landroidx/lifecycle/ViewModel;", "e2eManager", "Lcom/appvalence/hayatkurtar/core/crypto/E2EEncryptionManager;", "(Lcom/appvalence/hayatkurtar/core/crypto/E2EEncryptionManager;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/appvalence/hayatkurtar/presentation/e2e/E2EUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addContact", "", "contactId", "", "publicKey", "", "generateQRCode", "Landroid/graphics/Bitmap;", "loadE2EData", "removeContact", "scanQRCode", "sharePublicKey", "presentation_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class E2EViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.core.crypto.E2EEncryptionManager e2eManager = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.appvalence.hayatkurtar.presentation.e2e.E2EUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.appvalence.hayatkurtar.presentation.e2e.E2EUiState> uiState = null;
    
    @javax.inject.Inject()
    public E2EViewModel(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.core.crypto.E2EEncryptionManager e2eManager) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.appvalence.hayatkurtar.presentation.e2e.E2EUiState> getUiState() {
        return null;
    }
    
    public final void loadE2EData() {
    }
    
    public final void sharePublicKey() {
    }
    
    public final void scanQRCode() {
    }
    
    public final void addContact(@org.jetbrains.annotations.NotNull()
    java.lang.String contactId, @org.jetbrains.annotations.NotNull()
    byte[] publicKey) {
    }
    
    public final void removeContact(@org.jetbrains.annotations.NotNull()
    java.lang.String contactId) {
    }
    
    private final android.graphics.Bitmap generateQRCode(byte[] publicKey) {
        return null;
    }
}