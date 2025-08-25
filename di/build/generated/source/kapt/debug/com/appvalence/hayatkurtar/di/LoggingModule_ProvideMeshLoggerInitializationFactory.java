package com.appvalence.hayatkurtar.di;

import android.content.Context;
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
public final class LoggingModule_ProvideMeshLoggerInitializationFactory implements Factory<Boolean> {
  private final Provider<Context> contextProvider;

  public LoggingModule_ProvideMeshLoggerInitializationFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public Boolean get() {
    return provideMeshLoggerInitialization(contextProvider.get());
  }

  public static LoggingModule_ProvideMeshLoggerInitializationFactory create(
      Provider<Context> contextProvider) {
    return new LoggingModule_ProvideMeshLoggerInitializationFactory(contextProvider);
  }

  public static boolean provideMeshLoggerInitialization(Context context) {
    return LoggingModule.INSTANCE.provideMeshLoggerInitialization(context);
  }
}
