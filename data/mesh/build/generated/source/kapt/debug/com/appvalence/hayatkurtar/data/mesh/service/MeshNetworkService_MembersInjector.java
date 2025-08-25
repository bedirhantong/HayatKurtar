package com.appvalence.hayatkurtar.data.mesh.service;

import com.appvalence.hayatkurtar.domain.mesh.MeshRouter;
import com.appvalence.hayatkurtar.domain.transport.TransportMultiplexer;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class MeshNetworkService_MembersInjector implements MembersInjector<MeshNetworkService> {
  private final Provider<MeshRouter> meshRouterProvider;

  private final Provider<TransportMultiplexer> transportMultiplexerProvider;

  public MeshNetworkService_MembersInjector(Provider<MeshRouter> meshRouterProvider,
      Provider<TransportMultiplexer> transportMultiplexerProvider) {
    this.meshRouterProvider = meshRouterProvider;
    this.transportMultiplexerProvider = transportMultiplexerProvider;
  }

  public static MembersInjector<MeshNetworkService> create(Provider<MeshRouter> meshRouterProvider,
      Provider<TransportMultiplexer> transportMultiplexerProvider) {
    return new MeshNetworkService_MembersInjector(meshRouterProvider, transportMultiplexerProvider);
  }

  @Override
  public void injectMembers(MeshNetworkService instance) {
    injectMeshRouter(instance, meshRouterProvider.get());
    injectTransportMultiplexer(instance, transportMultiplexerProvider.get());
  }

  @InjectedFieldSignature("com.appvalence.hayatkurtar.data.mesh.service.MeshNetworkService.meshRouter")
  public static void injectMeshRouter(MeshNetworkService instance, MeshRouter meshRouter) {
    instance.meshRouter = meshRouter;
  }

  @InjectedFieldSignature("com.appvalence.hayatkurtar.data.mesh.service.MeshNetworkService.transportMultiplexer")
  public static void injectTransportMultiplexer(MeshNetworkService instance,
      TransportMultiplexer transportMultiplexer) {
    instance.transportMultiplexer = transportMultiplexer;
  }
}
