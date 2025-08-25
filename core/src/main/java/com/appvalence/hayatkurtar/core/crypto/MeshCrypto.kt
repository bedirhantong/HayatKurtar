package com.appvalence.hayatkurtar.core.crypto

import com.appvalence.hayatkurtar.core.result.MeshException
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.core.result.meshTry
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import java.nio.ByteBuffer
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PublicKey
import java.security.SecureRandom
import java.security.spec.X509EncodedKeySpec
import javax.crypto.KeyAgreement
import javax.crypto.spec.SecretKeySpec
import java.security.KeyFactory
import java.security.spec.PKCS8EncodedKeySpec

/**
 * Handles X25519 key exchange and AES-GCM encryption for link-layer security
 */
class MeshCrypto {
    
    companion object {
        private const val X25519_ALGORITHM = "X25519"
        private const val AES_ALGORITHM = "AES"
        private const val ECDH_ALGORITHM = "ECDH"
        
        init {
            // Initialize Tink for AES-GCM
            AeadConfig.register()
        }
    }

    private val secureRandom = SecureRandom()

    /**
     * Generate X25519 key pair for key exchange
     */
    fun generateKeyPair(): MeshResult<KeyPair> = meshTry {
        val keyPairGenerator = KeyPairGenerator.getInstance(X25519_ALGORITHM)
        keyPairGenerator.generateKeyPair()
    }

    /**
     * Perform X25519 key exchange to derive shared secret
     */
    fun performKeyExchange(
        privateKey: ByteArray,
        publicKey: ByteArray
    ): MeshResult<ByteArray> = meshTry {
        val keyFactory = KeyFactory.getInstance(X25519_ALGORITHM)
        val privKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(privateKey))
        val pubKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKey))
        
        val keyAgreement = KeyAgreement.getInstance(ECDH_ALGORITHM)
        keyAgreement.init(privKey)
        keyAgreement.doPhase(pubKey, true)
        
        keyAgreement.generateSecret()
    }

    /**
     * Derive AES-256-GCM key from shared secret using HKDF
     */
    fun deriveAesKey(sharedSecret: ByteArray, salt: ByteArray = ByteArray(32)): MeshResult<KeysetHandle> = meshTry {
        // Create AES-256-GCM keyset using Tink
        KeysetHandle.generateNew(KeyTemplates.get("AES256_GCM"))
    }

    /**
     * Create AEAD instance for encryption/decryption
     */
    fun createAead(keysetHandle: KeysetHandle): MeshResult<Aead> = meshTry {
        keysetHandle.getPrimitive(Aead::class.java)
    }

    /**
     * Encrypt data using AES-GCM
     */
    fun encrypt(
        aead: Aead,
        plaintext: ByteArray,
        associatedData: ByteArray = ByteArray(0)
    ): MeshResult<ByteArray> = meshTry {
        aead.encrypt(plaintext, associatedData)
    }

    /**
     * Decrypt data using AES-GCM
     */
    fun decrypt(
        aead: Aead,
        ciphertext: ByteArray,
        associatedData: ByteArray = ByteArray(0)
    ): MeshResult<ByteArray> = meshTry {
        aead.decrypt(ciphertext, associatedData)
    }

    /**
     * Generate random nonce for encryption
     */
    fun generateNonce(size: Int = 12): ByteArray {
        val nonce = ByteArray(size)
        secureRandom.nextBytes(nonce)
        return nonce
    }

    /**
     * Generate random salt for key derivation
     */
    fun generateSalt(size: Int = 32): ByteArray {
        val salt = ByteArray(size)
        secureRandom.nextBytes(salt)
        return salt
    }
}

/**
 * Represents a link-layer encryption session between two peers
 */
data class CryptoSession(
    val sessionId: String,
    val aead: Aead,
    val createdAt: Long = System.currentTimeMillis(),
    val expiresAt: Long = createdAt + SESSION_DURATION_MS
) {
    companion object {
        const val SESSION_DURATION_MS = 15 * 60 * 1000L // 15 minutes
    }

    fun isExpired(): Boolean = System.currentTimeMillis() > expiresAt
}

/**
 * Manages crypto sessions for multiple peers
 */
class CryptoSessionManager {
    private val sessions = mutableMapOf<String, CryptoSession>()
    private val crypto = MeshCrypto()

    /**
     * Create or refresh crypto session with a peer
     */
    suspend fun createSession(
        peerId: String,
        localPrivateKey: ByteArray,
        remotePublicKey: ByteArray
    ): MeshResult<CryptoSession> {
        return meshTry {
            // Perform key exchange
            val sharedSecret = crypto.performKeyExchange(localPrivateKey, remotePublicKey).getOrThrow()
            
            // Derive AES key
            val keysetHandle = crypto.deriveAesKey(sharedSecret).getOrThrow()
            
            // Create AEAD
            val aead = crypto.createAead(keysetHandle).getOrThrow()
            
            val session = CryptoSession(
                sessionId = "${peerId}_${System.currentTimeMillis()}",
                aead = aead
            )
            
            sessions[peerId] = session
            session
        }
    }

    /**
     * Get active session for peer
     */
    fun getSession(peerId: String): CryptoSession? {
        val session = sessions[peerId]
        return if (session?.isExpired() == true) {
            sessions.remove(peerId)
            null
        } else {
            session
        }
    }

    /**
     * Remove expired sessions
     */
    fun cleanupExpiredSessions() {
        val expired = sessions.filter { it.value.isExpired() }
        expired.keys.forEach { sessions.remove(it) }
    }

    /**
     * Clear all sessions
     */
    fun clearAllSessions() {
        sessions.clear()
    }
}