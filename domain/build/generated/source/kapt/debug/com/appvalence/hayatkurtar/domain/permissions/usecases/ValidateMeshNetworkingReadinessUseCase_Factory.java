package com.appvalence.hayatkurtar.domain.permissions.usecases;

import com.appvalence.hayatkurtar.domain.permissions.PermissionManager;
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
public final class ValidateMeshNetworkingReadinessUseCase_Factory implements Factory<ValidateMeshNetworkingReadinessUseCase> {
  private final Provider<PermissionManager> permissionManagerProvider;

  public ValidateMeshNetworkingReadinessUseCase_Factory(
      Provider<PermissionManager> permissionManagerProvider) {
    this.permissionManagerProvider = permissionManagerProvider;
  }

  @Override
  public ValidateMeshNetworkingReadinessUseCase get() {
    return newInstance(permissionManagerProvider.get());
  }

  public static ValidateMeshNetworkingReadinessUseCase_Factory create(
      Provider<PermissionManager> permissionManagerProvider) {
    return new ValidateMeshNetworkingReadinessUseCase_Factory(permissionManagerProvider);
  }

  public static ValidateMeshNetworkingReadinessUseCase newInstance(
      PermissionManager permissionManager) {
    return new ValidateMeshNetworkingReadinessUseCase(permissionManager);
  }
}
