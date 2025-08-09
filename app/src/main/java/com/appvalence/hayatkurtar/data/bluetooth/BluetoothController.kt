package com.appvalence.hayatkurtar.data.bluetooth

import kotlinx.coroutines.flow.Flow

data class DiscoveredDevice(val name: String?, val address: String)

interface BluetoothController {
    suspend fun startScan(): Flow<DiscoveredDevice>
    suspend fun stopScan()
    suspend fun connect(address: String): Boolean
    suspend fun disconnect()
    fun incoming(): Flow<ByteArray>
    suspend fun send(bytes: ByteArray)
    suspend fun findFirstAvailable(): String?
    fun isEnabled(): Boolean
}


