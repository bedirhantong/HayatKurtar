package com.appvalence.hayatkurtar.data.mesh.transport;

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
public final class DefaultTransportMultiplexer_Factory implements Factory<DefaultTransportMultiplexer> {
  @Override
  public DefaultTransportMultiplexer get() {
    return newInstance();
  }

  public static DefaultTransportMultiplexer_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DefaultTransportMultiplexer newInstance() {
    return new DefaultTransportMultiplexer();
  }

  private static final class InstanceHolder {
    private static final DefaultTransportMultiplexer_Factory INSTANCE = new DefaultTransportMultiplexer_Factory();
  }
}
