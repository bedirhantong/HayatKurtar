package com.appvalence.bluetooth.di;

import com.appvalence.bluetooth.api.DistanceEstimator;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;

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
public final class BluetoothModule_ProvideDistanceEstimatorFactory implements Factory<DistanceEstimator> {
  @Override
  public DistanceEstimator get() {
    return provideDistanceEstimator();
  }

  public static BluetoothModule_ProvideDistanceEstimatorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DistanceEstimator provideDistanceEstimator() {
    return Preconditions.checkNotNullFromProvides(BluetoothModule.INSTANCE.provideDistanceEstimator());
  }

  private static final class InstanceHolder {
    private static final BluetoothModule_ProvideDistanceEstimatorFactory INSTANCE = new BluetoothModule_ProvideDistanceEstimatorFactory();
  }
}
