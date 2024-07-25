package com.marcnuri.demo.fabric8.generic;


import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import io.quarkus.runtime.ShutdownEvent;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Singleton
public class AppConfig {
  
  void onEnd(@Observes ShutdownEvent shutdown, KubernetesClient client) {
    client.close();
  }
  
  @Singleton
  @Produces
  KubernetesClient kubernetesClient() {
    return new KubernetesClientBuilder().build();
  }
}
