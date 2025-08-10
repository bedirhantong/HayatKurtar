package com.appvalence.bluetooth.impl

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import com.appvalence.bluetooth.api.DiscoveredDevice
import com.appvalence.bluetooth.api.HighPerformanceScanner
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

        val seen = LinkedHashMap<String, DiscoveredDevice>()

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                when (intent?.action) {
                    BluetoothDevice.ACTION_FOUND -> {
                        val device: BluetoothDevice? =
                            intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                        val address = device?.address ?: return
                        val item = DiscoveredDevice(device?.name, address)
                        val wasNew = !seen.containsKey(address)
                        seen[address] = item
                        if (wasNew) trySend(item)
                    }
                }
            }
        }

        val filter = IntentFilter().apply { addAction(BluetoothDevice.ACTION_FOUND) }
        classicReceiver = receiver
        runCatching { appContext.registerReceiver(receiver, filter) }

        runCatching {
            if (localAdapter.isDiscovering) localAdapter.cancelDiscovery()
            localAdapter.startDiscovery()
        }

        val scanner = runCatching { localAdapter.bluetoothLeScanner }.getOrNull()
        leScanner = scanner
        val callback = object : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                val address = result.device?.address ?: return
                val name = result.device?.name
                    ?: result.scanRecord?.deviceName
                    ?: result.scanRecord?.deviceName.takeUnless { it.isNullOrBlank() }
                val rssi = result.rssi
                val tx = if (Build.VERSION.SDK_INT >= 26) result.txPower else null
                val item = DiscoveredDevice(name, address, rssi = rssi, txPower = tx)
                val wasNew = !seen.containsKey(address)
                seen[address] = item
                if (wasNew) trySend(item)
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>) {
                for (r in results) {
                    val address = r.device?.address ?: continue
                    val name = r.device?.name
                        ?: r.scanRecord?.deviceName
                        ?: r.scanRecord?.deviceName.takeUnless { it.isNullOrBlank() }
                    val rssi = r.rssi
                    val tx = if (Build.VERSION.SDK_INT >= 26) r.txPower else null
                    val item = DiscoveredDevice(name, address, rssi = rssi, txPower = tx)
                    val wasNew = !seen.containsKey(address)
                    seen[address] = item
                    if (wasNew) trySend(item)
                }
            }
        }
        leCallback = callback
        val settings = ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build()
        runCatching { scanner?.startScan(null, settings, callback) }

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


