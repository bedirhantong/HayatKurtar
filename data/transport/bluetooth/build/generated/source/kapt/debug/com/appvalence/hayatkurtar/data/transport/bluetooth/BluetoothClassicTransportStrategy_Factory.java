package com.appvalence.hayatkurtar.data.transport.bluetooth;

import android.content.Context;
import com.appvalence.bluetooth.api.BluetoothController;
import com.appvalence.bluetooth.api.HighPerformanceScanner;
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
public final class BluetoothClassicTransportStrategy_Factory implements Factory<BluetoothClassicTransportStrategy> {
  private final Provider<Context> contextProvider;

  private final Provider<BluetoothController> bluetoothControllerProvider;

  private final Provider<HighPerformanceScanner> scannerProvider;

  public BluetoothClassicTransportStrategy_Factory(Provider<Context> contextProvider,
      Provider<BluetoothController> bluetoothControllerProvider,
      Provider<HighPerformanceScanner> scannerProvider) {
    this.contextProvider = contextProvider;
    this.bluetoothControllerProvider = bluetoothControllerProvider;
    this.scannerProvider = scannerProvider;
  }

  @Override
  public BluetoothClassicTransportStrategy get() {
    return newInstance(contextProvider.get(), bluetoothControllerProvider.get(), scannerProvider.get());
  }

  public static BluetoothClassicTransportStrategy_Factory create(Provider<Context> contextProvider,
      Provider<BluetoothController> bluetoothControllerProvider,
      Provider<HighPerformanceScanner> scannerProvider) {
    return new BluetoothClassicTransportStrategy_Factory(contextProvider, bluetoothControllerProvider, scannerProvider);
  }

  public static BluetoothClassicTransportStrategy newInstance(Context context,
      BluetoothController bluetoothController, HighPerformanceScanner scanner) {
    return new BluetoothClassicTransportStrategy(context, bluetoothController, scanner);
  }
}
