package com.appvalence.hayatkurtar.domain.usecase;

import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DiscoverPeersUseCase_Factory implements Factory<DiscoverPeersUseCase> {
  private final Provider<TransportMultiplexer> transportMultiplexerProvider;

  public DiscoverPeersUseCase_Factory(Provider<TransportMultiplexer> transportMultiplexerProvider) {
    this.transportMultiplexerProvider = transportMultiplexerProvider;
  }

  @Override
  public DiscoverPeersUseCase get() {
    return newInstance(transportMultiplexerProvider.get());
  }

  public static DiscoverPeersUseCase_Factory create(
      Provider<TransportMultiplexer> transportMultiplexerProvider) {
    return new DiscoverPeersUseCase_Factory(transportMultiplexerProvider);
  }

  public static DiscoverPeersUseCase newInstance(TransportMultiplexer transportMultiplexer) {
    return new DiscoverPeersUseCase(transportMultiplexer);
  }
}
