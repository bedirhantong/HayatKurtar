package com.appvalence.bluetooth.di;

import android.content.Context;
import com.appvalence.bluetooth.api.BluetoothController;
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
public final class BluetoothModule_ProvideBluetoothControllerFactory implements Factory<BluetoothController> {
  private final Provider<Context> contextProvider;

  public BluetoothModule_ProvideBluetoothControllerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public BluetoothController get() {
    return provideBluetoothController(contextProvider.get());
  }

  public static BluetoothModule_ProvideBluetoothControllerFactory create(
      Provider<Context> contextProvider) {
    return new BluetoothModule_ProvideBluetoothControllerFactory(contextProvider);
  }

  public static BluetoothController provideBluetoothController(Context context) {
    return Preconditions.checkNotNullFromProvides(BluetoothModule.INSTANCE.provideBluetoothController(context));
  }
}
