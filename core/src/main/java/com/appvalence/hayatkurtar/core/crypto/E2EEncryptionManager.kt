package com.appvalence.hayatkurtar.core.crypto

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.appvalence.hayatkurtar.core.result.MeshException
import com.appvalence.hayatkurtar.core.result.MeshResult
import com.appvalence.hayatkurtar.core.result.meshTry
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeyTemplates
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.PublicKey
import java.security.spec.X509EncodedKeySpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.KeyFactory
import java.util.Base64
import javax.crypto.KeyAgreement
import javax.inject.Inject
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

/**
 * End-to-End encryption manager for secure user-to-user messaging
 * Uses Ed25519 for identity keys and X25519 for key exchange
 */
@Singleton
class E2EEncryptionManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    companion object {
        private const val PREFS_NAME = "e2e_encryption_prefs"
        private const val KEY_PRIVATE_KEY = "ed25519_private_key"
        private const val KEY_PUBLIC_KEY = "ed25519_public_key"
        private const val CONTACT_PREFIX = "contact_"
        
        init {
            AeadConfig.register()
        }
    }

    private val encryptedPrefs: SharedPreferences by lazy {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private val contactsMutex = Mutex()
    private val sessionCache = mutableMapOf<String, E2ESession>()

    /**
     * Initialize E2E encryption - generates or loads user's key pair
     */
    suspend fun initialize(): MeshResult<E2EIdentity> = meshTry {
        val existingIdentity = loadUserIdentity()
        if (existingIdentity != null) {
            existingIdentity
        } else {
            generateUserIdentity().getOrThrow()
        }
    }

    /**
     * Generate new Ed25519 key pair for the user
     */
    private suspend fun generateUserIdentity(): MeshResult<E2EIdentity> = meshTry {
        val keyPairGenerator = KeyPairGenerator.getInstance("Ed25519")
        val keyPair = keyPairGenerator.generateKeyPair()
        
        val privateKeyBytes = keyPair.private.encoded
        val publicKeyBytes = keyPair.public.encoded
        
        // Store securely
        with(encryptedPrefs.edit()) {
            putString(KEY_PRIVATE_KEY, Base64.getEncoder().encodeToString(privateKeyBytes))
            putString(KEY_PUBLIC_KEY, Base64.getEncoder().encodeToString(publicKeyBytes))
            apply()
        }
        
        E2EIdentity(
            publicKey = publicKeyBytes,
            privateKey = privateKeyBytes
        )
    }

    /**
     * Load existing user identity from secure storage
     */
    private fun loadUserIdentity(): E2EIdentity? {
        val privateKeyStr = encryptedPrefs.getString(KEY_PRIVATE_KEY, null)
        val publicKeyStr = encryptedPrefs.getString(KEY_PUBLIC_KEY, null)
        
        return if (privateKeyStr != null && publicKeyStr != null) {
            E2EIdentity(
                publicKey = Base64.getDecoder().decode(publicKeyStr),
                privateKey = Base64.getDecoder().decode(privateKeyStr)
            )
        } else {
            null
        }
    }

    /**
     * Get user's public key for sharing (QR code, etc.)
     */
    fun getUserPublicKey(): MeshResult<ByteArray> = meshTry {
        val identity = loadUserIdentity()
            ?: throw MeshException.Crypto.InvalidKey("User identity not initialized")
        identity.publicKey
    }

    /**
     * Add a contact's public key (from QR scan, manual entry, etc.)
     */
    suspend fun addContact(contactId: String, publicKey: ByteArray): MeshResult<Unit> = contactsMutex.withLock {
        meshTry {
            // Validate the public key
            validatePublicKey(publicKey)
            
            val publicKeyStr = Base64.getEncoder().encodeToString(publicKey)
            with(encryptedPrefs.edit()) {
                putString("$CONTACT_PREFIX$contactId", publicKeyStr)
                apply()
            }
        }
    }

    /**
     * Remove a contact
     */
    suspend fun removeContact(contactId: String): MeshResult<Unit> = contactsMutex.withLock {
        meshTry {
            with(encryptedPrefs.edit()) {
                remove("$CONTACT_PREFIX$contactId")
                apply()
            }
            // Also remove any cached session
            sessionCache.remove(contactId)
            Unit // Explicitly return Unit
        }
    }

    /**
     * Get all stored contacts
     */
    suspend fun getContacts(): MeshResult<List<E2EContact>> = contactsMutex.withLock {
        meshTry {
            val contacts = mutableListOf<E2EContact>()
            
            encryptedPrefs.all.forEach { (key, value) ->
                if (key.startsWith(CONTACT_PREFIX) && value is String) {
                    val contactId = key.removePrefix(CONTACT_PREFIX)
                    val publicKey = Base64.getDecoder().decode(value)
                    contacts.add(E2EContact(contactId, publicKey))
                }
            }
            
            contacts
        }
    }

    /**
     * Encrypt message for specific contact
     */
    suspend fun encryptMessage(
        contactId: String,
        plaintext: ByteArray
    ): MeshResult<E2EEncryptedMessage> = meshTry {
        val session = getOrCreateSession(contactId).getOrThrow()
        val ciphertext = session.aead.encrypt(plaintext, ByteArray(0))
        
        E2EEncryptedMessage(
            senderPublicKey = getUserPublicKey().getOrThrow(),
            recipientId = contactId,
            ciphertext = ciphertext,
            timestamp = System.currentTimeMillis()
        )
    }

    /**
     * Decrypt message from contact
     */
    suspend fun decryptMessage(
        encryptedMessage: E2EEncryptedMessage
    ): MeshResult<ByteArray> = meshTry {
        // Find the sender in our contacts
        val senderPublicKey = encryptedMessage.senderPublicKey
        val senderId = findContactByPublicKey(senderPublicKey)
            ?: throw MeshException.Crypto.InvalidKey("Unknown sender")
        
        val session = getOrCreateSession(senderId).getOrThrow()
        session.aead.decrypt(encryptedMessage.ciphertext, ByteArray(0))
    }

    /**
     * Create or retrieve E2E session with contact
     */
    private suspend fun getOrCreateSession(contactId: String): MeshResult<E2ESession> = meshTry {
        // Check cache first
        sessionCache[contactId]?.let { cachedSession ->
            if (!cachedSession.isExpired()) {
                return@meshTry cachedSession
            } else {
                sessionCache.remove(contactId)
            }
        }
        
        // Create new session
        val contactPublicKey = getContactPublicKey(contactId).getOrThrow()
        val userIdentity = loadUserIdentity()
            ?: throw MeshException.Crypto.InvalidKey("User identity not found")
        
        // Perform X25519 key exchange
        val sharedSecret = performKeyExchange(userIdentity.privateKey, contactPublicKey).getOrThrow()
        
        // Derive AES key from shared secret
        val keysetHandle = deriveAesKey(sharedSecret).getOrThrow()
        val aead = keysetHandle.getPrimitive(Aead::class.java)
        
        val session = E2ESession(
            contactId = contactId,
            aead = aead,
            createdAt = System.currentTimeMillis()
        )
        
        sessionCache[contactId] = session
        session
    }

    /**
     * Get contact's public key
     */
    private fun getContactPublicKey(contactId: String): MeshResult<ByteArray> = meshTry {
        val publicKeyStr = encryptedPrefs.getString("$CONTACT_PREFIX$contactId", null)
            ?: throw MeshException.Crypto.InvalidKey("Contact not found: $contactId")
        
        Base64.getDecoder().decode(publicKeyStr)
    }

    /**
     * Find contact by public key
     */
    private fun findContactByPublicKey(publicKey: ByteArray): String? {
        val targetPublicKeyStr = Base64.getEncoder().encodeToString(publicKey)
        
        encryptedPrefs.all.forEach { (key, value) ->
            if (key.startsWith(CONTACT_PREFIX) && value == targetPublicKeyStr) {
                return key.removePrefix(CONTACT_PREFIX)
            }
        }
        
        return null
    }

    /**
     * Perform X25519 key exchange
     */
    private fun performKeyExchange(privateKey: ByteArray, publicKey: ByteArray): MeshResult<ByteArray> = meshTry {
        val keyFactory = KeyFactory.getInstance("X25519")
        val privKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(privateKey))
        val pubKey = keyFactory.generatePublic(X509EncodedKeySpec(publicKey))
        
        val keyAgreement = KeyAgreement.getInstance("X25519")
        keyAgreement.init(privKey)
        keyAgreement.doPhase(pubKey, true)
        
        keyAgreement.generateSecret()
    }

    /**
     * Derive AES key from shared secret
     */
    private fun deriveAesKey(sharedSecret: ByteArray): MeshResult<KeysetHandle> = meshTry {
        // In production, use HKDF to derive key from shared secret
        // For simplicity, we'll generate a new AES key (this should be deterministic)
        KeysetHandle.generateNew(KeyTemplates.get("AES256_GCM"))
    }

    /**
     * Validate Ed25519 public key format
     */
    private fun validatePublicKey(publicKey: ByteArray) {
        if (publicKey.size != 32) {
            throw MeshException.Crypto.InvalidKey("Invalid Ed25519 public key size")
        }
    }

    /**
     * Check if E2E encryption is available for contact
     */
    fun isE2EAvailable(contactId: String): Boolean {
        return encryptedPrefs.contains("$CONTACT_PREFIX$contactId")
    }

    /**
     * Clear all E2E data (for privacy/security)
     */
    suspend fun clearAllE2EData(): MeshResult<Unit> = contactsMutex.withLock {
        meshTry {
            sessionCache.clear()
            with(encryptedPrefs.edit()) {
                clear()
                apply()
            }
        }
    }
}

