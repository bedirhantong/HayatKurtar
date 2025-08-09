package com.appvalence.hayatkurtar.domain.repository

import com.appvalence.hayatkurtar.domain.model.ChatMessage
import kotlinx.coroutines.flow.Flow
import com.appvalence.hayatkurtar.domain.model.DeviceInfo

interface ChatRepository {
    suspend fun scanDevices()
    fun observeDiscoveredDevices(): Flow<List<DeviceInfo>>
    suspend fun connectFirstAvailable(): Boolean
    suspend fun connect(address: String): Boolean
    suspend fun disconnect()
    suspend fun sendMessage(plainText: String)
    fun observeMessages(): Flow<List<ChatMessage>>
    fun observeMessagesByPeer(peer: String): Flow<List<ChatMessage>>
    fun isBluetoothEnabled(): Boolean
    suspend fun deleteChatByPeer(peer: String)
}


