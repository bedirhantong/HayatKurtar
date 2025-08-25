package com.appvalence.hayatkurtar.presentation.permissions;

import com.appvalence.hayatkurtar.domain.permissions.PermissionManager;
import com.appvalence.hayatkurtar.domain.permissions.usecases.CheckAllPermissionsGrantedUseCase;
import com.appvalence.hayatkurtar.domain.permissions.usecases.GetMissingPermissionsUseCase;
import com.appvalence.hayatkurtar.domain.permissions.usecases.GetPermissionStateUseCase;
import com.appvalence.hayatkurtar.domain.permissions.usecases.HandlePermanentlyDeniedPermissionsUseCase;
import com.appvalence.hayatkurtar.domain.permissions.usecases.RequestMeshPermissionsUseCase;
import com.appvalence.hayatkurtar.domain.permissions.usecases.RequestSinglePermissionUseCase;
import com.appvalence.hayatkurtar.domain.permissions.usecases.ValidateMeshNetworkingReadinessUseCase;
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
public final class PermissionViewModel_Factory implements Factory<PermissionViewModel> {
  private final Provider<GetPermissionStateUseCase> getPermissionStateUseCaseProvider;

  private final Provider<RequestMeshPermissionsUseCase> requestMeshPermissionsUseCaseProvider;

  private final Provider<RequestSinglePermissionUseCase> requestSinglePermissionUseCaseProvider;

  private final Provider<CheckAllPermissionsGrantedUseCase> checkAllPermissionsGrantedUseCaseProvider;

  private final Provider<GetMissingPermissionsUseCase> getMissingPermissionsUseCaseProvider;

  private final Provider<HandlePermanentlyDeniedPermissionsUseCase> handlePermanentlyDeniedPermissionsUseCaseProvider;

  private final Provider<ValidateMeshNetworkingReadinessUseCase> validateMeshNetworkingReadinessUseCaseProvider;

  private final Provider<PermissionManager> permissionManagerProvider;

  public PermissionViewModel_Factory(
      Provider<GetPermissionStateUseCase> getPermissionStateUseCaseProvider,
      Provider<RequestMeshPermissionsUseCase> requestMeshPermissionsUseCaseProvider,
      Provider<RequestSinglePermissionUseCase> requestSinglePermissionUseCaseProvider,
      Provider<CheckAllPermissionsGrantedUseCase> checkAllPermissionsGrantedUseCaseProvider,
      Provider<GetMissingPermissionsUseCase> getMissingPermissionsUseCaseProvider,
      Provider<HandlePermanentlyDeniedPermissionsUseCase> handlePermanentlyDeniedPermissionsUseCaseProvider,
      Provider<ValidateMeshNetworkingReadinessUseCase> validateMeshNetworkingReadinessUseCaseProvider,
      Provider<PermissionManager> permissionManagerProvider) {
    this.getPermissionStateUseCaseProvider = getPermissionStateUseCaseProvider;
    this.requestMeshPermissionsUseCaseProvider = requestMeshPermissionsUseCaseProvider;
    this.requestSinglePermissionUseCaseProvider = requestSinglePermissionUseCaseProvider;
    this.checkAllPermissionsGrantedUseCaseProvider = checkAllPermissionsGrantedUseCaseProvider;
    this.getMissingPermissionsUseCaseProvider = getMissingPermissionsUseCaseProvider;
    this.handlePermanentlyDeniedPermissionsUseCaseProvider = handlePermanentlyDeniedPermissionsUseCaseProvider;
    this.validateMeshNetworkingReadinessUseCaseProvider = validateMeshNetworkingReadinessUseCaseProvider;
    this.permissionManagerProvider = permissionManagerProvider;
  }

  @Override
  public PermissionViewModel get() {
    return newInstance(getPermissionStateUseCaseProvider.get(), requestMeshPermissionsUseCaseProvider.get(), requestSinglePermissionUseCaseProvider.get(), checkAllPermissionsGrantedUseCaseProvider.get(), getMissingPermissionsUseCaseProvider.get(), handlePermanentlyDeniedPermissionsUseCaseProvider.get(), validateMeshNetworkingReadinessUseCaseProvider.get(), permissionManagerProvider.get());
  }

  public static PermissionViewModel_Factory create(
      Provider<GetPermissionStateUseCase> getPermissionStateUseCaseProvider,
      Provider<RequestMeshPermissionsUseCase> requestMeshPermissionsUseCaseProvider,
      Provider<RequestSinglePermissionUseCase> requestSinglePermissionUseCaseProvider,
      Provider<CheckAllPermissionsGrantedUseCase> checkAllPermissionsGrantedUseCaseProvider,
      Provider<GetMissingPermissionsUseCase> getMissingPermissionsUseCaseProvider,
      Provider<HandlePermanentlyDeniedPermissionsUseCase> handlePermanentlyDeniedPermissionsUseCaseProvider,
      Provider<ValidateMeshNetworkingReadinessUseCase> validateMeshNetworkingReadinessUseCaseProvider,
      Provider<PermissionManager> permissionManagerProvider) {
    return new PermissionViewModel_Factory(getPermissionStateUseCaseProvider, requestMeshPermissionsUseCaseProvider, requestSinglePermissionUseCaseProvider, checkAllPermissionsGrantedUseCaseProvider, getMissingPermissionsUseCaseProvider, handlePermanentlyDeniedPermissionsUseCaseProvider, validateMeshNetworkingReadinessUseCaseProvider, permissionManagerProvider);
  }

  public static PermissionViewModel newInstance(GetPermissionStateUseCase getPermissionStateUseCase,
      RequestMeshPermissionsUseCase requestMeshPermissionsUseCase,
      RequestSinglePermissionUseCase requestSinglePermissionUseCase,
      CheckAllPermissionsGrantedUseCase checkAllPermissionsGrantedUseCase,
      GetMissingPermissionsUseCase getMissingPermissionsUseCase,
      HandlePermanentlyDeniedPermissionsUseCase handlePermanentlyDeniedPermissionsUseCase,
      ValidateMeshNetworkingReadinessUseCase validateMeshNetworkingReadinessUseCase,
      PermissionManager permissionManager) {
    return new PermissionViewModel(getPermissionStateUseCase, requestMeshPermissionsUseCase, requestSinglePermissionUseCase, checkAllPermissionsGrantedUseCase, getMissingPermissionsUseCase, handlePermanentlyDeniedPermissionsUseCase, validateMeshNetworkingReadinessUseCase, permissionManager);
  }
}
