package com.appvalence.bluetooth.api;

import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6@\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u0004\u0018\u00010\u0005H\u00a6@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fH&J\b\u0010\u000e\u001a\u00020\u0003H&J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\fH&J\u0016\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\rH\u00a6@\u00a2\u0006\u0002\u0010\u0012J\u0014\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\fH\u00a6@\u00a2\u0006\u0002\u0010\tJ\u000e\u0010\u0015\u001a\u00020\bH\u00a6@\u00a2\u0006\u0002\u0010\t\u00a8\u0006\u0016"}, d2 = {"Lcom/appvalence/bluetooth/api/BluetoothController;", "", "connect", "", "address", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "disconnect", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findFirstAvailable", "incoming", "Lkotlinx/coroutines/flow/Flow;", "", "isEnabled", "observeConnectionState", "send", "bytes", "([BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "startScan", "Lcom/appvalence/bluetooth/api/DiscoveredDevice;", "stopScan", "bluetooth_debug"})
public abstract interface BluetoothController {
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object startScan(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<com.appvalence.bluetooth.api.DiscoveredDevice>> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object stopScan(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object connect(@org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object disconnect(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<byte[]> incoming();
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object send(@org.jetbrains.annotations.NotNull()
    byte[] bytes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @org.jetbrains.annotations.Nullable()
    public abstract java.lang.Object findFirstAvailable(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion);
    
    public abstract boolean isEnabled();
    
    @org.jetbrains.annotations.NotNull()
    public abstract kotlinx.coroutines.flow.Flow<java.lang.Boolean> observeConnectionState();
}