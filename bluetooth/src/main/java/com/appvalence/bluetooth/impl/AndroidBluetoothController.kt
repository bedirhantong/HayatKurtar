package com.appvalence.bluetooth.impl

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.appvalence.bluetooth.api.BluetoothController
import com.appvalence.bluetooth.api.DiscoveredDevice
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.io.InputStream
import java.io.OutputStream
import java.util.UUID

class AndroidBluetoothController(
    context: Context,
) : BluetoothController {
    private val adapter: BluetoothAdapter? =
        (context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    private val appContext: Context = context.applicationContext

    private val incomingFlow = MutableSharedFlow<ByteArray>(extraBufferCapacity = 64, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    private var scanReceiver: BroadcastReceiver? = null
    private val ioScope = CoroutineScope(Dispatchers.IO)

    private val sppUuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private var serverJob: Job? = null
    private var insecureServerJob: Job? = null
    @Volatile private var currentIn: InputStream? = null
    @Volatile private var currentOut: OutputStream? = null
    @Volatile private var currentDeviceAddress: String? = null
    private val connectionState = MutableStateFlow(false)

    override suspend fun startScan(): Flow<DiscoveredDevice> = callbackFlow {
        val localAdapter = adapter ?: run {
            close()
            return@callbackFlow
        }

        val seen = LinkedHashMap<String, DiscoveredDevice>()

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val address = device?.address ?: return
                        val rssi: Int? = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE)
                            .takeIf { it != Short.MIN_VALUE }?.toInt()
                        val item = DiscoveredDevice(device?.name, address, rssi = rssi)
                        val wasNew = !seen.containsKey(address)
                        seen[address] = item
                        if (wasNew) trySend(item)
                    }
                    BluetoothAdapter.ACTION_DISCOVERY_FINISHED -> {
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
        try { appContext.registerReceiver(receiver, filter) } catch (_: Exception) {}

        try {
            if (localAdapter.isDiscovering) runCatching { localAdapter.cancelDiscovery() }
            runCatching { localAdapter.startDiscovery() }
        } catch (_: SecurityException) {}

        awaitClose {
            runCatching { localAdapter.cancelDiscovery() }
            runCatching { scanReceiver?.let { appContext.unregisterReceiver(it) } }
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
            runCatching { if (localAdapter.isDiscovering) localAdapter.cancelDiscovery() }
            runCatching {
                val s = device.createRfcommSocketToServiceRecord(sppUuid)
                s.connect()
                setActiveSocket(s.inputStream, s.outputStream, device.address)
                connectionState.value = true
                return true
            }.onFailure { }
            val insecure = runCatching { device.createInsecureRfcommSocketToServiceRecord(sppUuid) }.getOrNull()
            if (insecure != null) {
                insecure.connect()
                setActiveSocket(insecure.inputStream, insecure.outputStream, device.address)
                connectionState.value = true
                return true
            }
            false
        } catch (_: IllegalArgumentException) {
            false
        } catch (_: SecurityException) {
            false
        } catch (_: Exception) {
            false
        }
    }

    override suspend fun disconnect() {
        runCatching { currentIn?.close() }
        runCatching { currentOut?.close() }
        currentIn = null
        currentOut = null
        currentDeviceAddress = null
        connectionState.value = false
    }

    override fun incoming(): Flow<ByteArray> = incomingFlow

    override suspend fun send(bytes: ByteArray) {
        val out = currentOut
        if (out != null) {
            runCatching {
                out.write(bytes)
                out.flush()
            }.onFailure {
                incomingFlow.emit(bytes)
            }
        } else {
            incomingFlow.emit(bytes)
        }
    }

    override suspend fun findFirstAvailable(): String? = try {
        adapter?.bondedDevices?.firstOrNull()?.address
    } catch (_: SecurityException) { null }

    override fun isEnabled(): Boolean = adapter?.isEnabled == true

    override fun observeConnectionState(): Flow<Boolean> = connectionState.asStateFlow()

    override fun getCurrentPeerAddress(): String? = currentDeviceAddress

    init {
        startServerIfPossible()
    }

    private fun startServerIfPossible() {
        val localAdapter = adapter ?: return
        serverJob?.cancel()
        insecureServerJob?.cancel()
        serverJob = ioScope.launch {
            try {
                val server = localAdapter.listenUsingRfcommWithServiceRecord("HK_CHAT_SPP", sppUuid)
                while (true) {
                    val socket = server.accept()
                    val input = socket.inputStream
                    val output = socket.outputStream
                    setActiveSocket(input, output, socket.remoteDevice?.address ?: "")
                    connectionState.value = true
                }
            } catch (_: Exception) {
            }
        }
        insecureServerJob = ioScope.launch {
            try {
                val server = localAdapter.listenUsingInsecureRfcommWithServiceRecord("HK_CHAT_SPP_INSECURE", sppUuid)
                while (true) {
                    val socket = server.accept()
                    val input = socket.inputStream
                    val output = socket.outputStream
                    setActiveSocket(input, output, socket.remoteDevice?.address ?: "")
                    connectionState.value = true
                }
            } catch (_: Exception) { }
        }
    }

    private fun setActiveSocket(`in`: InputStream, out: OutputStream, address: String) {
        runCatching { currentIn?.close() }
        runCatching { currentOut?.close() }
        currentIn = `in`
        currentOut = out
        currentDeviceAddress = address
        ioScope.launch {
            val buffer = ByteArray(1024)
            while (true) {
                val read = runCatching { currentIn?.read(buffer) ?: -1 }.getOrDefault(-1)
                if (read <= 0) break
                val data = buffer.copyOf(read)
                incomingFlow.emit(data)
            }
        }
    }
}


