package com.appvalence.hayatkurtar.di

import android.content.Context
import androidx.room.Room
import com.appvalence.hayatkurtar.data.bluetooth.AndroidBluetoothController
import com.appvalence.hayatkurtar.data.bluetooth.BluetoothController
import com.appvalence.hayatkurtar.data.crypto.CryptoService
import com.appvalence.hayatkurtar.data.bluetooth.HighPerformanceScanner
import com.appvalence.hayatkurtar.data.bluetooth.AndroidHighPerformanceScanner
import com.appvalence.hayatkurtar.data.bluetooth.DistanceEstimator
import com.appvalence.hayatkurtar.data.bluetooth.RssiDistanceEstimator
import com.appvalence.hayatkurtar.data.bluetooth.BleAdvertiser
import com.appvalence.hayatkurtar.data.bluetooth.AndroidBleAdvertiser
import com.appvalence.hayatkurtar.data.local.AppDatabase
import com.appvalence.hayatkurtar.data.local.MessageDao
import com.appvalence.hayatkurtar.data.local.CalibrationStore
import com.appvalence.hayatkurtar.data.repository.ChatRepositoryImpl
import com.appvalence.hayatkurtar.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "hk.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMessageDao(db: AppDatabase): MessageDao = db.messageDao()

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
    fun provideCalibrationStore(@ApplicationContext context: Context): CalibrationStore = CalibrationStore(context)

    @Provides
    @Singleton
    fun provideBleAdvertiser(@ApplicationContext context: Context): BleAdvertiser = AndroidBleAdvertiser(context)

    @Provides
    @Singleton
    fun provideCrypto(@ApplicationContext context: Context): CryptoService = CryptoService(context)

    @Provides
    @Singleton
    fun provideRepository(
        bluetoothController: BluetoothController,
        highPerformanceScanner: HighPerformanceScanner,
        distanceEstimator: DistanceEstimator,
        dao: MessageDao,
        crypto: CryptoService,
    ): ChatRepository = ChatRepositoryImpl(
        bluetoothController,
        highPerformanceScanner,
        dao,
        crypto,
        distanceEstimator,
    )
}


