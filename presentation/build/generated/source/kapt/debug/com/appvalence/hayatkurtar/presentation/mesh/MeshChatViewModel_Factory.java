package com.appvalence.hayatkurtar.presentation.mesh;

import com.appvalence.hayatkurtar.domain.mesh.MeshRouter;
import com.appvalence.hayatkurtar.domain.usecase.GetMeshStatsUseCase;
import com.appvalence.hayatkurtar.domain.usecase.SendEmergencyMessageUseCase;
import com.appvalence.hayatkurtar.domain.usecase.SendMessageUseCase;
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
public final class MeshChatViewModel_Factory implements Factory<MeshChatViewModel> {
  private final Provider<SendMessageUseCase> sendMessageUseCaseProvider;

  private final Provider<SendEmergencyMessageUseCase> sendEmergencyMessageUseCaseProvider;

  private final Provider<GetMeshStatsUseCase> getMeshStatsUseCaseProvider;

  private final Provider<MeshRouter> meshRouterProvider;

  public MeshChatViewModel_Factory(Provider<SendMessageUseCase> sendMessageUseCaseProvider,
      Provider<SendEmergencyMessageUseCase> sendEmergencyMessageUseCaseProvider,
      Provider<GetMeshStatsUseCase> getMeshStatsUseCaseProvider,
      Provider<MeshRouter> meshRouterProvider) {
    this.sendMessageUseCaseProvider = sendMessageUseCaseProvider;
    this.sendEmergencyMessageUseCaseProvider = sendEmergencyMessageUseCaseProvider;
    this.getMeshStatsUseCaseProvider = getMeshStatsUseCaseProvider;
    this.meshRouterProvider = meshRouterProvider;
  }

  @Override
  public MeshChatViewModel get() {
    return newInstance(sendMessageUseCaseProvider.get(), sendEmergencyMessageUseCaseProvider.get(), getMeshStatsUseCaseProvider.get(), meshRouterProvider.get());
  }

  public static MeshChatViewModel_Factory create(
      Provider<SendMessageUseCase> sendMessageUseCaseProvider,
      Provider<SendEmergencyMessageUseCase> sendEmergencyMessageUseCaseProvider,
      Provider<GetMeshStatsUseCase> getMeshStatsUseCaseProvider,
      Provider<MeshRouter> meshRouterProvider) {
    return new MeshChatViewModel_Factory(sendMessageUseCaseProvider, sendEmergencyMessageUseCaseProvider, getMeshStatsUseCaseProvider, meshRouterProvider);
  }

  public static MeshChatViewModel newInstance(SendMessageUseCase sendMessageUseCase,
      SendEmergencyMessageUseCase sendEmergencyMessageUseCase,
      GetMeshStatsUseCase getMeshStatsUseCase, MeshRouter meshRouter) {
    return new MeshChatViewModel(sendMessageUseCase, sendEmergencyMessageUseCase, getMeshStatsUseCase, meshRouter);
  }
}
