package com.appvalence.hayatkurtar.di;

import android.content.Context;
import androidx.room.Room;
import com.appvalence.bluetooth.api.BluetoothController;
import com.appvalence.bluetooth.api.HighPerformanceScanner;
import com.appvalence.hayatkurtar.data.mesh.database.MeshDatabase;
import com.appvalence.hayatkurtar.data.mesh.router.AndroidDeviceIdProvider;
import com.appvalence.hayatkurtar.data.mesh.router.DefaultMeshRouter;
import com.appvalence.hayatkurtar.data.mesh.router.DeviceIdProvider;
import com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao;
import com.appvalence.hayatkurtar.data.mesh.store.RoomMessageStore;
import com.appvalence.hayatkurtar.data.mesh.transport.DefaultTransportMultiplexer;
import com.appvalence.hayatkurtar.data.system.AndroidSystemStatusManager;
import com.appvalence.hayatkurtar.data.transport.bluetooth.BluetoothClassicTransportStrategy;
import com.appvalence.hayatkurtar.data.transport.wifidirect.WiFiDirectTransportStrategy;
import com.appvalence.hayatkurtar.domain.mesh.MessageStore;
import com.appvalence.hayatkurtar.domain.mesh.MeshRouter;
import com.appvalence.hayatkurtar.domain.system.SystemStatusManager;
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer;
import com.appvalence.hayatkurtar.domain.transport.TransportStrategy;
import com.appvalence.hayatkurtar.domain.transport.TransportType;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;
import javax.inject.Singleton;

/**
 * Hilt module for configuring transport strategies
 */
@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J)\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\t\u0012\u00070\b\u00a2\u0006\u0002\b\t0\u00062\u0006\u0010\n\u001a\u00020\u000bH\u0007\u00a8\u0006\f"}, d2 = {"Lcom/appvalence/hayatkurtar/di/TransportConfigModule;", "", "()V", "provideConfiguredTransportMultiplexer", "Lcom/appvalence/hayatkurtar/domain/transport/TransportMultiplexer;", "transportStrategies", "", "", "Lcom/appvalence/hayatkurtar/domain/transport/TransportStrategy;", "Lkotlin/jvm/JvmSuppressWildcards;", "loggerInitialized", "", "di_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public final class TransportConfigModule {
    @org.jetbrains.annotations.NotNull()
    public static final com.appvalence.hayatkurtar.di.TransportConfigModule INSTANCE = null;
    
    private TransportConfigModule() {
        super();
    }
    
    @dagger.Provides()
    @javax.inject.Singleton()
    @org.jetbrains.annotations.NotNull()
    public final com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer provideConfiguredTransportMultiplexer(@org.jetbrains.annotations.NotNull()
    java.util.Map<java.lang.String, com.appvalence.hayatkurtar.domain.transport.TransportStrategy> transportStrategies, boolean loggerInitialized) {
        return null;
    }
}