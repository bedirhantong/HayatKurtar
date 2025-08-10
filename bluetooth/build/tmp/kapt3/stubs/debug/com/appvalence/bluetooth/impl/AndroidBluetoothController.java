package com.appvalence.bluetooth.impl;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.appvalence.bluetooth.api.BluetoothController;
import com.appvalence.bluetooth.api.DiscoveredDevice;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0016\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001e\u001a\u00020\fH\u0096@\u00a2\u0006\u0002\u0010\u001fJ\u000e\u0010 \u001a\u00020!H\u0096@\u00a2\u0006\u0002\u0010\"J\u0010\u0010#\u001a\u0004\u0018\u00010\fH\u0096@\u00a2\u0006\u0002\u0010\"J\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00130%H\u0016J\b\u0010&\u001a\u00020\nH\u0016J\u000e\u0010\'\u001a\b\u0012\u0004\u0012\u00020\n0%H\u0016J\u0016\u0010(\u001a\u00020!2\u0006\u0010)\u001a\u00020\u0013H\u0096@\u00a2\u0006\u0002\u0010*J \u0010+\u001a\u00020!2\u0006\u0010,\u001a\u00020\u000e2\u0006\u0010-\u001a\u00020\u00102\u0006\u0010\u001e\u001a\u00020\fH\u0002J\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020/0%H\u0096@\u00a2\u0006\u0002\u0010\"J\b\u00100\u001a\u00020!H\u0002J\u000e\u00101\u001a\u00020!H\u0096@\u00a2\u0006\u0002\u0010\"R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lcom/appvalence/bluetooth/impl/AndroidBluetoothController;", "Lcom/appvalence/bluetooth/api/BluetoothController;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "adapter", "Landroid/bluetooth/BluetoothAdapter;", "appContext", "connectionState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "", "currentDeviceAddress", "", "currentIn", "Ljava/io/InputStream;", "currentOut", "Ljava/io/OutputStream;", "incomingFlow", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "", "insecureServerJob", "Lkotlinx/coroutines/Job;", "ioScope", "Lkotlinx/coroutines/CoroutineScope;", "scanReceiver", "Landroid/content/BroadcastReceiver;", "serverJob", "sppUuid", "Ljava/util/UUID;", "connect", "address", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "disconnect", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "findFirstAvailable", "incoming", "Lkotlinx/coroutines/flow/Flow;", "isEnabled", "observeConnectionState", "send", "bytes", "([BLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setActiveSocket", "in", "out", "startScan", "Lcom/appvalence/bluetooth/api/DiscoveredDevice;", "startServerIfPossible", "stopScan", "bluetooth_debug"})
public final class AndroidBluetoothController implements com.appvalence.bluetooth.api.BluetoothController {
    @org.jetbrains.annotations.Nullable()
    private final android.bluetooth.BluetoothAdapter adapter = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<byte[]> incomingFlow = null;
    @org.jetbrains.annotations.Nullable()
    private android.content.BroadcastReceiver scanReceiver;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.CoroutineScope ioScope = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.UUID sppUuid = null;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job serverJob;
    @org.jetbrains.annotations.Nullable()
    private kotlinx.coroutines.Job insecureServerJob;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private volatile java.io.InputStream currentIn;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private volatile java.io.OutputStream currentOut;
    @kotlin.jvm.Volatile()
    @org.jetbrains.annotations.Nullable()
    private volatile java.lang.String currentDeviceAddress;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.Boolean> connectionState = null;
    
    public AndroidBluetoothController(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object startScan(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<com.appvalence.bluetooth.api.DiscoveredDevice>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object stopScan(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object connect(@org.jetbrains.annotations.NotNull()
    java.lang.String address, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object disconnect(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<byte[]> incoming() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object send(@org.jetbrains.annotations.NotNull()
    byte[] bytes, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object findFirstAvailable(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @java.lang.Override()
    public boolean isEnabled() {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> observeConnectionState() {
        return null;
    }
    
    private final void startServerIfPossible() {
    }
    
    private final void setActiveSocket(java.io.InputStream in, java.io.OutputStream out, java.lang.String address) {
    }
}