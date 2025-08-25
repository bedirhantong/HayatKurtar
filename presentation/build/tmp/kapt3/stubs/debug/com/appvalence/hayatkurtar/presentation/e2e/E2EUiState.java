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

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0013\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001BC\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n\u0012\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0017\u001a\u00020\u0003H\u00c6\u0003J\u000b\u0010\u0018\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u00c6\u0003J\u000b\u0010\u001a\u001a\u0004\u0018\u00010\nH\u00c6\u0003J\u000b\u0010\u001b\u001a\u0004\u0018\u00010\fH\u00c6\u0003JG\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00c6\u0001J\u0013\u0010\u001d\u001a\u00020\u00032\b\u0010\u001e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u001f\u001a\u00020 H\u00d6\u0001J\t\u0010!\u001a\u00020\fH\u00d6\u0001R\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0013\u0010\u000b\u001a\u0004\u0018\u00010\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0012R\u0013\u0010\t\u001a\u0004\u0018\u00010\n\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\""}, d2 = {"Lcom/appvalence/hayatkurtar/presentation/e2e/E2EUiState;", "", "isLoading", "", "userPublicKey", "", "contacts", "", "Lcom/appvalence/hayatkurtar/core/crypto/E2EContact;", "qrCodeBitmap", "Landroid/graphics/Bitmap;", "error", "", "(Z[BLjava/util/List;Landroid/graphics/Bitmap;Ljava/lang/String;)V", "getContacts", "()Ljava/util/List;", "getError", "()Ljava/lang/String;", "()Z", "getQrCodeBitmap", "()Landroid/graphics/Bitmap;", "getUserPublicKey", "()[B", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "", "toString", "presentation_debug"})
public final class E2EUiState {
    private final boolean isLoading = false;
    @org.jetbrains.annotations.Nullable()
    private final byte[] userPublicKey = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.appvalence.hayatkurtar.core.crypto.E2EContact> contacts = null;
    @org.jetbrains.annotations.Nullable()
    private final android.graphics.Bitmap qrCodeBitmap = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    
    public E2EUiState(boolean isLoading, @org.jetbrains.annotations.Nullable()
    byte[] userPublicKey, @org.jetbrains.annotations.NotNull()
    java.util.List<com.appvalence.hayatkurtar.core.crypto.E2EContact> contacts, @org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap qrCodeBitmap, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
        super();
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final byte[] getUserPublicKey() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.appvalence.hayatkurtar.core.crypto.E2EContact> getContacts() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap getQrCodeBitmap() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public E2EUiState() {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final byte[] component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.appvalence.hayatkurtar.core.crypto.E2EContact> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final android.graphics.Bitmap component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.presentation.e2e.E2EUiState copy(boolean isLoading, @org.jetbrains.annotations.Nullable()
    byte[] userPublicKey, @org.jetbrains.annotations.NotNull()
    java.util.List<com.appvalence.hayatkurtar.core.crypto.E2EContact> contacts, @org.jetbrains.annotations.Nullable()
    android.graphics.Bitmap qrCodeBitmap, @org.jetbrains.annotations.Nullable()
    java.lang.String error) {
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