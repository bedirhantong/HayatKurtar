package com.appvalence.hayatkurtar.di

import android.content.Context
import androidx.room.Room
import com.appvalence.bluetooth.api.BluetoothController
import com.appvalence.bluetooth.api.HighPerformanceScanner
import com.appvalence.hayatkurtar.data.mesh.database.MeshDatabase
import com.appvalence.hayatkurtar.data.mesh.router.AndroidDeviceIdProvider
import com.appvalence.hayatkurtar.data.mesh.router.DefaultMeshRouter
import com.appvalence.hayatkurtar.data.mesh.router.DeviceIdProvider
import com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao
import com.appvalence.hayatkurtar.data.mesh.store.RoomMessageStore
import com.appvalence.hayatkurtar.data.mesh.transport.DefaultTransportMultiplexer
import com.appvalence.hayatkurtar.data.system.AndroidSystemStatusManager
import com.appvalence.hayatkurtar.data.transport.bluetooth.BluetoothClassicTransportStrategy
import com.appvalence.hayatkurtar.data.transport.wifidirect.WiFiDirectTransportStrategy
import com.appvalence.hayatkurtar.domain.mesh.MessageStore
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter
import com.appvalence.hayatkurtar.domain.system.SystemStatusManager
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer
import com.appvalence.hayatkurtar.domain.transport.TransportStrategy
import com.appvalence.hayatkurtar.domain.transport.TransportType
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import javax.inject.Singleton

/**
 * Hilt module for database dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMeshDatabase(@ApplicationContext context: Context): MeshDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            MeshDatabase::class.java,
            MeshDatabase.DATABASE_NAME
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideMessageStoreDao(database: MeshDatabase): MessageStoreDao {
        return database.messageStoreDao()
    }
}

/**
 * Hilt module for mesh networking core components
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MeshNetworkModule {

    @Binds
    @Singleton
    abstract fun bindMessageStore(
        roomMessageStore: RoomMessageStore
    ): MessageStore

    @Binds
    @Singleton
    abstract fun bindMeshRouter(
        defaultMeshRouter: DefaultMeshRouter
    ): MeshRouter

    @Binds
    @Singleton
    abstract fun bindDeviceIdProvider(
        androidDeviceIdProvider: AndroidDeviceIdProvider
    ): DeviceIdProvider

    @Binds
    @Singleton
    abstract fun bindSystemStatusManager(
        androidSystemStatusManager: AndroidSystemStatusManager
    ): SystemStatusManager

    // Note: TransportMultiplexer is provided by TransportConfigModule to configure transport strategies
}

/**
 * Hilt module for transport strategies
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class TransportModule {

    @Binds
    @IntoMap
    @StringKey("BLUETOOTH_CLASSIC")
    abstract fun bindBluetoothTransport(
        bluetoothClassicTransportStrategy: BluetoothClassicTransportStrategy
    ): TransportStrategy

    @Binds
    @IntoMap
    @StringKey("WIFI_DIRECT")
    abstract fun bindWiFiDirectTransport(
        wiFiDirectTransportStrategy: WiFiDirectTransportStrategy
    ): TransportStrategy
}

/**
 * Hilt module for configuring transport strategies
 */
@Module
@InstallIn(SingletonComponent::class)
object TransportConfigModule {

    @Provides
    @Singleton
    fun provideConfiguredTransportMultiplexer(
        transportStrategies: Map<String, @JvmSuppressWildcards TransportStrategy>,
        loggerInitialized: Boolean // Ensure logger is initialized before creating multiplexer
    ): TransportMultiplexer {
        // Create new instance and add all available transport strategies
        val transportMultiplexer = DefaultTransportMultiplexer()
        transportStrategies.values.forEach { strategy ->
            transportMultiplexer.addTransport(strategy)
        }
        return transportMultiplexer
    }
}

/**
 * Hilt module for logging configuration
 */
@Module
@InstallIn(SingletonComponent::class)
object LoggingModule {

    @Provides
    @Singleton
    fun provideMeshLoggerInitialization(@ApplicationContext context: Context): Boolean {
        com.appvalence.hayatkurtar.core.logging.MeshLogger.initialize(
            context,
            com.appvalence.hayatkurtar.core.logging.MeshLogLevel.DEBUG
        )
        return true // Return success flag
    }
}