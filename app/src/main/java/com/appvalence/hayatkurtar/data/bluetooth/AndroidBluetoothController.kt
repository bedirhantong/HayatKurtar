package com.appvalence.hayatkurtar.data.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow

class AndroidBluetoothController(
    context: Context,
) : BluetoothController {
    private val adapter: BluetoothAdapter? =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    private val incomingFlow = MutableSharedFlow<ByteArray>(extraBufferCapacity = 64, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override suspend fun startScan(): Flow<DiscoveredDevice> = flow {
        runCatching {
            adapter?.bondedDevices
        }.onSuccess { set ->
            set?.forEach { dev -> emit(DiscoveredDevice(dev.name, dev.address)) }
        }.onFailure {
            // swallow SecurityException if permissions not yet granted
        }
    }

    override suspend fun stopScan() { /* no-op for demo */ }

    override suspend fun connect(address: String): Boolean {
        val localAdapter = adapter ?: return false
        return try {
            if (!BluetoothAdapter.checkBluetoothAddress(address)) return false
            val device: BluetoothDevice = localAdapter.getRemoteDevice(address)
            device.address.isNotBlank()
        } catch (_: IllegalArgumentException) {
            false
        } catch (_: SecurityException) {
            false
        }
    }

    override suspend fun disconnect() { /* no-op for demo */ }

    override fun incoming(): Flow<ByteArray> = incomingFlow

    override suspend fun send(bytes: ByteArray) {
        incomingFlow.emit(bytes)
    }

    override suspend fun findFirstAvailable(): String? = try {
        adapter?.bondedDevices?.firstOrNull()?.address
    } catch (_: SecurityException) {
        null
    }

    override fun isEnabled(): Boolean = adapter?.isEnabled == true
}


