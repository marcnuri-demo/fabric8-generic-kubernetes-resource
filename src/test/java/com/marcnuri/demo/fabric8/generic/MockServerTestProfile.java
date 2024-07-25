package com.marcnuri.demo.fabric8.generic;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.server.mock.KubernetesMixedDispatcher;
import io.fabric8.kubernetes.client.server.mock.KubernetesMockServer;
import io.fabric8.kubernetes.client.utils.Serialization;
import io.fabric8.mockwebserver.Context;
import io.fabric8.mockwebserver.ServerRequest;
import io.fabric8.mockwebserver.ServerResponse;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.test.junit.QuarkusTestProfile;
import jakarta.annotation.Priority;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Alternative;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.mockwebserver.MockWebServer;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class MockServerTestProfile implements QuarkusTestProfile {
  
  @Produces
  @Singleton
  KubernetesMockServer kubernetesMockServer() {
    final Map<ServerRequest, Queue<ServerResponse>> responses = new HashMap<>();
    final var kubernetesMockServer = new KubernetesMockServer(new Context(Serialization.jsonMapper()), new MockWebServer(), responses, new KubernetesMixedDispatcher(responses), true);
    kubernetesMockServer.init();
    return kubernetesMockServer;
  }
  
  @Produces
  @Singleton
  @Alternative
  @Priority(1)
  KubernetesClient kubernetesClient(KubernetesMockServer kubernetesMockServer) {
    return kubernetesMockServer.createClient();
  }


  void onEnd(@Observes ShutdownEvent shutdown, KubernetesMockServer kubernetesMockServer) {
    kubernetesMockServer.destroy();
  }
}
