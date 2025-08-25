package com.appvalence.hayatkurtar.data.permissions.di;

import com.appvalence.hayatkurtar.data.permissions.AndroidPermissionManager;
import com.appvalence.hayatkurtar.domain.permissions.PermissionManager;
import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

/**
 * Dependency injection module for permission management.
 *
 * This module binds the Android-specific implementation to the domain interface,
 * following Clean Architecture principles where the domain layer defines the contract
 * and the data layer provides the implementation.
 */
@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H'\u00a8\u0006\u0007"}, d2 = {"Lcom/appvalence/hayatkurtar/data/permissions/di/PermissionModule;", "", "()V", "bindPermissionManager", "Lcom/appvalence/hayatkurtar/domain/permissions/PermissionManager;", "androidPermissionManager", "Lcom/appvalence/hayatkurtar/data/permissions/AndroidPermissionManager;", "mesh_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class PermissionModule {
    
    public PermissionModule() {
        super();
    }
    
    @dagger.Binds()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public abstract com.appvalence.hayatkurtar.domain.permissions.PermissionManager bindPermissionManager(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.data.permissions.AndroidPermissionManager androidPermissionManager);
}