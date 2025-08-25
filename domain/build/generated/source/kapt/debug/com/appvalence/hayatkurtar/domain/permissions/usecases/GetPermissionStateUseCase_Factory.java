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
public final class GetPermissionStateUseCase_Factory implements Factory<GetPermissionStateUseCase> {
  private final Provider<PermissionManager> permissionManagerProvider;

  public GetPermissionStateUseCase_Factory(Provider<PermissionManager> permissionManagerProvider) {
    this.permissionManagerProvider = permissionManagerProvider;
  }

  @Override
  public GetPermissionStateUseCase get() {
    return newInstance(permissionManagerProvider.get());
  }

  public static GetPermissionStateUseCase_Factory create(
      Provider<PermissionManager> permissionManagerProvider) {
    return new GetPermissionStateUseCase_Factory(permissionManagerProvider);
  }

  public static GetPermissionStateUseCase newInstance(PermissionManager permissionManager) {
    return new GetPermissionStateUseCase(permissionManager);
  }
}
