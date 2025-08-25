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
public final class RequestMeshPermissionsUseCase_Factory implements Factory<RequestMeshPermissionsUseCase> {
  private final Provider<PermissionManager> permissionManagerProvider;

  public RequestMeshPermissionsUseCase_Factory(
      Provider<PermissionManager> permissionManagerProvider) {
    this.permissionManagerProvider = permissionManagerProvider;
  }

  @Override
  public RequestMeshPermissionsUseCase get() {
    return newInstance(permissionManagerProvider.get());
  }

  public static RequestMeshPermissionsUseCase_Factory create(
      Provider<PermissionManager> permissionManagerProvider) {
    return new RequestMeshPermissionsUseCase_Factory(permissionManagerProvider);
  }

  public static RequestMeshPermissionsUseCase newInstance(PermissionManager permissionManager) {
    return new RequestMeshPermissionsUseCase(permissionManager);
  }
}
