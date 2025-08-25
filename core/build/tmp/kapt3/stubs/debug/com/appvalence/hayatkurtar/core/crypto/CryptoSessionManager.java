package com.appvalence.hayatkurtar.core.crypto;

import com.appvalence.hayatkurtar.core.result.MeshException;
import com.appvalence.hayatkurtar.core.result.MeshResult;
import com.google.crypto.tink.Aead;
import com.google.crypto.tink.KeyTemplates;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;

/**
 * Manages crypto sessions for multiple peers
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\t\u001a\u00020\nJ\u0006\u0010\u000b\u001a\u00020\nJ,\u0010\f\u001a\b\u0012\u0004\u0012\u00020\b0\r2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\b2\u0006\u0010\u000e\u001a\u00020\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/appvalence/hayatkurtar/core/crypto/CryptoSessionManager;", "", "()V", "crypto", "Lcom/appvalence/hayatkurtar/core/crypto/MeshCrypto;", "sessions", "", "", "Lcom/appvalence/hayatkurtar/core/crypto/CryptoSession;", "cleanupExpiredSessions", "", "clearAllSessions", "createSession", "Lcom/appvalence/hayatkurtar/core/result/MeshResult;", "peerId", "localPrivateKey", "", "remotePublicKey", "(Ljava/lang/String;[B[BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getSession", "core_debug"})
public final class CryptoSessionManager {
    @org.jetbrains.annotations.NotNull()
    private final java.util.Map<java.lang.String, com.appvalence.hayatkurtar.core.crypto.CryptoSession> sessions = null;
    @org.jetbrains.annotations.NotNull()
    private final com.appvalence.hayatkurtar.core.crypto.MeshCrypto crypto = null;
    
    public CryptoSessionManager() {
        super();
    }
    
    /**
     * Create or refresh crypto session with a peer
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object createSession(@org.jetbrains.annotations.NotNull()
    java.lang.String peerId, @org.jetbrains.annotations.NotNull()
    byte[] localPrivateKey, @org.jetbrains.annotations.NotNull()
    byte[] remotePublicKey, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.core.result.MeshResult<com.appvalence.hayatkurtar.core.crypto.CryptoSession>> $completion) {
        return null;
    }
    
    /**
     * Get active session for peer
     */
    @org.jetbrains.annotations.Nullable()
    public final com.appvalence.hayatkurtar.core.crypto.CryptoSession getSession(@org.jetbrains.annotations.NotNull()
    java.lang.String peerId) {
        return null;
    }
    
    /**
     * Remove expired sessions
     */
    public final void cleanupExpiredSessions() {
    }
    
    /**
     * Clear all sessions
     */
    public final void clearAllSessions() {
    }
}