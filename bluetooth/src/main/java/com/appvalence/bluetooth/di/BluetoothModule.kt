package com.appvalence.bluetooth.di

import android.content.Context
import com.appvalence.bluetooth.api.BleAdvertiser
import com.appvalence.bluetooth.api.BluetoothController
import com.appvalence.bluetooth.api.DistanceEstimator
import com.appvalence.bluetooth.api.HighPerformanceScanner
import com.appvalence.bluetooth.impl.AndroidBleAdvertiser
import com.appvalence.bluetooth.impl.AndroidBluetoothController
import com.appvalence.bluetooth.impl.AndroidHighPerformanceScanner
import com.appvalence.bluetooth.impl.RssiDistanceEstimator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BluetoothModule {

    @Provides
    @Singleton
    fun provideBluetoothController(@ApplicationContext context: Context): BluetoothController =
        AndroidBluetoothController(context)

    @Provides
    @Singleton
    fun provideHighPerformanceScanner(@ApplicationContext context: Context): HighPerformanceScanner =
        AndroidHighPerformanceScanner(context)

    @Provides
    @Singleton
    fun provideDistanceEstimator(): DistanceEstimator = RssiDistanceEstimator()

    @Provides
    @Singleton
    fun provideBleAdvertiser(@ApplicationContext context: Context): BleAdvertiser = AndroidBleAdvertiser(context)
}


