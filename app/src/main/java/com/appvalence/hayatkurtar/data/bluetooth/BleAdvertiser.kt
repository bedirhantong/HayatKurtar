package com.appvalence.hayatkurtar.data.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.AdvertiseCallback
import android.bluetooth.le.AdvertiseData
import android.bluetooth.le.AdvertiseSettings
import android.bluetooth.le.BluetoothLeAdvertiser
import android.content.Context
import java.util.UUID

interface BleAdvertiser {
    fun start(serviceUuid: UUID? = null): Boolean
    fun stop()
    fun isAdvertising(): Boolean
}

class AndroidBleAdvertiser(context: Context) : BleAdvertiser {
    private val appContext = context.applicationContext
    private val adapter: BluetoothAdapter? =
        (appContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager).adapter
    private var advertiser: BluetoothLeAdvertiser? = null
    private var callback: AdvertiseCallback? = null
    @Volatile private var advertising: Boolean = false

    @SuppressLint("MissingPermission")
    override fun start(serviceUuid: UUID?): Boolean {
        val adv = adapter?.bluetoothLeAdvertiser ?: return false
        val settings = AdvertiseSettings.Builder()
            .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
            .setConnectable(true)
            .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
            .build()
        val dataBuilder = AdvertiseData.Builder()
            .setIncludeDeviceName(true)
        serviceUuid?.let { uuid ->
            dataBuilder.addServiceUuid(android.os.ParcelUuid(uuid))
        }
        val data = dataBuilder.build()
        val c = object : AdvertiseCallback() {}
        callback = c
        advertiser = adv
        return runCatching {
            adv.startAdvertising(settings, data, c)
            advertising = true
            true
        }.getOrElse { false }
    }

    @SuppressLint("MissingPermission")
    override fun stop() {
        val adv = advertiser
        val c = callback
        if (adv != null && c != null) runCatching { adv.stopAdvertising(c) }
        advertising = false
        advertiser = null
        callback = null
    }

    override fun isAdvertising(): Boolean = advertising
}


