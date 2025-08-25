package com.appvalence.hayatkurtar.domain.permissions.usecases

import com.appvalence.hayatkurtar.domain.permissions.*
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use cases for permission management following Clean Architecture principles.
 * These use cases encapsulate business logic and are platform-independent.
 * 
 * Based on Domain-Driven Design and Clean Architecture principles.
 */

/**
 * Use case to get current permission state
 */
class GetPermissionStateUseCase @Inject constructor(
    private val permissionManager: PermissionManager
) {
    operator fun invoke(): Flow<PermissionState> {
        return permissionManager.getPermissionState()
    }
}

/**
 * Use case to request all required permissions for mesh networking
 */
class RequestMeshPermissionsUseCase @Inject constructor(
    private val permissionManager: PermissionManager
) {
    suspend operator fun invoke(sdkVersion: Int): PermissionResult {
        val requiredPermissions = MeshPermission.getRequiredPermissions(sdkVersion)
        return permissionManager.requestPermissions(requiredPermissions)
    }
}

/**
 * Use case to request a specific permission
 */
class RequestSinglePermissionUseCase @Inject constructor(
    private val permissionManager: PermissionManager
) {
    suspend operator fun invoke(permission: MeshPermission): PermissionResult {
        return permissionManager.requestPermission(permission)
    }
}

/**
 * Use case to check if all mesh networking permissions are granted
 */
class CheckAllPermissionsGrantedUseCase @Inject constructor(
    private val permissionManager: PermissionManager
) {
    operator fun invoke(): Boolean {
        return permissionManager.areAllPermissionsGranted()
    }
}

/**
 * Use case to get missing permissions
 */
class GetMissingPermissionsUseCase @Inject constructor(
    private val permissionManager: PermissionManager
) {
    operator fun invoke(): Set<MeshPermission> {
        return permissionManager.getMissingPermissions()
    }
}

/**
 * Use case to handle permanently denied permissions
 */
class HandlePermanentlyDeniedPermissionsUseCase @Inject constructor(
    private val permissionManager: PermissionManager
) {
    suspend operator fun invoke(): Boolean {
        return permissionManager.openAppSettings()
    }
}

/**
 * Use case to validate mesh networking readiness
 */
class ValidateMeshNetworkingReadinessUseCase @Inject constructor(
    private val permissionManager: PermissionManager
) {
    /**
     * Validates if the device is ready for mesh networking
     * @param sdkVersion Current Android SDK version
     * @return ValidationResult indicating readiness status
     */
    operator fun invoke(sdkVersion: Int): MeshNetworkingValidationResult {
        val requiredPermissions = MeshPermission.getRequiredPermissions(sdkVersion)
        val missingPermissions = permissionManager.getMissingPermissions()
        val permanentlyDeniedPermissions = requiredPermissions.filter { 
            permissionManager.isPermissionPermanentlyDenied(it) 
        }.toSet()
        
        return when {
            missingPermissions.isEmpty() -> MeshNetworkingValidationResult.Ready
            permanentlyDeniedPermissions.isNotEmpty() -> 
                MeshNetworkingValidationResult.PermanentlyBlocked(permanentlyDeniedPermissions)
            else -> MeshNetworkingValidationResult.MissingPermissions(missingPermissions)
        }
    }
}

/**
 * Result of mesh networking validation
 */
sealed class MeshNetworkingValidationResult {
    object Ready : MeshNetworkingValidationResult()
    data class MissingPermissions(val permissions: Set<MeshPermission>) : MeshNetworkingValidationResult()
    data class PermanentlyBlocked(val permissions: Set<MeshPermission>) : MeshNetworkingValidationResult()
}