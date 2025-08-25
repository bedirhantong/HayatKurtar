package com.appvalence.hayatkurtar.domain.usecase;

import com.appvalence.hayatkurtar.domain.mesh.MeshRouter;
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
public final class GetMeshStatsUseCase_Factory implements Factory<GetMeshStatsUseCase> {
  private final Provider<MeshRouter> meshRouterProvider;

  public GetMeshStatsUseCase_Factory(Provider<MeshRouter> meshRouterProvider) {
    this.meshRouterProvider = meshRouterProvider;
  }

  @Override
  public GetMeshStatsUseCase get() {
    return newInstance(meshRouterProvider.get());
  }

  public static GetMeshStatsUseCase_Factory create(Provider<MeshRouter> meshRouterProvider) {
    return new GetMeshStatsUseCase_Factory(meshRouterProvider);
  }

  public static GetMeshStatsUseCase newInstance(MeshRouter meshRouter) {
    return new GetMeshStatsUseCase(meshRouter);
  }
}
