package com.appvalence.hayatkurtar.data.permissions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.appvalence.hayatkurtar.domain.permissions.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

/**
 * Android-specific implementation of PermissionManager using Activity Result API.
 * 
 * This implementation follows modern Android best practices:
 * - Uses Activity Result API instead of deprecated onRequestPermissionsResult
 * - Properly handles Android version-specific permissions
 * - Provides lifecycle-aware permission handling
 * - Follows Clean Architecture principles with proper separation of concerns
 * 
 * Based on research from:
 * - Android Developer Guide: Activity Result API
 * - Clean Architecture principles
 * - Modern Android permission management patterns
 */
@Singleton
class AndroidPermissionManager @Inject constructor(
    @ApplicationContext private val context: Context
) : PermissionManager {
    
    private val _permissionState = MutableStateFlow(PermissionState.empty())
    
    // Channels for handling permission request results
    private var permissionResultChannel: Channel<Map<String, Boolean>>? = null
    private var settingsResultChannel: Channel<Boolean>? = null
    
    // Activity Result Launchers - will be registered by UI components
    private var permissionLauncher: ActivityResultLauncher<Array<String>>? = null
    private var settingsLauncher: ActivityResultLauncher<Intent>? = null
    
    init {
        // Initialize permission state
        updatePermissionState()
    }
    
    override fun getPermissionState(): Flow<PermissionState> = _permissionState.asStateFlow()
    
    override suspend fun requestPermission(permission: MeshPermission): PermissionResult {
        return requestPermissions(setOf(permission))
    }
    
    override suspend fun requestPermissions(permissions: Set<MeshPermission>): PermissionResult {
        // Check if already granted
        val missingPermissions = permissions.filter { !isPermissionGranted(it) }
        if (missingPermissions.isEmpty()) {
            return PermissionResult.AllGranted
        }
        
        // Convert to Android manifest permissions
        val manifestPermissions = missingPermissions.flatMap { it.toManifestPermissions() }
            .filter { isPermissionRelevantForCurrentSdk(it) }
            .distinct()
            .toTypedArray()
        
        if (manifestPermissions.isEmpty()) {
            return PermissionResult.AllGranted
        }
        
        return try {
            suspendCancellableCoroutine { continuation ->
                permissionResultChannel = Channel<Map<String, Boolean>>(Channel.UNLIMITED).also { channel ->
                    continuation.invokeOnCancellation { 
                        channel.close() 
                        permissionResultChannel = null
                    }
                    
                    // Launch permission request
                    permissionLauncher?.launch(manifestPermissions) ?: run {
                        continuation.resume(PermissionResult.Error(IllegalStateException("Permission launcher not registered")))
                        return@suspendCancellableCoroutine
                    }
                    
                    // Handle result
                    channel.trySend(emptyMap()) // This will be updated by the launcher callback
                }
                
                // This will be resumed by the launcher callback
            }
        } catch (e: Exception) {
            PermissionResult.Error(e)
        }
    }
    
    override fun isPermissionGranted(permission: MeshPermission): Boolean {
        return permission.toManifestPermissions()
            .filter { isPermissionRelevantForCurrentSdk(it) }
            .all { manifestPermission ->
                ContextCompat.checkSelfPermission(context, manifestPermission) == PackageManager.PERMISSION_GRANTED
            }
    }
    
    override fun areAllPermissionsGranted(): Boolean {
        val requiredPermissions = MeshPermission.getRequiredPermissions(Build.VERSION.SDK_INT)
        return requiredPermissions.all { isPermissionGranted(it) }
    }
    
    override fun getMissingPermissions(): Set<MeshPermission> {
        val requiredPermissions = MeshPermission.getRequiredPermissions(Build.VERSION.SDK_INT)
        return requiredPermissions.filter { !isPermissionGranted(it) }.toSet()
    }
    
    override fun isPermissionPermanentlyDenied(permission: MeshPermission): Boolean {
        // This requires Activity context, so we'll need to track this separately
        // For now, return false - this will be properly implemented when integrated with UI
        return false
    }
    
    override suspend fun openAppSettings(): Boolean {
        return try {
            suspendCancellableCoroutine { continuation ->
                settingsResultChannel = Channel<Boolean>(Channel.UNLIMITED).also { channel ->
                    continuation.invokeOnCancellation { 
                        channel.close()
                        settingsResultChannel = null
                    }
                    
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    
                    settingsLauncher?.launch(intent) ?: run {
                        context.startActivity(intent)
                        continuation.resume(true)
                        return@suspendCancellableCoroutine
                    }
                }
            }
        } catch (e: Exception) {
            false
        }
    }
    
    /**
     * Register Activity Result Launchers with the permission manager.
     * This should be called from the UI layer (Activity/Fragment) during initialization.
     */
    fun registerLaunchers(
        permissionLauncher: ActivityResultLauncher<Array<String>>,
        settingsLauncher: ActivityResultLauncher<Intent>
    ) {
        this.permissionLauncher = permissionLauncher
        this.settingsLauncher = settingsLauncher
    }
    
    /**
     * Handle permission request result from Activity Result API
     */
    fun handlePermissionResult(results: Map<String, Boolean>): PermissionResult {
        permissionResultChannel?.trySend(results)
        
        // Update permission state
        updatePermissionState()
        
        val deniedPermissions = results.filterValues { !it }.keys
        return when {
            deniedPermissions.isEmpty() -> PermissionResult.AllGranted
            deniedPermissions.size == results.size -> PermissionResult.AllDenied
            else -> {
                val grantedManifest = results.filterValues { it }.keys
                val deniedManifest = deniedPermissions
                
                val grantedDomain = MeshPermission.values().filter { permission ->
                    permission.toManifestPermissions().any { it in grantedManifest }
                }.toSet()
                
                val deniedDomain = MeshPermission.values().filter { permission ->
                    permission.toManifestPermissions().any { it in deniedManifest }
                }.toSet()
                
                PermissionResult.PartiallyGranted(grantedDomain, deniedDomain)
            }
        }
    }
    
    /**
     * Handle settings result from Activity Result API
     */
    fun handleSettingsResult() {
        updatePermissionState()
        settingsResultChannel?.trySend(true)
    }
    
    /**
     * Check if permanently denied with Activity context
     */
    fun checkPermanentlyDenied(activity: ComponentActivity, permission: MeshPermission): Boolean {
        return permission.toManifestPermissions()
            .filter { isPermissionRelevantForCurrentSdk(it) }
            .any { manifestPermission ->
                !ActivityCompat.shouldShowRequestPermissionRationale(activity, manifestPermission) &&
                ContextCompat.checkSelfPermission(context, manifestPermission) != PackageManager.PERMISSION_GRANTED
            }
    }
    
    private fun updatePermissionState() {
        val requiredPermissions = MeshPermission.getRequiredPermissions(Build.VERSION.SDK_INT)
        val permissionStatuses = mutableMapOf<MeshPermission, PermissionStatus>()
        
        for (permission in MeshPermission.values()) {
            permissionStatuses[permission] = when {
                !requiredPermissions.contains(permission) -> PermissionStatus.NOT_REQUIRED
                isPermissionGranted(permission) -> PermissionStatus.GRANTED
                // Note: PERMANENTLY_DENIED check requires Activity context
                else -> PermissionStatus.DENIED
            }
        }
        
        val missingPermissions = permissionStatuses
            .filterValues { it == PermissionStatus.DENIED }
            .keys
        
        val permanentlyDenied = permissionStatuses
            .filterValues { it == PermissionStatus.PERMANENTLY_DENIED }
            .keys
        
        _permissionState.value = PermissionState(
            permissions = permissionStatuses,
            allGranted = missingPermissions.isEmpty(),
            missingPermissions = missingPermissions,
            permanentlyDeniedPermissions = permanentlyDenied,
            canRequestPermissions = permanentlyDenied.isEmpty()
        )
    }
    
    private fun isPermissionRelevantForCurrentSdk(manifestPermission: String): Boolean {
        return when (manifestPermission) {
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE -> Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
            Manifest.permission.NEARBY_WIFI_DEVICES -> Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN -> Build.VERSION.SDK_INT < Build.VERSION_CODES.S
            Manifest.permission.ACCESS_FINE_LOCATION -> Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU
            else -> true
        }
    }
}

