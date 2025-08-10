package com.appvalence.bluetooth.api

import kotlinx.coroutines.flow.Flow

/**
 * High-performance Bluetooth discovery that combines Classic discovery and BLE scanning.
 * Emits unique devices by MAC address with latest known name.
 */
interface HighPerformanceScanner {
    suspend fun startScan(): Flow<DiscoveredDevice>
    suspend fun stopScan()
}


