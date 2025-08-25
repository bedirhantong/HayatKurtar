package com.appvalence.hayatkurtar.di;

import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer;
import com.appvalence.hayatkurtar.domain.transport.TransportStrategy;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import java.util.Map;
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
public final class TransportConfigModule_ProvideConfiguredTransportMultiplexerFactory implements Factory<TransportMultiplexer> {
  private final Provider<Map<String, TransportStrategy>> transportStrategiesProvider;

  private final Provider<Boolean> loggerInitializedProvider;

  public TransportConfigModule_ProvideConfiguredTransportMultiplexerFactory(
      Provider<Map<String, TransportStrategy>> transportStrategiesProvider,
      Provider<Boolean> loggerInitializedProvider) {
    this.transportStrategiesProvider = transportStrategiesProvider;
    this.loggerInitializedProvider = loggerInitializedProvider;
  }

  @Override
  public TransportMultiplexer get() {
    return provideConfiguredTransportMultiplexer(transportStrategiesProvider.get(), loggerInitializedProvider.get());
  }

  public static TransportConfigModule_ProvideConfiguredTransportMultiplexerFactory create(
      Provider<Map<String, TransportStrategy>> transportStrategiesProvider,
      Provider<Boolean> loggerInitializedProvider) {
    return new TransportConfigModule_ProvideConfiguredTransportMultiplexerFactory(transportStrategiesProvider, loggerInitializedProvider);
  }

  public static TransportMultiplexer provideConfiguredTransportMultiplexer(
      Map<String, TransportStrategy> transportStrategies, boolean loggerInitialized) {
    return Preconditions.checkNotNullFromProvides(TransportConfigModule.INSTANCE.provideConfiguredTransportMultiplexer(transportStrategies, loggerInitialized));
  }
}
