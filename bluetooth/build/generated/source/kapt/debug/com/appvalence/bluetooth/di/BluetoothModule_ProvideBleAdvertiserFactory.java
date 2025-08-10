package com.appvalence.bluetooth.di;

import android.content.Context;
import com.appvalence.bluetooth.api.BleAdvertiser;
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
public final class BluetoothModule_ProvideBleAdvertiserFactory implements Factory<BleAdvertiser> {
  private final Provider<Context> contextProvider;

  public BluetoothModule_ProvideBleAdvertiserFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public BleAdvertiser get() {
    return provideBleAdvertiser(contextProvider.get());
  }

  public static BluetoothModule_ProvideBleAdvertiserFactory create(
      Provider<Context> contextProvider) {
    return new BluetoothModule_ProvideBleAdvertiserFactory(contextProvider);
  }

  public static BleAdvertiser provideBleAdvertiser(Context context) {
    return Preconditions.checkNotNullFromProvides(BluetoothModule.INSTANCE.provideBleAdvertiser(context));
  }
}
