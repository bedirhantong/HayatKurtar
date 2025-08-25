package com.appvalence.hayatkurtar.domain.system;

import kotlinx.coroutines.flow.Flow;

/**
 * Central system status management following SOLID principles
 * Single Responsibility: Manages all system-level status information
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u000e\u0010\u0002\u001a\u00020\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H&J\u0014\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0006H&J\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0006H&J\u000e\u0010\r\u001a\u00020\u000eH\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u000f\u001a\u00020\u0010H\u00a6@\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0011\u001a\u00020\u0012H\u00a6@\u00a2\u0006\u0002\u0010\u0004\u00a8\u0006\u0013"}, d2 = {"Lcom/appvalence/hayatkurtar/domain/system/SystemStatusManager;", "", "enableRequiredServices", "Lcom/appvalence/hayatkurtar/domain/system/ServiceEnableResult;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getDeviceVisibility", "Lkotlinx/coroutines/flow/Flow;", "Lcom/appvalence/hayatkurtar/domain/system/DeviceVisibilityStatus;", "getDiscoveredPeers", "", "Lcom/appvalence/hayatkurtar/domain/system/DiscoveredPeer;", "getSystemStatus", "Lcom/appvalence/hayatkurtar/domain/system/SystemStatus;", "requestPermissions", "Lcom/appvalence/hayatkurtar/domain/system/PermissionResult;", "startDiscoveryAndAdvertising", "Lcom/appvalence/hayatkurtar/domain/system/DiscoveryResult;", "stopDiscoveryAndAdvertising", "", "domain_debug"})
public abstract interface SystemStatusManager {
    
    /**
     * Get current comprehensive system status
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.system.SystemStatus> getSystemStatus();
    
    /**
     * Request necessary permissions from user
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object requestPermissions(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.domain.system.PermissionResult> $completion);
    
    /**
     * Enable required services (Bluetooth, WiFi)
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object enableRequiredServices(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.domain.system.ServiceEnableResult> $completion);
    
    /**
     * Start device discovery and advertising
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startDiscoveryAndAdvertising(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.appvalence.hayatkurtar.domain.system.DiscoveryResult> $completion);
    
    /**
     * Stop discovery and advertising
     */
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stopDiscoveryAndAdvertising(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    /**
     * Get discovered peers
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.util.List<com.appvalence.hayatkurtar.domain.system.DiscoveredPeer>> getDiscoveredPeers();
    
    /**
     * Get device visibility status (are we discoverable?)
     */
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<com.appvalence.hayatkurtar.domain.system.DeviceVisibilityStatus> getDeviceVisibility();
}