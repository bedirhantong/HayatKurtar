package com.appvalence.hayatkurtar.data.repository

import com.appvalence.bluetooth.api.BluetoothController
import com.appvalence.bluetooth.api.HighPerformanceScanner
import com.appvalence.bluetooth.api.DistanceEstimator
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
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.math.pow
import kotlin.math.roundToInt

class ChatRepositoryImpl @Inject constructor(
    private val bluetooth: BluetoothController,
    private val highScanner: HighPerformanceScanner,
    private val dao: MessageDao,
    private val crypto: CryptoService,
    private val distanceEstimator: DistanceEstimator,
) : ChatRepository {

    private val discoveredDevices = MutableStateFlow<List<com.appvalence.hayatkurtar.domain.model.DeviceInfo>>(emptyList())
    @Volatile private var currentPeerAddress: String? = null

    override suspend fun scanDevices() {
        val list = mutableListOf<com.appvalence.hayatkurtar.domain.model.DeviceInfo>()
        discoveredDevices.value = emptyList()
        try {
            withTimeoutOrNull(25_000) {
                highScanner.startScan().collect { d ->
                    val distance = distanceEstimator.estimateDistanceMeters(d.address, d.rssi, d.txPower)
                    list.add(
                        com.appvalence.hayatkurtar.domain.model.DeviceInfo(
                            name = d.name,
                            address = d.address,
                            rssi = d.rssi,
                            txPower = d.txPower,
                            estimatedDistanceMeters = distance
                        )
                    )
                    discoveredDevices.value = list.toList()
                }
            }
        } finally {
            runCatching { highScanner.stopScan() }
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

    override fun recalculateDiscoveredDistances() {
        val current = discoveredDevices.value
        if (current.isEmpty()) return
        val updated = current.map { d ->
            val newDist = distanceEstimator.estimateDistanceMeters(d.address, d.rssi, d.txPower)
            d.copy(estimatedDistanceMeters = newDist)
        }
        discoveredDevices.value = updated
    }
}


