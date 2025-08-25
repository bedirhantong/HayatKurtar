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
public final class AcknowledgeMessageUseCase_Factory implements Factory<AcknowledgeMessageUseCase> {
  private final Provider<MeshRouter> meshRouterProvider;

  public AcknowledgeMessageUseCase_Factory(Provider<MeshRouter> meshRouterProvider) {
    this.meshRouterProvider = meshRouterProvider;
  }

  @Override
  public AcknowledgeMessageUseCase get() {
    return newInstance(meshRouterProvider.get());
  }

  public static AcknowledgeMessageUseCase_Factory create(Provider<MeshRouter> meshRouterProvider) {
    return new AcknowledgeMessageUseCase_Factory(meshRouterProvider);
  }

  public static AcknowledgeMessageUseCase newInstance(MeshRouter meshRouter) {
    return new AcknowledgeMessageUseCase(meshRouter);
  }
}
