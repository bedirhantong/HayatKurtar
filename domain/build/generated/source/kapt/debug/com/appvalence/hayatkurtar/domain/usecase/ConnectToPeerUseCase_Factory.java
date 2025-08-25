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
public final class ConnectToPeerUseCase_Factory implements Factory<ConnectToPeerUseCase> {
  private final Provider<TransportMultiplexer> transportMultiplexerProvider;

  public ConnectToPeerUseCase_Factory(Provider<TransportMultiplexer> transportMultiplexerProvider) {
    this.transportMultiplexerProvider = transportMultiplexerProvider;
  }

  @Override
  public ConnectToPeerUseCase get() {
    return newInstance(transportMultiplexerProvider.get());
  }

  public static ConnectToPeerUseCase_Factory create(
      Provider<TransportMultiplexer> transportMultiplexerProvider) {
    return new ConnectToPeerUseCase_Factory(transportMultiplexerProvider);
  }

  public static ConnectToPeerUseCase newInstance(TransportMultiplexer transportMultiplexer) {
    return new ConnectToPeerUseCase(transportMultiplexer);
  }
}
