package com.appvalence.bluetooth.api;

import kotlinx.coroutines.flow.Flow;

/**
 * High-performance Bluetooth discovery that combines Classic discovery and BLE scanning.
 * Emits unique devices by MAC address with latest known name.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u00a6@\u00a2\u0006\u0002\u0010\u0005J\u000e\u0010\u0006\u001a\u00020\u0007H\u00a6@\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\b"}, d2 = {"Lcom/appvalence/bluetooth/api/HighPerformanceScanner;", "", "startScan", "Lkotlinx/coroutines/flow/Flow;", "Lcom/appvalence/bluetooth/api/DiscoveredDevice;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopScan", "", "bluetooth_debug"})
public abstract interface HighPerformanceScanner {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startScan(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<com.appvalence.bluetooth.api.DiscoveredDevice>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stopScan(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}