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
public final class GetConnectedPeersUseCase_Factory implements Factory<GetConnectedPeersUseCase> {
  private final Provider<TransportMultiplexer> transportMultiplexerProvider;

  public GetConnectedPeersUseCase_Factory(
      Provider<TransportMultiplexer> transportMultiplexerProvider) {
    this.transportMultiplexerProvider = transportMultiplexerProvider;
  }

  @Override
  public GetConnectedPeersUseCase get() {
    return newInstance(transportMultiplexerProvider.get());
  }

  public static GetConnectedPeersUseCase_Factory create(
      Provider<TransportMultiplexer> transportMultiplexerProvider) {
    return new GetConnectedPeersUseCase_Factory(transportMultiplexerProvider);
  }

  public static GetConnectedPeersUseCase newInstance(TransportMultiplexer transportMultiplexer) {
    return new GetConnectedPeersUseCase(transportMultiplexer);
  }
}
