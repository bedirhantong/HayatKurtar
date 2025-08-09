package com.appvalence.hayatkurtar.domain.usecase

import android.content.Context
import android.content.Intent
import com.appvalence.hayatkurtar.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import com.appvalence.hayatkurtar.domain.model.ChatMessage
import javax.inject.Inject
import com.appvalence.hayatkurtar.domain.model.DeviceInfo
import com.appvalence.hayatkurtar.data.bluetooth.HighPerformanceDiscoveryService
import dagger.hilt.android.qualifiers.ApplicationContext

class ScanDevicesUseCase @Inject constructor(private val repo: ChatRepository) {
    suspend operator fun invoke() = repo.scanDevices()
}

class ConnectFirstAvailableUseCase @Inject constructor(private val repo: ChatRepository) {
    suspend operator fun invoke(): Boolean = repo.connectFirstAvailable()
}

class ConnectDeviceUseCase @Inject constructor(private val repo: ChatRepository) {
    suspend operator fun invoke(address: String): Boolean = repo.connect(address)
}

class DisconnectUseCase @Inject constructor(private val repo: ChatRepository) {
    suspend operator fun invoke() = repo.disconnect()
}

class SendMessageUseCase @Inject constructor(private val repo: ChatRepository) {
    suspend operator fun invoke(text: String) = repo.sendMessage(text)
}

class ObserveMessagesUseCase @Inject constructor(private val repo: ChatRepository) {
    operator fun invoke(): Flow<List<ChatMessage>> = repo.observeMessages()
}

class ObserveMessagesByPeerUseCase @Inject constructor(private val repo: ChatRepository) {
    operator fun invoke(peer: String): Flow<List<ChatMessage>> = repo.observeMessagesByPeer(peer)
}

class DeleteChatByPeerUseCase @Inject constructor(private val repo: ChatRepository) {
    suspend operator fun invoke(peer: String) = repo.deleteChatByPeer(peer)
}

class IsBluetoothEnabledUseCase @Inject constructor(private val repo: ChatRepository) {
    operator fun invoke(): Boolean = repo.isBluetoothEnabled()
}

class ObserveDiscoveredDevicesUseCase @Inject constructor(private val repo: ChatRepository) {
    operator fun invoke(): Flow<List<DeviceInfo>> = repo.observeDiscoveredDevices()
}

class StartDiscoveryServiceUseCase @Inject constructor(@ApplicationContext private val context: Context) {
    operator fun invoke() {
        val i = Intent(context, HighPerformanceDiscoveryService::class.java)
        context.startForegroundService(i)
    }
}

class StopDiscoveryServiceUseCase @Inject constructor(@ApplicationContext private val context: Context) {
    operator fun invoke() {
        val i = Intent(context, HighPerformanceDiscoveryService::class.java)
        context.stopService(i)
    }
}


