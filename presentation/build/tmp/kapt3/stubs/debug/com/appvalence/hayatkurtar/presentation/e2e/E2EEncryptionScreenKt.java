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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000R\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a(\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0003\u001a\\\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0003\u001ad\u0010\u0012\u001a\u00020\u00012\u0006\u0010\u0013\u001a\u00020\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00010\r2\u0018\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u00102\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0003\u001a*\u0010\u0016\u001a\u00020\u00012\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0018\u001a\u00020\u0019H\u0007\u001a(\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u000e2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0003\u001a\u0012\u0010\u001d\u001a\u00020\u00012\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0003\u001a4\u0010\u001e\u001a\u00020\u00012\b\u0010\u001f\u001a\u0004\u0018\u00010\u00112\b\u0010 \u001a\u0004\u0018\u00010!2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0003\u001a\u0010\u0010\"\u001a\u00020\u000e2\u0006\u0010#\u001a\u00020\u0011H\u0002\u00a8\u0006$"}, d2 = {"ContactCard", "", "contact", "Lcom/appvalence/hayatkurtar/core/crypto/E2EContact;", "onRemove", "Lkotlin/Function0;", "modifier", "Landroidx/compose/ui/Modifier;", "ContactsSection", "contacts", "", "onScanQR", "onRemoveContact", "Lkotlin/Function1;", "", "onAddContact", "Lkotlin/Function2;", "", "E2EContent", "uiState", "Lcom/appvalence/hayatkurtar/presentation/e2e/E2EUiState;", "onShareKey", "E2EEncryptionScreen", "onNavigateBack", "viewModel", "Lcom/appvalence/hayatkurtar/presentation/e2e/E2EViewModel;", "ErrorCard", "error", "onRetry", "InfoSection", "UserKeySection", "userPublicKey", "qrCodeBitmap", "Landroid/graphics/Bitmap;", "getKeyFingerprint", "publicKey", "presentation_debug"})
public final class E2EEncryptionScreenKt {
    
    /**
     * E2E encryption management screen with QR code sharing
     */
    @androidx.compose.runtime.Composable()
    public static final void E2EEncryptionScreen(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onNavigateBack, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.presentation.e2e.E2EViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void E2EContent(com.appvalence.hayatkurtar.presentation.e2e.E2EUiState uiState, kotlin.jvm.functions.Function0<kotlin.Unit> onShareKey, kotlin.jvm.functions.Function0<kotlin.Unit> onScanQR, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemoveContact, kotlin.jvm.functions.Function2<? super java.lang.String, ? super byte[], kotlin.Unit> onAddContact, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void UserKeySection(byte[] userPublicKey, android.graphics.Bitmap qrCodeBitmap, kotlin.jvm.functions.Function0<kotlin.Unit> onShareKey, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ContactsSection(java.util.List<com.appvalence.hayatkurtar.core.crypto.E2EContact> contacts, kotlin.jvm.functions.Function0<kotlin.Unit> onScanQR, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemoveContact, kotlin.jvm.functions.Function2<? super java.lang.String, ? super byte[], kotlin.Unit> onAddContact, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ContactCard(com.appvalence.hayatkurtar.core.crypto.E2EContact contact, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void InfoSection(androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ErrorCard(java.lang.String error, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.lang.String getKeyFingerprint(byte[] publicKey) {
        return null;
    }
}