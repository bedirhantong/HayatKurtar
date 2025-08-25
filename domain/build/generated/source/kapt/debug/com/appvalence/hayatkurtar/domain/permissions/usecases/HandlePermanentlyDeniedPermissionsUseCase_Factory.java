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
public final class HandlePermanentlyDeniedPermissionsUseCase_Factory implements Factory<HandlePermanentlyDeniedPermissionsUseCase> {
  private final Provider<PermissionManager> permissionManagerProvider;

  public HandlePermanentlyDeniedPermissionsUseCase_Factory(
      Provider<PermissionManager> permissionManagerProvider) {
    this.permissionManagerProvider = permissionManagerProvider;
  }

  @Override
  public HandlePermanentlyDeniedPermissionsUseCase get() {
    return newInstance(permissionManagerProvider.get());
  }

  public static HandlePermanentlyDeniedPermissionsUseCase_Factory create(
      Provider<PermissionManager> permissionManagerProvider) {
    return new HandlePermanentlyDeniedPermissionsUseCase_Factory(permissionManagerProvider);
  }

  public static HandlePermanentlyDeniedPermissionsUseCase newInstance(
      PermissionManager permissionManager) {
    return new HandlePermanentlyDeniedPermissionsUseCase(permissionManager);
  }
}
