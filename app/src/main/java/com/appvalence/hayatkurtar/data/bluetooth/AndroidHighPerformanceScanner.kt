package com.appvalence.hayatkurtar.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AndroidHighPerformanceScanner(
    context: Context,
) : HighPerformanceScanner {

    private val appContext: Context = context.applicationContext
    private val adapter: BluetoothAdapter? =
        (appContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter

    private var classicReceiver: BroadcastReceiver? = null
    private var leScanner: BluetoothLeScanner? = null
    private var leCallback: ScanCallback? = null

    @SuppressLint("MissingPermission")
    override suspend fun startScan(): Flow<DiscoveredDevice> = callbackFlow {
        val localAdapter = adapter ?: run {
            close()
            return@callbackFlow
        }

        val seen = mutableSetOf<String>()

        // Classic discovery receiver
        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val address = device?.address ?: return
                        if (seen.add(address)) {
                            trySend(DiscoveredDevice(device.name, address))
                        }
                    }
                }
            }
        }

        val filter = IntentFilter().apply {
            addAction(BluetoothDevice.ACTION_FOUND)
        }

        classicReceiver = receiver
        runCatching { appContext.registerReceiver(receiver, filter) }

        // Start Classic discovery
        runCatching {
            if (localAdapter.isDiscovering) localAdapter.cancelDiscovery()
            localAdapter.startDiscovery()
        }

        // Start BLE scanning in parallel
        val scanner = runCatching { localAdapter.bluetoothLeScanner }.getOrNull()
        leScanner = scanner
        val callback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                val address = result.device?.address ?: return
                if (seen.add(address)) {
                    val name = result.device?.name ?: result.scanRecord?.deviceName
                    trySend(DiscoveredDevice(name, address))
                }
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>) {
                for (r in results) {
                    val address = r.device?.address ?: continue
                    if (seen.add(address)) {
                        val name = r.device?.name ?: r.scanRecord?.deviceName
                        trySend(DiscoveredDevice(name, address))
                    }
                }
            }
        }
        leCallback = callback
        runCatching { scanner?.startScan(callback) }

        awaitClose {
            runCatching { localAdapter.cancelDiscovery() }
            runCatching { classicReceiver?.let { appContext.unregisterReceiver(it) } }
            classicReceiver = null
            runCatching { scanner?.stopScan(callback) }
            leCallback = null
        }
    }

    @SuppressLint("MissingPermission")
    override suspend fun stopScan() {
        val localAdapter = adapter ?: return
        runCatching { localAdapter.cancelDiscovery() }
        runCatching { classicReceiver?.let { appContext.unregisterReceiver(it) } }
        classicReceiver = null
        val scanner = leScanner
        val callback = leCallback
        if (scanner != null && callback != null) {
            runCatching { scanner.stopScan(callback) }
        }
        leScanner = null
        leCallback = null
    }
}


