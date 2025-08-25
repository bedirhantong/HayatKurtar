package com.appvalence.hayatkurtar.presentation.e2e;

import com.appvalence.hayatkurtar.core.crypto.E2EEncryptionManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class E2EViewModel_Factory implements Factory<E2EViewModel> {
  private final Provider<E2EEncryptionManager> e2eManagerProvider;

  public E2EViewModel_Factory(Provider<E2EEncryptionManager> e2eManagerProvider) {
    this.e2eManagerProvider = e2eManagerProvider;
  }

  @Override
  public E2EViewModel get() {
    return newInstance(e2eManagerProvider.get());
  }

  public static E2EViewModel_Factory create(Provider<E2EEncryptionManager> e2eManagerProvider) {
    return new E2EViewModel_Factory(e2eManagerProvider);
  }

  public static E2EViewModel newInstance(E2EEncryptionManager e2eManager) {
    return new E2EViewModel(e2eManager);
  }
}
