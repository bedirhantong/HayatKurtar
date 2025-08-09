package com.appvalence.hayatkurtar.data.repository

import com.appvalence.hayatkurtar.data.bluetooth.BluetoothController
import com.appvalence.hayatkurtar.data.crypto.CryptoService
import com.appvalence.hayatkurtar.data.local.MessageDao
import com.appvalence.hayatkurtar.data.local.MessageEntity
import com.appvalence.hayatkurtar.domain.model.ChatMessage
import com.appvalence.hayatkurtar.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ChatRepositoryImpl @Inject constructor(
    private val bluetooth: BluetoothController,
    private val dao: MessageDao,
    private val crypto: CryptoService,
) : ChatRepository {

    private val discoveredDevices = MutableStateFlow<List<com.appvalence.hayatkurtar.domain.model.DeviceInfo>>(emptyList())
    @Volatile private var currentPeerAddress: String? = null

    override suspend fun scanDevices() {
        val list = mutableListOf<com.appvalence.hayatkurtar.domain.model.DeviceInfo>()
        bluetooth.startScan().collect { d ->
            list.add(com.appvalence.hayatkurtar.domain.model.DeviceInfo(d.name, d.address))
            discoveredDevices.value = list.toList()
        }
    }

    override suspend fun connectFirstAvailable(): Boolean {
        val address = bluetooth.findFirstAvailable() ?: return false
        val ok = bluetooth.connect(address)
        if (ok) currentPeerAddress = address
        return ok
    }

    override suspend fun connect(address: String): Boolean {
        if (!android.bluetooth.BluetoothAdapter.checkBluetoothAddress(address)) return false
        val ok = bluetooth.connect(address)
        if (ok) currentPeerAddress = address
        return ok
    }

    override suspend fun disconnect() {
        bluetooth.disconnect()
        currentPeerAddress = null
    }

    override suspend fun sendMessage(plainText: String) {
        val aead = crypto.aead()
        val cipher = aead.encrypt(plainText.encodeToByteArray(), null)
        bluetooth.send(cipher)
        dao.insert(
            MessageEntity(
                sender = "Me",
                content = plainText,
                timestamp = System.currentTimeMillis(),
                peerAddress = currentPeerAddress ?: bluetooth.findFirstAvailable() ?: "",
            )
        )
    }

    override fun observeMessages(): Flow<List<ChatMessage>> =
        dao.observeAll().map { list ->
            list.map { e ->
                ChatMessage(id = e.id, sender = e.sender, content = e.content, timestamp = e.timestamp, peerAddress = e.peerAddress)
            }
        }

    override fun observeMessagesByPeer(peer: String): Flow<List<ChatMessage>> =
        dao.observeByPeer(peer).map { list ->
            list.map { e ->
                ChatMessage(id = e.id, sender = e.sender, content = e.content, timestamp = e.timestamp, peerAddress = e.peerAddress)
            }
        }

    override fun isBluetoothEnabled(): Boolean = bluetooth.isEnabled()

    override fun observeDiscoveredDevices(): Flow<List<com.appvalence.hayatkurtar.domain.model.DeviceInfo>> =
        discoveredDevices.asStateFlow()

    override suspend fun deleteChatByPeer(peer: String) {
        dao.deleteByPeer(peer)
    }
}


