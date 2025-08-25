package com.appvalence.hayatkurtar.domain.system

import kotlinx.coroutines.flow.Flow

/**
 * Central system status management following SOLID principles
 * Single Responsibility: Manages all system-level status information
 */
interface SystemStatusManager {
    
    /**
     * Get current comprehensive system status
     */
    fun getSystemStatus(): Flow<SystemStatus>
    
    /**
     * Request necessary permissions from user
     */
    suspend fun requestPermissions(): PermissionResult
    
    /**
     * Enable required services (Bluetooth, WiFi)
     */
    suspend fun enableRequiredServices(): ServiceEnableResult
    
    /**
     * Start device discovery and advertising
     */
    suspend fun startDiscoveryAndAdvertising(): DiscoveryResult
    
    /**
     * Stop discovery and advertising
     */
    suspend fun stopDiscoveryAndAdvertising()
    
    /**
     * Get discovered peers
     */
    fun getDiscoveredPeers(): Flow<List<DiscoveredPeer>>
    
    /**
     * Get device visibility status (are we discoverable?)
     */
    fun getDeviceVisibility(): Flow<DeviceVisibilityStatus>
}

/**
 * Comprehensive system status
 */
data class SystemStatus(
    val overallState: SystemState,
    val permissions: PermissionStatus,
    val services: ServiceStatus,
    val connectivity: ConnectivityStatus,
    val discovery: DiscoveryStatus,
    val errors: List<SystemError> = emptyList()
)

/**
 * Overall system state enum
 */
enum class SystemState {
    READY,              // Everything is ready for mesh networking
    NEEDS_PERMISSIONS,  // Requires permission grants
    NEEDS_SERVICES,     // Requires service activation
    DISCOVERING,        // Currently discovering/advertising
    ERROR               // System error state
}

/**
 * Permission-related status
 */
data class PermissionStatus(
    val bluetooth: PermissionState,
    val wifiDirect: PermissionState,
    val location: PermissionState,
    val allGranted: Boolean
) {
    val criticalMissing: List<String> get() = buildList {
        if (bluetooth == PermissionState.DENIED) add("Bluetooth")
        if (wifiDirect == PermissionState.DENIED) add("Wi-Fi Direct")
        if (location == PermissionState.DENIED) add("Location")
    }
}

enum class PermissionState {
    GRANTED,
    DENIED,
    NOT_DETERMINED,
    PERMANENTLY_DENIED
}

/**
 * Service status (Bluetooth, WiFi)
 */
data class ServiceStatus(
    val bluetooth: ServiceState,
    val wifi: ServiceState,
    val location: ServiceState,
    val allEnabled: Boolean
) {
    val disabledServices: List<String> get() = buildList {
        if (bluetooth == ServiceState.DISABLED) add("Bluetooth")
        if (wifi == ServiceState.DISABLED) add("Wi-Fi")
        if (location == ServiceState.DISABLED) add("Location")
    }
}

enum class ServiceState {
    ENABLED,
    DISABLED,
    UNAVAILABLE,
    ENABLING,
    ERROR
}

/**
 * Connectivity and mesh status
 */
data class ConnectivityStatus(
    val connectedPeers: Int,
    val connectionQuality: ConnectionQuality,
    val meshNetworkSize: Int,
    val isConnectedToMesh: Boolean
)

enum class ConnectionQuality {
    EXCELLENT,
    GOOD,
    FAIR,
    POOR,
    NONE
}

/**
 * Discovery status
 */
data class DiscoveryStatus(
    val isDiscovering: Boolean,
    val isAdvertising: Boolean,
    val discoveredDeviceCount: Int,
    val lastDiscoveryTime: Long?
)

/**
 * Device visibility status
 */
data class DeviceVisibilityStatus(
    val isVisible: Boolean,
    val deviceName: String,
    val advertisementStrength: Int, // 0-100
    val visibilityRange: VisibilityRange
)

enum class VisibilityRange {
    SHORT,   // ~10m (Bluetooth LE)
    MEDIUM,  // ~30m (Bluetooth Classic)
    LONG     // ~100m (Wi-Fi Direct)
}

/**
 * Discovered peer information
 */
data class DiscoveredPeer(
    val id: String,
    val name: String,
    val transportType: String,
    val signalStrength: Int, // 0-100
    val distance: String,    // "~10m", "nearby", etc.
    val isConnectable: Boolean,
    val lastSeen: Long,
    val capabilities: PeerCapabilities
)

data class PeerCapabilities(
    val supportsEncryption: Boolean,
    val supportsMeshRelay: Boolean,
    val maxHopCount: Int,
    val preferredTransports: List<String>
)

/**
 * System error information
 */
data class SystemError(
    val type: ErrorType,
    val message: String,
    val isRecoverable: Boolean,
    val suggestedAction: String?
)

enum class ErrorType {
    PERMISSION_ERROR,
    SERVICE_ERROR,
    HARDWARE_ERROR,
    NETWORK_ERROR,
    UNKNOWN_ERROR
}

/**
 * Result types for operations
 */
sealed class PermissionResult {
    object Success : PermissionResult()
    data class Partial(val granted: List<String>, val denied: List<String>) : PermissionResult()
    data class Failed(val error: String) : PermissionResult()
}

sealed class ServiceEnableResult {
    object Success : ServiceEnableResult()
    data class Partial(val enabled: List<String>, val failed: List<String>) : ServiceEnableResult()
    data class Failed(val error: String) : ServiceEnableResult()
}

sealed class DiscoveryResult {
    object Success : DiscoveryResult()
    data class Failed(val error: String) : DiscoveryResult()
}