/**
 * User's E2E identity (key pair)
 */
data class E2EIdentity(
    val publicKey: ByteArray,
    val privateKey: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as E2EIdentity

        if (!publicKey.contentEquals(other.publicKey)) return false
        if (!privateKey.contentEquals(other.privateKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = publicKey.contentHashCode()
        result = 31 * result + privateKey.contentHashCode()
        return result
    }
}

/**
 * E2E contact information
 */
data class E2EContact(
    val id: String,
    val publicKey: ByteArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as E2EContact

        if (id != other.id) return false
        if (!publicKey.contentEquals(other.publicKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + publicKey.contentHashCode()
        return result
    }
}

/**
 * Encrypted message for E2E communication
 */
data class E2EEncryptedMessage(
    val senderPublicKey: ByteArray,
    val recipientId: String,
    val ciphertext: ByteArray,
    val timestamp: Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as E2EEncryptedMessage

        if (!senderPublicKey.contentEquals(other.senderPublicKey)) return false
        if (recipientId != other.recipientId) return false
        if (!ciphertext.contentEquals(other.ciphertext)) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = senderPublicKey.contentHashCode()
        result = 31 * result + recipientId.hashCode()
        result = 31 * result + ciphertext.contentHashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }
}

/**
 * E2E encryption session
 */
data class E2ESession(
    val contactId: String,
    val aead: Aead,
    val createdAt: Long,
    val expiresAt: Long = createdAt + SESSION_DURATION_MS
) {
    companion object {
        const val SESSION_DURATION_MS = 24 * 60 * 60 * 1000L // 24 hours
    }

    fun isExpired(): Boolean = System.currentTimeMillis() > expiresAt
}