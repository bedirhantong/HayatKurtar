package com.appvalence.bluetooth.di;

import android.content.Context;
import com.appvalence.bluetooth.api.HighPerformanceScanner;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class BluetoothModule_ProvideHighPerformanceScannerFactory implements Factory<HighPerformanceScanner> {
  private final Provider<Context> contextProvider;

  public BluetoothModule_ProvideHighPerformanceScannerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public HighPerformanceScanner get() {
    return provideHighPerformanceScanner(contextProvider.get());
  }

  public static BluetoothModule_ProvideHighPerformanceScannerFactory create(
      Provider<Context> contextProvider) {
    return new BluetoothModule_ProvideHighPerformanceScannerFactory(contextProvider);
  }

  public static HighPerformanceScanner provideHighPerformanceScanner(Context context) {
    return Preconditions.checkNotNullFromProvides(BluetoothModule.INSTANCE.provideHighPerformanceScanner(context));
  }
}
