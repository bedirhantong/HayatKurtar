package com.appvalence.hayatkurtar.data.system;

import android.content.Context;
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AndroidSystemStatusManager_Factory implements Factory<AndroidSystemStatusManager> {
  private final Provider<Context> contextProvider;

  private final Provider<TransportMultiplexer> transportMultiplexerProvider;

  public AndroidSystemStatusManager_Factory(Provider<Context> contextProvider,
      Provider<TransportMultiplexer> transportMultiplexerProvider) {
    this.contextProvider = contextProvider;
    this.transportMultiplexerProvider = transportMultiplexerProvider;
  }

  @Override
  public AndroidSystemStatusManager get() {
    return newInstance(contextProvider.get(), transportMultiplexerProvider.get());
  }

  public static AndroidSystemStatusManager_Factory create(Provider<Context> contextProvider,
      Provider<TransportMultiplexer> transportMultiplexerProvider) {
    return new AndroidSystemStatusManager_Factory(contextProvider, transportMultiplexerProvider);
  }

  public static AndroidSystemStatusManager newInstance(Context context,
      TransportMultiplexer transportMultiplexer) {
    return new AndroidSystemStatusManager(context, transportMultiplexer);
  }
}
