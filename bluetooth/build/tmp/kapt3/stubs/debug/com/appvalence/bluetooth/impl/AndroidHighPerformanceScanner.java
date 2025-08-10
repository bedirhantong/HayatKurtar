package com.appvalence.bluetooth.impl;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import com.appvalence.bluetooth.api.DiscoveredDevice;
import com.appvalence.bluetooth.api.HighPerformanceScanner;
import kotlinx.coroutines.flow.Flow;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00100\u000fH\u0097@\u00a2\u0006\u0002\u0010\u0011J\u000e\u0010\u0012\u001a\u00020\u0013H\u0097@\u00a2\u0006\u0002\u0010\u0011R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\f\u001a\u0004\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lcom/appvalence/bluetooth/impl/AndroidHighPerformanceScanner;", "Lcom/appvalence/bluetooth/api/HighPerformanceScanner;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "adapter", "Landroid/bluetooth/BluetoothAdapter;", "appContext", "classicReceiver", "Landroid/content/BroadcastReceiver;", "leCallback", "Landroid/bluetooth/le/ScanCallback;", "leScanner", "Landroid/bluetooth/le/BluetoothLeScanner;", "startScan", "Lkotlinx/coroutines/flow/Flow;", "Lcom/appvalence/bluetooth/api/DiscoveredDevice;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "stopScan", "", "bluetooth_debug"})
public final class AndroidHighPerformanceScanner implements com.appvalence.bluetooth.api.HighPerformanceScanner {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.Nullable()
    private final android.bluetooth.BluetoothAdapter adapter = null;
    @org.jetbrains.annotations.Nullable()
    private android.content.BroadcastReceiver classicReceiver;
    @org.jetbrains.annotations.Nullable()
    private android.bluetooth.le.BluetoothLeScanner leScanner;
    @org.jetbrains.annotations.Nullable()
    private android.bluetooth.le.ScanCallback leCallback;
    
    public AndroidHighPerformanceScanner(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object startScan(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<com.appvalence.bluetooth.api.DiscoveredDevice>> $completion) {
        return null;
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    @org.jetbrains.annotations.Nullable()
    public java.lang.Object stopScan(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
}