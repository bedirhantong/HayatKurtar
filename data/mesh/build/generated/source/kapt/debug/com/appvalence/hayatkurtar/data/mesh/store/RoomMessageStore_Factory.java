package com.appvalence.hayatkurtar.data.mesh.store;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class RoomMessageStore_Factory implements Factory<RoomMessageStore> {
  private final Provider<MessageStoreDao> daoProvider;

  public RoomMessageStore_Factory(Provider<MessageStoreDao> daoProvider) {
    this.daoProvider = daoProvider;
  }

  @Override
  public RoomMessageStore get() {
    return newInstance(daoProvider.get());
  }

  public static RoomMessageStore_Factory create(Provider<MessageStoreDao> daoProvider) {
    return new RoomMessageStore_Factory(daoProvider);
  }

  public static RoomMessageStore newInstance(MessageStoreDao dao) {
    return new RoomMessageStore(dao);
  }
}
