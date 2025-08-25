package com.appvalence.hayatkurtar.di;

import com.appvalence.hayatkurtar.data.mesh.database.MeshDatabase;
import com.appvalence.hayatkurtar.data.mesh.store.MessageStoreDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class DatabaseModule_ProvideMessageStoreDaoFactory implements Factory<MessageStoreDao> {
  private final Provider<MeshDatabase> databaseProvider;

  public DatabaseModule_ProvideMessageStoreDaoFactory(Provider<MeshDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public MessageStoreDao get() {
    return provideMessageStoreDao(databaseProvider.get());
  }

  public static DatabaseModule_ProvideMessageStoreDaoFactory create(
      Provider<MeshDatabase> databaseProvider) {
    return new DatabaseModule_ProvideMessageStoreDaoFactory(databaseProvider);
  }

  public static MessageStoreDao provideMessageStoreDao(MeshDatabase database) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMessageStoreDao(database));
  }
}
