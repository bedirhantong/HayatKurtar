package com.appvalence.hayatkurtar.data.permissions.di

import com.appvalence.hayatkurtar.data.permissions.AndroidPermissionManager
import com.appvalence.hayatkurtar.domain.permissions.PermissionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency injection module for permission management.
 * 
 * This module binds the Android-specific implementation to the domain interface,
 * following Clean Architecture principles where the domain layer defines the contract
 * and the data layer provides the implementation.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class PermissionModule {
    
    @Binds
    @Singleton
    abstract fun bindPermissionManager(
        androidPermissionManager: AndroidPermissionManager
    ): PermissionManager
}