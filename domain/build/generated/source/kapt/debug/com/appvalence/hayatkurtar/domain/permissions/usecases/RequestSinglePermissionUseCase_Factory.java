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
public final class RequestSinglePermissionUseCase_Factory implements Factory<RequestSinglePermissionUseCase> {
  private final Provider<PermissionManager> permissionManagerProvider;

  public RequestSinglePermissionUseCase_Factory(
      Provider<PermissionManager> permissionManagerProvider) {
    this.permissionManagerProvider = permissionManagerProvider;
  }

  @Override
  public RequestSinglePermissionUseCase get() {
    return newInstance(permissionManagerProvider.get());
  }

  public static RequestSinglePermissionUseCase_Factory create(
      Provider<PermissionManager> permissionManagerProvider) {
    return new RequestSinglePermissionUseCase_Factory(permissionManagerProvider);
  }

  public static RequestSinglePermissionUseCase newInstance(PermissionManager permissionManager) {
    return new RequestSinglePermissionUseCase(permissionManager);
  }
}