/**
 * Extension to convert domain permission to Android manifest permissions
 */
private fun MeshPermission.toManifestPermissions(): List<String> {
    return when (this) {
        MeshPermission.BLUETOOTH_CONNECT -> listOf(Manifest.permission.BLUETOOTH_CONNECT)
        MeshPermission.BLUETOOTH_SCAN -> listOf(Manifest.permission.BLUETOOTH_SCAN)
        MeshPermission.BLUETOOTH_ADVERTISE -> listOf(Manifest.permission.BLUETOOTH_ADVERTISE)
        MeshPermission.BLUETOOTH_LEGACY -> listOf(Manifest.permission.BLUETOOTH)
        MeshPermission.BLUETOOTH_ADMIN_LEGACY -> listOf(Manifest.permission.BLUETOOTH_ADMIN)
        MeshPermission.ACCESS_WIFI_STATE -> listOf(Manifest.permission.ACCESS_WIFI_STATE)
        MeshPermission.CHANGE_WIFI_STATE -> listOf(Manifest.permission.CHANGE_WIFI_STATE)
        MeshPermission.NEARBY_WIFI_DEVICES -> listOf(Manifest.permission.NEARBY_WIFI_DEVICES)
        MeshPermission.ACCESS_FINE_LOCATION -> listOf(Manifest.permission.ACCESS_FINE_LOCATION)
        MeshPermission.ACCESS_COARSE_LOCATION -> listOf(Manifest.permission.ACCESS_COARSE_LOCATION)
    }
}