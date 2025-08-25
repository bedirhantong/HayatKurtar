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
public final class GetMissingPermissionsUseCase_Factory implements Factory<GetMissingPermissionsUseCase> {
  private final Provider<PermissionManager> permissionManagerProvider;

  public GetMissingPermissionsUseCase_Factory(
      Provider<PermissionManager> permissionManagerProvider) {
    this.permissionManagerProvider = permissionManagerProvider;
  }

  @Override
  public GetMissingPermissionsUseCase get() {
    return newInstance(permissionManagerProvider.get());
  }

  public static GetMissingPermissionsUseCase_Factory create(
      Provider<PermissionManager> permissionManagerProvider) {
    return new GetMissingPermissionsUseCase_Factory(permissionManagerProvider);
  }

  public static GetMissingPermissionsUseCase newInstance(PermissionManager permissionManager) {
    return new GetMissingPermissionsUseCase(permissionManager);
  }
}
