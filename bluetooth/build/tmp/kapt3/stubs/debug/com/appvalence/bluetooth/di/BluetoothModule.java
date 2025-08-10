package com.appvalence.bluetooth.di;

import android.content.Context;
import com.appvalence.bluetooth.api.BleAdvertiser;
import com.appvalence.bluetooth.api.BluetoothController;
import com.appvalence.bluetooth.api.DistanceEstimator;
import com.appvalence.bluetooth.api.HighPerformanceScanner;
import com.appvalence.bluetooth.impl.AndroidBleAdvertiser;
import com.appvalence.bluetooth.impl.AndroidBluetoothController;
import com.appvalence.bluetooth.impl.AndroidHighPerformanceScanner;
import com.appvalence.bluetooth.impl.RssiDistanceEstimator;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\u0012\u0010\u0007\u001a\u00020\b2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\t\u001a\u00020\nH\u0007J\u0012\u0010\u000b\u001a\u00020\f2\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\r"}, d2 = {"Lcom/appvalence/bluetooth/di/BluetoothModule;", "", "()V", "provideBleAdvertiser", "Lcom/appvalence/bluetooth/api/BleAdvertiser;", "context", "Landroid/content/Context;", "provideBluetoothController", "Lcom/appvalence/bluetooth/api/BluetoothController;", "provideDistanceEstimator", "Lcom/appvalence/bluetooth/api/DistanceEstimator;", "provideHighPerformanceScanner", "Lcom/appvalence/bluetooth/api/HighPerformanceScanner;", "bluetooth_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class BluetoothModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.bluetooth.di.BluetoothModule INSTANCE = null;
    
    private BluetoothModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.bluetooth.api.BluetoothController provideBluetoothController(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.bluetooth.api.HighPerformanceScanner provideHighPerformanceScanner(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.bluetooth.api.DistanceEstimator provideDistanceEstimator() {
        return null;
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.bluetooth.api.BleAdvertiser provideBleAdvertiser(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}