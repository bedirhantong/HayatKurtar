package com.appvalence.hayatkurtar.data.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow

class AndroidBluetoothController(
    context: Context,
) : BluetoothController {
    private val adapter: BluetoothAdapter? =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    private val appContext: Context = context.applicationContext

    private val incomingFlow = MutableSharedFlow<ByteArray>(extraBufferCapacity = 64, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private var scanReceiver: BroadcastReceiver? = null

    override suspend fun startScan(): Flow<DiscoveredDevice> = callbackFlow {
        val localAdapter = adapter ?: run {
            close()
            return@callbackFlow
        }

        val seen = mutableSetOf<String>()

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val address = device?.address ?: return
                        if (seen.add(address)) {
                            trySend(DiscoveredDevice(device.name, address))
                        }
                    }
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
                        // Complete this scan session
                        // We do not restart automatically; caller may call startScan again
                        close()
                    }
                }
            }
        }

        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
            addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
        }

        scanReceiver = receiver
        try {
            appContext.registerReceiver(receiver, filter)
        } catch (_: Exception) {
            // ignore
        }

        try {
            if (localAdapter.isDiscovering) {
                runCatching { localAdapter.cancelDiscovery() }
            }
            runCatching { localAdapter.startDiscovery() }
        } catch (_: SecurityException) {
            // Permissions not granted yet
        }

        awaitClose {
            runCatching { localAdapter.cancelDiscovery() }
            runCatching {
                scanReceiver?.let { appContext.unregisterReceiver(it) }
            }
            scanReceiver = null
        }
    }

    override suspend fun stopScan() {
        val localAdapter = adapter ?: return
        runCatching { localAdapter.cancelDiscovery() }
        runCatching { scanReceiver?.let { appContext.unregisterReceiver(it) } }
        scanReceiver = null
    }

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


