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
public final class SendMessageUseCase_Factory implements Factory<SendMessageUseCase> {
  private final Provider<MeshRouter> meshRouterProvider;

  public SendMessageUseCase_Factory(Provider<MeshRouter> meshRouterProvider) {
    this.meshRouterProvider = meshRouterProvider;
  }

  @Override
  public SendMessageUseCase get() {
    return newInstance(meshRouterProvider.get());
  }

  public static SendMessageUseCase_Factory create(Provider<MeshRouter> meshRouterProvider) {
    return new SendMessageUseCase_Factory(meshRouterProvider);
  }

  public static SendMessageUseCase newInstance(MeshRouter meshRouter) {
    return new SendMessageUseCase(meshRouter);
  }
}
