package com.appvalence.hayatkurtar.presentation.system;

import com.appvalence.hayatkurtar.domain.system.SystemStatusManager;
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
public final class SystemStatusViewModel_Factory implements Factory<SystemStatusViewModel> {
  private final Provider<SystemStatusManager> systemStatusManagerProvider;

  public SystemStatusViewModel_Factory(Provider<SystemStatusManager> systemStatusManagerProvider) {
    this.systemStatusManagerProvider = systemStatusManagerProvider;
  }

  @Override
  public SystemStatusViewModel get() {
    return newInstance(systemStatusManagerProvider.get());
  }

  public static SystemStatusViewModel_Factory create(
      Provider<SystemStatusManager> systemStatusManagerProvider) {
    return new SystemStatusViewModel_Factory(systemStatusManagerProvider);
  }

  public static SystemStatusViewModel newInstance(SystemStatusManager systemStatusManager) {
    return new SystemStatusViewModel(systemStatusManager);
  }
}
