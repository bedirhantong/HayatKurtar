package com.appvalence.hayatkurtar.domain.permissions

/**
 * Domain models for permission management following Clean Architecture.
 * These models are platform-agnostic and contain only business logic.
 */

/**
 * Represents specific permissions required for mesh networking functionality
 */
enum class MeshPermission(
    val description: String,
    val isRequired: Boolean = true,
    val minSdkVersion: Int = 1
) {
    // Bluetooth permissions (SDK-specific)
    BLUETOOTH_CONNECT(
        description = "Bluetooth bağlantısı kurmak için gerekli",
        minSdkVersion = 31 // Android 12 (API 31)
    ),
    BLUETOOTH_SCAN(
        description = "Bluetooth cihazlarını taramak için gerekli", 
        minSdkVersion = 31 // Android 12 (API 31)
    ),
    BLUETOOTH_ADVERTISE(
        description = "Bluetooth ile keşfedilebilir olmak için gerekli",
        minSdkVersion = 31 // Android 12 (API 31)
    ),
    
    // Legacy Bluetooth permissions (SDK < 31)
    BLUETOOTH_LEGACY(
        description = "Bluetooth kullanımı için gerekli",
        minSdkVersion = 1
    ),
    BLUETOOTH_ADMIN_LEGACY(
        description = "Bluetooth yönetimi için gerekli",
        minSdkVersion = 1
    ),
    
    // Wi-Fi Direct permissions
    ACCESS_WIFI_STATE(
        description = "Wi-Fi durumu kontrolü için gerekli"
    ),
    CHANGE_WIFI_STATE(
        description = "Wi-Fi durumu değiştirmek için gerekli"
    ),
    NEARBY_WIFI_DEVICES(
        description = "Yakındaki Wi-Fi cihazlarını keşfetmek için gerekli",
        minSdkVersion = 33 // Android 13 (API 33)
    ),
    
    // Location permissions (required for older Android versions)
    ACCESS_FINE_LOCATION(
        description = "Konum erişimi (Wi-Fi Direct için gerekli)",
        isRequired = false // Conditional based on Android version
    ),
    ACCESS_COARSE_LOCATION(
        description = "Kaba konum erişimi",
        isRequired = false // Conditional based on Android version
    );
    
    companion object {
        /**
         * Get permissions required for current SDK version
         */
        fun getRequiredPermissions(sdkVersion: Int): Set<MeshPermission> {
            return values().filter { permission ->
                when {
                    // Android 13+ (API 33+)
                    sdkVersion >= 33 -> when (permission) {
                        BLUETOOTH_CONNECT, BLUETOOTH_SCAN, BLUETOOTH_ADVERTISE,
                        ACCESS_WIFI_STATE, CHANGE_WIFI_STATE, NEARBY_WIFI_DEVICES -> true
                        else -> false
                    }
                    // Android 12 (API 31-32)
                    sdkVersion >= 31 -> when (permission) {
                        BLUETOOTH_CONNECT, BLUETOOTH_SCAN, BLUETOOTH_ADVERTISE,
                        ACCESS_WIFI_STATE, CHANGE_WIFI_STATE, ACCESS_FINE_LOCATION -> true
                        else -> false
                    }
                    // Android 6-11 (API 23-30)
                    sdkVersion >= 23 -> when (permission) {
                        BLUETOOTH_LEGACY, BLUETOOTH_ADMIN_LEGACY,
                        ACCESS_WIFI_STATE, CHANGE_WIFI_STATE, ACCESS_FINE_LOCATION -> true
                        else -> false
                    }
                    // Below Android 6
                    else -> when (permission) {
                        BLUETOOTH_LEGACY, BLUETOOTH_ADMIN_LEGACY,
                        ACCESS_WIFI_STATE, CHANGE_WIFI_STATE -> true
                        else -> false
                    }
                }
            }.toSet()
        }
    }
}

/**
 * Current state of a specific permission
 */
enum class PermissionStatus {
    GRANTED,           // Permission is granted
    DENIED,            // Permission is denied, can be requested again
    PERMANENTLY_DENIED, // Permission is permanently denied (user selected "Don't ask again")
    NOT_REQUIRED       // Permission is not required for current Android version
}

/**
 * Overall state of all permissions
 */
data class PermissionState(
    val permissions: Map<MeshPermission, PermissionStatus>,
    val allGranted: Boolean,
    val missingPermissions: Set<MeshPermission>,
    val permanentlyDeniedPermissions: Set<MeshPermission>,
    val canRequestPermissions: Boolean
) {
    companion object {
        fun empty() = PermissionState(
            permissions = emptyMap(),
            allGranted = false,
            missingPermissions = emptySet(),
            permanentlyDeniedPermissions = emptySet(),
            canRequestPermissions = false
        )
    }
}

/**
 * Result of permission request operation
 */
sealed class PermissionResult {
    object AllGranted : PermissionResult()
    object AllDenied : PermissionResult()
    data class PartiallyGranted(
        val grantedPermissions: Set<MeshPermission>,
        val deniedPermissions: Set<MeshPermission>
    ) : PermissionResult()
    data class Error(val exception: Throwable) : PermissionResult()
}

/**
 * Events that can occur during permission management
 */
sealed class PermissionEvent {
    object AllPermissionsGranted : PermissionEvent()
    data class PermissionDenied(val permission: MeshPermission) : PermissionEvent()
    data class PermissionPermanentlyDenied(val permission: MeshPermission) : PermissionEvent()
    object ShouldShowRationale : PermissionEvent()
    object ShouldOpenSettings : PermissionEvent()
}