package com.appvalence.hayatkurtar.domain.permissions

import kotlinx.coroutines.flow.Flow

/**
 * Domain interface for permission management following Clean Architecture principles.
 * This interface abstracts permission handling from Android-specific implementations.
 * 
 * Based on research from:
 * - Android Architecture Guidelines
 * - Clean Architecture principles by Robert C. Martin
 * - Modern Android permission best practices with Activity Result API
 */
interface PermissionManager {
    
    /**
     * Get current state of all required permissions
     */
    fun getPermissionState(): Flow<PermissionState>
    
    /**
     * Request a single permission
     * @param permission The permission to request
     * @return Result of the permission request
     */
    suspend fun requestPermission(permission: MeshPermission): PermissionResult
    
    /**
     * Request multiple permissions
     * @param permissions The permissions to request
     * @return Result of the permission request
     */
    suspend fun requestPermissions(permissions: Set<MeshPermission>): PermissionResult
    
    /**
     * Check if a specific permission is granted
     * @param permission The permission to check
     * @return true if granted, false otherwise
     */
    fun isPermissionGranted(permission: MeshPermission): Boolean
    
    /**
     * Check if all mesh networking permissions are granted
     * @return true if all required permissions are granted
     */
    fun areAllPermissionsGranted(): Boolean
    
    /**
     * Get list of missing permissions
     * @return Set of missing permissions
     */
    fun getMissingPermissions(): Set<MeshPermission>
    
    /**
     * Check if permission is permanently denied (user selected "Don't ask again")
     * @param permission The permission to check
     * @return true if permanently denied
     */
    fun isPermissionPermanentlyDenied(permission: MeshPermission): Boolean
    
    /**
     * Open app settings for manual permission management
     */
    suspend fun openAppSettings(): Boolean
}