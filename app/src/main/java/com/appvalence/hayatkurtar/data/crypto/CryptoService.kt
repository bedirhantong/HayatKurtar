package com.appvalence.hayatkurtar.data.crypto

import androidx.security.crypto.MasterKey
import com.google.crypto.tink.Aead
import com.google.crypto.tink.KeysetHandle
import com.google.crypto.tink.aead.AeadConfig
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import com.google.crypto.tink.subtle.X25519
import android.content.Context

class CryptoService(private val context: Context) {

    private val keysetAlias = "hk_aead_keys"
    private val prefFile = "hk_key_prefs"
    private val masterKeyAliasString = "hk_master_key"
    private val masterKey = MasterKey.Builder(context, masterKeyAliasString)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    init {
        AeadConfig.register()
    }

    fun aead(): Aead {
        val handle: KeysetHandle = AndroidKeysetManager.Builder()
            .withSharedPref(context, keysetAlias, prefFile)
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri("android-keystore://${masterKeyAliasString}")
            .build()
            .keysetHandle
        return handle.getPrimitive(Aead::class.java)
    }

    fun generateDhKeyPair(): Pair<ByteArray, ByteArray> {
        val privateKey = X25519.generatePrivateKey()
        val publicKey = X25519.publicFromPrivate(privateKey)
        return privateKey to publicKey
    }

    fun deriveSharedKey(ourPrivate: ByteArray, theirPublic: ByteArray): ByteArray {
        return X25519.computeSharedSecret(ourPrivate, theirPublic)
    }
}


