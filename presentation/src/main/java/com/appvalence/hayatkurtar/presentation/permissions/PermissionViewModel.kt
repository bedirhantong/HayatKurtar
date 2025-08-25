package com.appvalence.hayatkurtar.presentation.permissions

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appvalence.hayatkurtar.data.permissions.AndroidPermissionManager
import com.appvalence.hayatkurtar.domain.permissions.*
import com.appvalence.hayatkurtar.domain.permissions.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for permission management following MVVM architecture principles.
 * 
 * This ViewModel:
 * - Follows MVVM pattern with clear separation of UI state and business logic
 * - Uses Clean Architecture use cases for business operations
 * - Provides reactive UI state updates via StateFlow
 * - Handles UI events through sealed classes
 * - Maintains lifecycle-aware state management
 * 
 * Based on Android Architecture Guidelines and MVVM best practices.
 */
@HiltViewModel
class PermissionViewModel @Inject constructor(
    private val getPermissionStateUseCase: GetPermissionStateUseCase,
    private val requestMeshPermissionsUseCase: RequestMeshPermissionsUseCase,
    private val requestSinglePermissionUseCase: RequestSinglePermissionUseCase,
    private val checkAllPermissionsGrantedUseCase: CheckAllPermissionsGrantedUseCase,
    private val getMissingPermissionsUseCase: GetMissingPermissionsUseCase,
    private val handlePermanentlyDeniedPermissionsUseCase: HandlePermanentlyDeniedPermissionsUseCase,
    private val validateMeshNetworkingReadinessUseCase: ValidateMeshNetworkingReadinessUseCase,
    private val permissionManager: PermissionManager
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<PermissionUiState>(PermissionUiState.Loading)
    val uiState: StateFlow<PermissionUiState> = _uiState.asStateFlow()
    
    private val _uiEvents = Channel<PermissionUiEvent>(Channel.UNLIMITED)
    val uiEvents: Flow<PermissionUiEvent> = _uiEvents.receiveAsFlow()
    
    private var currentActivity: ComponentActivity? = null
    
    /**
     * Access to AndroidPermissionManager for launcher registration
     */
    val androidPermissionManager: AndroidPermissionManager
        get() = permissionManager as AndroidPermissionManager
    
    init {
        observePermissionState()
    }
    
    /**
     * Register the current activity for permission checks
     */
    fun registerActivity(activity: ComponentActivity) {
        currentActivity = activity
        refreshPermissions()
    }
    
    /**
     * Request all required permissions for mesh networking
     */
    fun requestAllPermissions() {
        viewModelScope.launch {
            try {
                _uiState.value = PermissionUiState.Loading
                
                val result = requestMeshPermissionsUseCase(Build.VERSION.SDK_INT)
                processPermissionResult(result)
                
            } catch (e: Exception) {
                _uiState.value = PermissionUiState.Error(e.message ?: "Unknown error occurred")
                _uiEvents.trySend(PermissionUiEvent.ShowError(e.message ?: "Permission request failed"))
            }
        }
    }
    
    /**
     * Request a specific permission
     */
    fun requestPermission(permission: MeshPermission) {
        viewModelScope.launch {
            try {
                val result = requestSinglePermissionUseCase(permission)
                processPermissionResult(result)
                
            } catch (e: Exception) {
                _uiEvents.trySend(PermissionUiEvent.ShowError("Failed to request ${permission.description}"))
            }
        }
    }
    
    /**
     * Handle permanently denied permissions by opening settings
     */
    fun openAppSettings() {
        viewModelScope.launch {
            try {
                val success = handlePermanentlyDeniedPermissionsUseCase()
                if (success) {
                    _uiEvents.trySend(PermissionUiEvent.NavigatedToSettings)
                } else {
                    _uiEvents.trySend(PermissionUiEvent.ShowError("Could not open app settings"))
                }
            } catch (e: Exception) {
                _uiEvents.trySend(PermissionUiEvent.ShowError("Failed to open settings"))
            }
        }
    }
    
    /**
     * Skip permission request (if allowed by app logic)
     */
    fun skipPermissions() {
        _uiEvents.trySend(PermissionUiEvent.PermissionsSkipped)
    }
    
    /**
     * Refresh permission state
     */
    fun refreshPermissions() {
        viewModelScope.launch {
            updateUiState()
        }
    }
    
    /**
     * Handle permission result from Activity Result API
     */
    fun handlePermissionResult(results: Map<String, Boolean>) {
        val androidManager = permissionManager as AndroidPermissionManager
        val result = androidManager.handlePermissionResult(results)
        processPermissionResult(result)
    }
    
    /**
     * Handle settings result
     */
    fun handleSettingsResult() {
        val androidManager = permissionManager as AndroidPermissionManager
        androidManager.handleSettingsResult()
        refreshPermissions()
    }
    
    private fun observePermissionState() {
        viewModelScope.launch {
            getPermissionStateUseCase()
                .catch { e ->
                    _uiState.value = PermissionUiState.Error(e.message ?: "Failed to observe permissions")
                }
                .collect { permissionState ->
                    updateUiStateFromPermissionState(permissionState)
                }
        }
    }
    
    private suspend fun updateUiState() {
        try {
            val validation = validateMeshNetworkingReadinessUseCase(Build.VERSION.SDK_INT)
            val missingPermissions = getMissingPermissionsUseCase()
            
            when (validation) {
                is MeshNetworkingValidationResult.Ready -> {
                    _uiState.value = PermissionUiState.AllGranted
                    _uiEvents.trySend(PermissionUiEvent.AllPermissionsGranted)
                }
                is MeshNetworkingValidationResult.MissingPermissions -> {
                    val permissionDetails = createPermissionDetails(validation.permissions)
                    _uiState.value = PermissionUiState.NeedsPermissions(
                        missingPermissions = validation.permissions,
                        permissionDetails = permissionDetails
                    )
                }
                is MeshNetworkingValidationResult.PermanentlyBlocked -> {
                    _uiState.value = PermissionUiState.PermanentlyDenied(
                        permanentlyDeniedPermissions = validation.permissions
                    )
                }
            }
        } catch (e: Exception) {
            _uiState.value = PermissionUiState.Error(e.message ?: "Failed to validate permissions")
        }
    }
    
    private fun updateUiStateFromPermissionState(permissionState: PermissionState) {
        when {
            permissionState.allGranted -> {
                _uiState.value = PermissionUiState.AllGranted
            }
            permissionState.permanentlyDeniedPermissions.isNotEmpty() -> {
                _uiState.value = PermissionUiState.PermanentlyDenied(
                    permanentlyDeniedPermissions = permissionState.permanentlyDeniedPermissions
                )
            }
            permissionState.missingPermissions.isNotEmpty() -> {
                val permissionDetails = createPermissionDetails(permissionState.missingPermissions)
                _uiState.value = PermissionUiState.NeedsPermissions(
                    missingPermissions = permissionState.missingPermissions,
                    permissionDetails = permissionDetails
                )
            }
        }
    }
    
    private fun processPermissionResult(result: PermissionResult) {
        when (result) {
            is PermissionResult.AllGranted -> {
                _uiEvents.trySend(PermissionUiEvent.AllPermissionsGranted)
            }
            is PermissionResult.AllDenied -> {
                _uiEvents.trySend(PermissionUiEvent.AllPermissionsDenied)
            }
            is PermissionResult.PartiallyGranted -> {
                _uiEvents.trySend(PermissionUiEvent.SomePermissionsDenied(result.deniedPermissions))
            }
            is PermissionResult.Error -> {
                _uiEvents.trySend(PermissionUiEvent.ShowError(result.exception.message ?: "Permission error"))
            }
        }
        
        // Refresh UI state after handling result
        refreshPermissions()
    }
    
    private fun createPermissionDetails(permissions: Set<MeshPermission>): List<PermissionDetail> {
        return permissions.map { permission ->
            PermissionDetail(
                permission = permission,
                title = getPermissionTitle(permission),
                description = permission.description,
                isRequired = permission.isRequired,
                icon = getPermissionIcon(permission)
            )
        }
    }
    
    private fun getPermissionTitle(permission: MeshPermission): String {
        return when (permission) {
            MeshPermission.BLUETOOTH_CONNECT -> "Bluetooth Bağlantısı"
            MeshPermission.BLUETOOTH_SCAN -> "Bluetooth Tarama"
            MeshPermission.BLUETOOTH_ADVERTISE -> "Bluetooth Yayın"
            MeshPermission.BLUETOOTH_LEGACY -> "Bluetooth"
            MeshPermission.BLUETOOTH_ADMIN_LEGACY -> "Bluetooth Yönetimi"
            MeshPermission.ACCESS_WIFI_STATE -> "Wi-Fi Durumu"
            MeshPermission.CHANGE_WIFI_STATE -> "Wi-Fi Kontrolü"
            MeshPermission.NEARBY_WIFI_DEVICES -> "Yakındaki Wi-Fi Cihazları"
            MeshPermission.ACCESS_FINE_LOCATION -> "Hassas Konum"
            MeshPermission.ACCESS_COARSE_LOCATION -> "Genel Konum"
        }
    }
    
    private fun getPermissionIcon(permission: MeshPermission): String {
        return when (permission) {
            MeshPermission.BLUETOOTH_CONNECT,
            MeshPermission.BLUETOOTH_SCAN,
            MeshPermission.BLUETOOTH_ADVERTISE,
            MeshPermission.BLUETOOTH_LEGACY,
            MeshPermission.BLUETOOTH_ADMIN_LEGACY -> "bluetooth"
            MeshPermission.ACCESS_WIFI_STATE,
            MeshPermission.CHANGE_WIFI_STATE,
            MeshPermission.NEARBY_WIFI_DEVICES -> "wifi"
            MeshPermission.ACCESS_FINE_LOCATION,
            MeshPermission.ACCESS_COARSE_LOCATION -> "location"
        }
    }
    
    /**
     * Check if permission is permanently denied
     */
    fun isPermissionPermanentlyDenied(permission: MeshPermission): Boolean {
        return currentActivity?.let { activity ->
            val androidManager = permissionManager as AndroidPermissionManager
            androidManager.checkPermanentlyDenied(activity, permission)
        } ?: false
    }
}

