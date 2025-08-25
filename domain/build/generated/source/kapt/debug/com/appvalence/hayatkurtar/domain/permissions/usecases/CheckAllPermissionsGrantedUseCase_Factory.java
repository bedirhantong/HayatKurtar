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
public final class CheckAllPermissionsGrantedUseCase_Factory implements Factory<CheckAllPermissionsGrantedUseCase> {
  private final Provider<PermissionManager> permissionManagerProvider;

  public CheckAllPermissionsGrantedUseCase_Factory(
      Provider<PermissionManager> permissionManagerProvider) {
    this.permissionManagerProvider = permissionManagerProvider;
  }

  @Override
  public CheckAllPermissionsGrantedUseCase get() {
    return newInstance(permissionManagerProvider.get());
  }

  public static CheckAllPermissionsGrantedUseCase_Factory create(
      Provider<PermissionManager> permissionManagerProvider) {
    return new CheckAllPermissionsGrantedUseCase_Factory(permissionManagerProvider);
  }

  public static CheckAllPermissionsGrantedUseCase newInstance(PermissionManager permissionManager) {
    return new CheckAllPermissionsGrantedUseCase(permissionManager);
  }
}
