package com.appvalence.hayatkurtar.data.mesh.router;

import com.appvalence.hayatkurtar.data.mesh.store.RoomMessageStore;
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class DefaultMeshRouter_Factory implements Factory<DefaultMeshRouter> {
  private final Provider<TransportMultiplexer> transportMultiplexerProvider;

  private final Provider<RoomMessageStore> messageStoreProvider;

  private final Provider<DeviceIdProvider> deviceIdProvider;

  public DefaultMeshRouter_Factory(Provider<TransportMultiplexer> transportMultiplexerProvider,
      Provider<RoomMessageStore> messageStoreProvider,
      Provider<DeviceIdProvider> deviceIdProvider) {
    this.transportMultiplexerProvider = transportMultiplexerProvider;
    this.messageStoreProvider = messageStoreProvider;
    this.deviceIdProvider = deviceIdProvider;
  }

  @Override
  public DefaultMeshRouter get() {
    return newInstance(transportMultiplexerProvider.get(), messageStoreProvider.get(), deviceIdProvider.get());
  }

  public static DefaultMeshRouter_Factory create(
      Provider<TransportMultiplexer> transportMultiplexerProvider,
      Provider<RoomMessageStore> messageStoreProvider,
      Provider<DeviceIdProvider> deviceIdProvider) {
    return new DefaultMeshRouter_Factory(transportMultiplexerProvider, messageStoreProvider, deviceIdProvider);
  }

  public static DefaultMeshRouter newInstance(TransportMultiplexer transportMultiplexer,
      RoomMessageStore messageStore, DeviceIdProvider deviceIdProvider) {
    return new DefaultMeshRouter(transportMultiplexer, messageStore, deviceIdProvider);
  }
}
