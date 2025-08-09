package com.appvalence.hayatkurtar.data.bluetooth

import kotlinx.coroutines.flow.Flow

/**
 * High-performance Bluetooth discovery that combines Classic discovery and BLE scanning.
 * Emits unique devices by MAC address with latest known name.
 */
interface HighPerformanceScanner {
    /** Start a combined Classic + BLE scan session and emit discovered devices as they appear. */
    suspend fun startScan(): Flow<DiscoveredDevice>

    /** Stop the current scan session if active. */
    suspend fun stopScan()
}