/**
 * UI state for permission screen following MVVM pattern
 */
sealed class PermissionUiState {
    object Loading : PermissionUiState()
    object AllGranted : PermissionUiState()
    
    data class NeedsPermissions(
        val missingPermissions: Set<MeshPermission>,
        val permissionDetails: List<PermissionDetail>
    ) : PermissionUiState()
    
    data class PermanentlyDenied(
        val permanentlyDeniedPermissions: Set<MeshPermission>
    ) : PermissionUiState()
    
    data class Error(val message: String) : PermissionUiState()
}

/**
 * UI events for permission management
 */
sealed class PermissionUiEvent {
    object AllPermissionsGranted : PermissionUiEvent()
    object AllPermissionsDenied : PermissionUiEvent()
    object PermissionsSkipped : PermissionUiEvent()
    object NavigatedToSettings : PermissionUiEvent()
    data class SomePermissionsDenied(val deniedPermissions: Set<MeshPermission>) : PermissionUiEvent()
    data class ShowError(val message: String) : PermissionUiEvent()
    data class ShowSnackbar(val message: String) : PermissionUiEvent()
}

/**
 * Permission detail for UI display
 */
data class PermissionDetail(
    val permission: MeshPermission,
    val title: String,
    val description: String,
    val isRequired: Boolean,
    val icon: String
)