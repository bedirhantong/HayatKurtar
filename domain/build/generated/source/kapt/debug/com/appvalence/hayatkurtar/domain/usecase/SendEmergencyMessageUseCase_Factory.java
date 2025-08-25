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
public final class SendEmergencyMessageUseCase_Factory implements Factory<SendEmergencyMessageUseCase> {
  private final Provider<MeshRouter> meshRouterProvider;

  public SendEmergencyMessageUseCase_Factory(Provider<MeshRouter> meshRouterProvider) {
    this.meshRouterProvider = meshRouterProvider;
  }

  @Override
  public SendEmergencyMessageUseCase get() {
    return newInstance(meshRouterProvider.get());
  }

  public static SendEmergencyMessageUseCase_Factory create(
      Provider<MeshRouter> meshRouterProvider) {
    return new SendEmergencyMessageUseCase_Factory(meshRouterProvider);
  }

  public static SendEmergencyMessageUseCase newInstance(MeshRouter meshRouter) {
    return new SendEmergencyMessageUseCase(meshRouter);
  }
}
