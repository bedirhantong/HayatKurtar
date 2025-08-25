package com.appvalence.hayatkurtar.data.system

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.appvalence.hayatkurtar.domain.system.*
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer
import com.appvalence.hayatkurtar.domain.transport.Peer
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Android implementation of SystemStatusManager
 * Dependency Inversion: Depends on abstractions (TransportMultiplexer)
 * Single Responsibility: Manages system status for Android platform
 */
@Singleton
class AndroidSystemStatusManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val transportMultiplexer: TransportMultiplexer
) : SystemStatusManager {

    private val _systemStatus = MutableStateFlow(createInitialStatus())
    private val _discoveredPeers = MutableStateFlow<List<DiscoveredPeer>>(emptyList())
    private val _deviceVisibility = MutableStateFlow(createInitialVisibilityStatus())

    init {
        // Start monitoring system status changes
        monitorSystemChanges()
    }

    override fun getSystemStatus(): Flow<SystemStatus> = _systemStatus.asStateFlow()

    override suspend fun requestPermissions(): PermissionResult {
        // The actual permission request will be triggered by UI layer
        // This method notifies the system to update status after permissions are handled
        updateSystemStatus()
        
        val permissionStatus = checkPermissionStatus()
        return if (permissionStatus.allGranted) {
            PermissionResult.Success
        } else {
            val granted = mutableListOf<String>()
            val denied = mutableListOf<String>()

            if (permissionStatus.bluetooth == PermissionState.GRANTED) granted.add("Bluetooth")
            else denied.add("Bluetooth")

            if (permissionStatus.wifiDirect == PermissionState.GRANTED) granted.add("Wi-Fi Direct")
            else denied.add("Wi-Fi Direct")

            if (permissionStatus.location == PermissionState.GRANTED) granted.add("Location")
            else denied.add("Location")

            PermissionResult.Partial(granted, denied)
        }
    }

    override suspend fun enableRequiredServices(): ServiceEnableResult {
        // The actual service enabling will be handled by the UI layer
        // This method checks current status and notifies for updates
        updateSystemStatus()
        
        val serviceStatus = checkServiceStatus()
        val enabled = mutableListOf<String>()
        val failed = mutableListOf<String>()

        // Check each service
        if (serviceStatus.bluetooth == ServiceState.ENABLED) enabled.add("Bluetooth")
        else failed.add("Bluetooth")

        if (serviceStatus.wifi == ServiceState.ENABLED) enabled.add("Wi-Fi")
        else failed.add("Wi-Fi")

        return if (failed.isEmpty()) {
            ServiceEnableResult.Success
        } else {
            ServiceEnableResult.Partial(enabled, failed)
        }
    }

    override suspend fun startDiscoveryAndAdvertising(): DiscoveryResult {
        return try {
            // Start discovery on all available transports
            transportMultiplexer.startDiscovery()

            // Update status
            updateSystemStatus()

            DiscoveryResult.Success
        } catch (e: Exception) {
            DiscoveryResult.Failed(e.message ?: "Unknown error starting discovery")
        }
    }

    override suspend fun stopDiscoveryAndAdvertising() {
        try {
            transportMultiplexer.stopDiscovery()
            updateSystemStatus()
        } catch (e: Exception) {
            // Log error but don't throw
        }
    }

    override fun getDiscoveredPeers(): Flow<List<DiscoveredPeer>> = _discoveredPeers.asStateFlow()

    override fun getDeviceVisibility(): Flow<DeviceVisibilityStatus> = _deviceVisibility.asStateFlow()

    private fun createInitialStatus(): SystemStatus {
        val permissionStatus = checkPermissionStatus()
        val serviceStatus = checkServiceStatus()
        val connectivityStatus = checkConnectivityStatus()
        val discoveryStatus = DiscoveryStatus(
            isDiscovering = false,
            isAdvertising = false,
            discoveredDeviceCount = 0,
            lastDiscoveryTime = null
        )

        val overallState = determineOverallState(permissionStatus, serviceStatus)

        return SystemStatus(
            overallState = overallState,
            permissions = permissionStatus,
            services = serviceStatus,
            connectivity = connectivityStatus,
            discovery = discoveryStatus
        )
    }

    private fun createInitialVisibilityStatus(): DeviceVisibilityStatus {
        return DeviceVisibilityStatus(
            isVisible = false,
            deviceName = getDeviceName(),
            advertisementStrength = 0,
            visibilityRange = VisibilityRange.SHORT
        )
    }

    private fun monitorSystemChanges() {
        // Monitor transport multiplexer for peer changes
        // This would be implemented with proper Flow observation
        // For now, update periodically but less frequently to avoid flickering
        kotlinx.coroutines.GlobalScope.launch {
            while (true) {
                updateSystemStatus()
                updateDiscoveredPeers()
                updateDeviceVisibility()
                delay(3000) // Update every 3 seconds to reduce flickering
            }
        }
    }

    private fun updateSystemStatus() {
        val permissionStatus = checkPermissionStatus()
        val serviceStatus = checkServiceStatus()
        val connectivityStatus = checkConnectivityStatus()
        val discoveryStatus = checkDiscoveryStatus()

        val overallState = determineOverallState(permissionStatus, serviceStatus)
        val errors = checkSystemErrors(permissionStatus, serviceStatus)

        _systemStatus.value = SystemStatus(
            overallState = overallState,
            permissions = permissionStatus,
            services = serviceStatus,
            connectivity = connectivityStatus,
            discovery = discoveryStatus,
            errors = errors
        )
    }

    private fun updateDiscoveredPeers() {
        try {
            val connectedPeers = transportMultiplexer.getConnectedPeers()
            val discoveredPeers = connectedPeers.map { peer ->
                DiscoveredPeer(
                    id = peer.id,
                    name = peer.name ?: "Unknown Device",
                    transportType = peer.transport.toString(),
                    signalStrength = 75, // Placeholder - would get from transport
                    distance = "~20m", // Placeholder - would calculate
                    isConnectable = true,
                    lastSeen = System.currentTimeMillis(),
                    capabilities = PeerCapabilities(
                        supportsEncryption = true,
                        supportsMeshRelay = true,
                        maxHopCount = 5,
                        preferredTransports = listOf("Bluetooth", "Wi-Fi Direct")
                    )
                )
            }
            _discoveredPeers.value = discoveredPeers
        } catch (e: Exception) {
            // Handle error
        }
    }

    private fun updateDeviceVisibility() {
        // Check if we're currently advertising/discoverable
        val isVisible = checkIfDeviceIsVisible()
        val strength = if (isVisible) 85 else 0

        _deviceVisibility.value = DeviceVisibilityStatus(
            isVisible = isVisible,
            deviceName = getDeviceName(),
            advertisementStrength = strength,
            visibilityRange = VisibilityRange.MEDIUM
        )
    }

    private fun checkPermissionStatus(): PermissionStatus {
        val bluetoothState = checkBluetoothPermissions()
        val wifiState = checkWiFiDirectPermissions()
        val locationState = checkLocationPermissions()

        return PermissionStatus(
            bluetooth = bluetoothState,
            wifiDirect = wifiState,
            location = locationState,
            allGranted = bluetoothState == PermissionState.GRANTED &&
                        wifiState == PermissionState.GRANTED &&
                        locationState == PermissionState.GRANTED
        )
    }

    private fun checkServiceStatus(): ServiceStatus {
        val bluetoothState = checkBluetoothService()
        val wifiState = checkWiFiService()
        val locationState = checkLocationService()

        return ServiceStatus(
            bluetooth = bluetoothState,
            wifi = wifiState,
            location = locationState,
            allEnabled = bluetoothState == ServiceState.ENABLED &&
                        wifiState == ServiceState.ENABLED &&
                        locationState == ServiceState.ENABLED
        )
    }

    private fun checkConnectivityStatus(): ConnectivityStatus {
        try {
            val connectedPeers = transportMultiplexer.getConnectedPeers().size
            val quality = when {
                connectedPeers >= 3 -> ConnectionQuality.EXCELLENT
                connectedPeers >= 2 -> ConnectionQuality.GOOD
                connectedPeers >= 1 -> ConnectionQuality.FAIR
                else -> ConnectionQuality.NONE
            }

            return ConnectivityStatus(
                connectedPeers = connectedPeers,
                connectionQuality = quality,
                meshNetworkSize = connectedPeers + 1, // Include self
                isConnectedToMesh = connectedPeers > 0
            )
        } catch (e: Exception) {
            return ConnectivityStatus(
                connectedPeers = 0,
                connectionQuality = ConnectionQuality.NONE,
                meshNetworkSize = 1,
                isConnectedToMesh = false
            )
        }
    }

    private fun checkDiscoveryStatus(): DiscoveryStatus {
        // This would check transport multiplexer discovery state
        return DiscoveryStatus(
            isDiscovering = false, // Placeholder
            isAdvertising = false, // Placeholder
            discoveredDeviceCount = _discoveredPeers.value.size,
            lastDiscoveryTime = System.currentTimeMillis()
        )
    }

    private fun determineOverallState(
        permissions: PermissionStatus,
        services: ServiceStatus
    ): SystemState {
        return when {
            // Check permissions first - this is most critical
            !permissions.allGranted -> SystemState.NEEDS_PERMISSIONS
            // Then check services
            !services.allEnabled -> SystemState.NEEDS_SERVICES
            // All good
            else -> SystemState.READY
        }
    }

    private fun checkSystemErrors(
        permissions: PermissionStatus,
        services: ServiceStatus
    ): List<SystemError> {
        val errors = mutableListOf<SystemError>()

        if (!permissions.allGranted) {
            errors.add(SystemError(
                type = ErrorType.PERMISSION_ERROR,
                message = "Missing permissions: ${permissions.criticalMissing.joinToString()}",
                isRecoverable = true,
                suggestedAction = "Grant required permissions in app settings"
            ))
        }

        if (!services.allEnabled) {
            errors.add(SystemError(
                type = ErrorType.SERVICE_ERROR,
                message = "Disabled services: ${services.disabledServices.joinToString()}",
                isRecoverable = true,
                suggestedAction = "Enable required services in device settings"
            ))
        }

        return errors
    }

    // Permission checking methods
    private fun checkBluetoothPermissions(): PermissionState {
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            listOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE
            )
        } else {
            listOf(
                Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN
            )
        }

        return if (permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }) {
            PermissionState.GRANTED
        } else {
            PermissionState.DENIED
        }
    }

    private fun checkWiFiDirectPermissions(): PermissionState {
        val permissions = buildList {
            add(Manifest.permission.ACCESS_WIFI_STATE)
            add(Manifest.permission.CHANGE_WIFI_STATE)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                add(Manifest.permission.NEARBY_WIFI_DEVICES)
            }
        }

        return if (permissions.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }) {
            PermissionState.GRANTED
        } else {
            PermissionState.DENIED
        }
    }

    private fun checkLocationPermissions(): PermissionState {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            PermissionState.GRANTED // Not needed on Android 13+
        } else {
            if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
                PermissionState.GRANTED
            } else {
                PermissionState.DENIED
            }
        }
    }

    // Service checking methods
    private fun checkBluetoothService(): ServiceState {
        return try {
            val adapter = BluetoothAdapter.getDefaultAdapter()
            if (adapter?.isEnabled == true) ServiceState.ENABLED else ServiceState.DISABLED
        } catch (e: Exception) {
            ServiceState.UNAVAILABLE
        }
    }

    private fun checkWiFiService(): ServiceState {
        return try {
            val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            if (wifiManager.isWifiEnabled) ServiceState.ENABLED else ServiceState.DISABLED
        } catch (e: Exception) {
            ServiceState.UNAVAILABLE
        }
    }

    private fun checkLocationService(): ServiceState {
        // Location service is system-level, assume enabled if permissions are granted
        return if (checkLocationPermissions() == PermissionState.GRANTED) {
            ServiceState.ENABLED
        } else {
            ServiceState.DISABLED
        }
    }

    private fun checkIfDeviceIsVisible(): Boolean {
        // Check if we're currently advertising on any transport
        return false // Placeholder
    }

    private fun getDeviceName(): String {
        return try {
            android.provider.Settings.Global.getString(
                context.contentResolver,
                android.provider.Settings.Global.DEVICE_NAME
            ) ?: "HayatKurtar Device"
        } catch (e: Exception) {
            "HayatKurtar Device"
        }
    }
}
