package com.appvalence.bluetooth.impl;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import com.appvalence.bluetooth.api.BleAdvertiser;
import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u000f\u001a\u00020\nH\u0016J\u0012\u0010\u0010\u001a\u00020\n2\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0017J\b\u0010\u0013\u001a\u00020\u0014H\u0017R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u000b\u001a\n \f*\u0004\u0018\u00010\u00030\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u0004\u0018\u00010\u000eX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0015"}, d2 = {"Lcom/appvalence/bluetooth/impl/AndroidBleAdvertiser;", "Lcom/appvalence/bluetooth/api/BleAdvertiser;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "adapter", "Landroid/bluetooth/BluetoothAdapter;", "advertiser", "Landroid/bluetooth/le/BluetoothLeAdvertiser;", "advertising", "", "appContext", "kotlin.jvm.PlatformType", "callback", "Landroid/bluetooth/le/AdvertiseCallback;", "isAdvertising", "start", "serviceUuid", "Ljava/util/UUID;", "stop", "", "bluetooth_debug"})
public final class AndroidBleAdvertiser implements com.appvalence.bluetooth.api.BleAdvertiser {
    private final android.content.Context appContext = null;
    @org.jetbrains.annotations.Nullable()
    private final android.bluetooth.BluetoothAdapter adapter = null;
    @org.jetbrains.annotations.Nullable()
    private android.bluetooth.le.BluetoothLeAdvertiser advertiser;
    @org.jetbrains.annotations.Nullable()
    private android.bluetooth.le.AdvertiseCallback callback;
    @kotlin.jvm.Volatile()
    private volatile boolean advertising = false;
    
    public AndroidBleAdvertiser(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    public boolean start(@org.jetbrains.annotations.Nullable()
    java.util.UUID serviceUuid) {
        return false;
    }
    
    @java.lang.Override()
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    public void stop() {
    }
    
    @java.lang.Override()
    public boolean isAdvertising() {
        return false;
    }
}