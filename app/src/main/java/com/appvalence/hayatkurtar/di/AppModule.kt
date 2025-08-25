package com.appvalence.hayatkurtar.di

import android.content.Context
import androidx.room.Room
import com.appvalence.hayatkurtar.data.crypto.CryptoService
import com.appvalence.hayatkurtar.data.local.AppDatabase
import com.appvalence.hayatkurtar.data.local.MessageDao
import com.appvalence.hayatkurtar.data.local.CalibrationStore
import com.appvalence.hayatkurtar.data.local.UserPrefsStore
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
    fun provideCalibrationStore(@ApplicationContext context: Context): CalibrationStore = CalibrationStore(context)

    @Provides
    @Singleton
    fun provideUserPrefsStore(@ApplicationContext context: Context): UserPrefsStore = UserPrefsStore(context)

    @Provides
    @Singleton
    fun provideCrypto(@ApplicationContext context: Context): CryptoService = CryptoService(context)

    // Note: ChatRepository and Bluetooth dependencies removed - using new mesh networking system
}


