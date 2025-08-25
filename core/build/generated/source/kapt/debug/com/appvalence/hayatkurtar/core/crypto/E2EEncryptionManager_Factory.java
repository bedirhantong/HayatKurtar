package com.appvalence.hayatkurtar.core.crypto;

import android.content.Context;
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
public final class E2EEncryptionManager_Factory implements Factory<E2EEncryptionManager> {
  private final Provider<Context> contextProvider;

  public E2EEncryptionManager_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public E2EEncryptionManager get() {
    return newInstance(contextProvider.get());
  }

  public static E2EEncryptionManager_Factory create(Provider<Context> contextProvider) {
    return new E2EEncryptionManager_Factory(contextProvider);
  }

  public static E2EEncryptionManager newInstance(Context context) {
    return new E2EEncryptionManager(context);
  }
}
