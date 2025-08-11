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
import kotlinx.coroutines.launch
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
    private val repoScope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.IO)
    private var pendingIncoming: ByteArray = ByteArray(0)
    private val ENCRYPTION_ENABLED: Boolean = false // TODO: enable after Noise handshake

    init {
        // Observe incoming bytes and persist as messages
        repoScope.launch {
            try {
                bluetooth.incoming().collect { chunk ->
                    // Append to pending buffer
                    pendingIncoming = pendingIncoming + chunk
                    // Process frames: [4-byte big-endian length][payload]
                    while (pendingIncoming.size >= 4) {
                        val len = ((pendingIncoming[0].toInt() and 0xFF) shl 24) or
                                ((pendingIncoming[1].toInt() and 0xFF) shl 16) or
                                ((pendingIncoming[2].toInt() and 0xFF) shl 8) or
                                (pendingIncoming[3].toInt() and 0xFF)
                        if (len < 0 || len > 1024 * 1024) {
                            // Corrupt length; drop buffer
                            pendingIncoming = ByteArray(0)
                            break
                        }
                        if (pendingIncoming.size - 4 < len) {
                            // Wait for more bytes
                            break
                        }
                        val payload = pendingIncoming.copyOfRange(4, 4 + len)
                        pendingIncoming = pendingIncoming.copyOfRange(4 + len, pendingIncoming.size)

                        // Decrypt if enabled; otherwise treat as UTF-8 text
                        val text: String = if (ENCRYPTION_ENABLED) {
                            val aead = runCatching { crypto.aead() }.getOrNull()
                            runCatching { aead?.decrypt(payload, null)?.decodeToString() ?: "" }.getOrDefault("")
                        } else {
                            runCatching { payload.toString(Charsets.UTF_8) }.getOrDefault("")
                        }
                        if (text.isNotBlank()) {
                            val peer = currentPeerAddress ?: bluetooth.getCurrentPeerAddress() ?: bluetooth.findFirstAvailable() ?: ""
                            dao.insert(
                                MessageEntity(
                                    sender = peer.ifBlank { "Peer" },
                                    content = text,
                                    timestamp = System.currentTimeMillis(),
                                    peerAddress = peer,
                                )
                            )
                        }
                    }
                }
            } catch (_: Exception) { }
        }
    }

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
        val payload = if (ENCRYPTION_ENABLED) {
            val aead = crypto.aead()
            aead.encrypt(plainText.encodeToByteArray(), null)
        } else {
            plainText.encodeToByteArray()
        }
        val len = payload.size
        val header = byteArrayOf(
            ((len ushr 24) and 0xFF).toByte(),
            ((len ushr 16) and 0xFF).toByte(),
            ((len ushr 8) and 0xFF).toByte(),
            (len and 0xFF).toByte()
        )
        bluetooth.send(header + payload)
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


