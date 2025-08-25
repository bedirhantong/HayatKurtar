package com.appvalence.hayatkurtar.di;

import android.content.Context;
import com.appvalence.hayatkurtar.data.mesh.database.MeshDatabase;
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
public final class DatabaseModule_ProvideMeshDatabaseFactory implements Factory<MeshDatabase> {
  private final Provider<Context> contextProvider;

  public DatabaseModule_ProvideMeshDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MeshDatabase get() {
    return provideMeshDatabase(contextProvider.get());
  }

  public static DatabaseModule_ProvideMeshDatabaseFactory create(
      Provider<Context> contextProvider) {
    return new DatabaseModule_ProvideMeshDatabaseFactory(contextProvider);
  }

  public static MeshDatabase provideMeshDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(DatabaseModule.INSTANCE.provideMeshDatabase(context));
  }
}
