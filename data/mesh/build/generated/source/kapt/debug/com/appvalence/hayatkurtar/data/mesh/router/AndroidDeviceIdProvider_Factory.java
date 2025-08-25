package com.appvalence.hayatkurtar.data.mesh.router;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class AndroidDeviceIdProvider_Factory implements Factory<AndroidDeviceIdProvider> {
  @Override
  public AndroidDeviceIdProvider get() {
    return newInstance();
  }

  public static AndroidDeviceIdProvider_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static AndroidDeviceIdProvider newInstance() {
    return new AndroidDeviceIdProvider();
  }

  private static final class InstanceHolder {
    private static final AndroidDeviceIdProvider_Factory INSTANCE = new AndroidDeviceIdProvider_Factory();
  }
}
