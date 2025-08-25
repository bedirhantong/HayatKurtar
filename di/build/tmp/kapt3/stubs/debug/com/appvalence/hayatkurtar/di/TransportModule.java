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
 * Hilt module for transport strategies
 */
@dagger.Module()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H'J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\tH'\u00a8\u0006\n"}, d2 = {"Lcom/appvalence/hayatkurtar/di/TransportModule;", "", "()V", "bindBluetoothTransport", "Lcom/appvalence/hayatkurtar/domain/transport/TransportStrategy;", "bluetoothClassicTransportStrategy", "Lcom/appvalence/hayatkurtar/data/transport/bluetooth/BluetoothClassicTransportStrategy;", "bindWiFiDirectTransport", "wiFiDirectTransportStrategy", "Lcom/appvalence/hayatkurtar/data/transport/wifidirect/WiFiDirectTransportStrategy;", "di_debug"})
@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
public abstract class TransportModule {
    
    public TransportModule() {
        super();
    }
    
    @dagger.Binds()
    @dagger.multibindings.IntoMap()
    @dagger.multibindings.StringKey(value = "BLUETOOTH_CLASSIC")
    @org.jetbrains.annotations.NotNull()
    public abstract com.appvalence.hayatkurtar.domain.transport.TransportStrategy bindBluetoothTransport(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.data.transport.bluetooth.BluetoothClassicTransportStrategy bluetoothClassicTransportStrategy);
    
    @dagger.Binds()
    @dagger.multibindings.IntoMap()
    @dagger.multibindings.StringKey(value = "WIFI_DIRECT")
    @org.jetbrains.annotations.NotNull()
    public abstract com.appvalence.hayatkurtar.domain.transport.TransportStrategy bindWiFiDirectTransport(@org.jetbrains.annotations.NotNull()
    com.appvalence.hayatkurtar.data.transport.wifidirect.WiFiDirectTransportStrategy wiFiDirectTransportStrategy);
}