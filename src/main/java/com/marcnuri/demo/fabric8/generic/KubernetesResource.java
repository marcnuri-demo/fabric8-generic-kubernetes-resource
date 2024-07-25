package com.marcnuri.demo.fabric8.generic;

import com.marcnuri.demo.fabric8.generic.model.GNamespace;
import io.fabric8.kubernetes.client.KubernetesClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/")
public class KubernetesResource {
  
  private final KubernetesClient kubernetesClient;
  
  @Inject
  public KubernetesResource(KubernetesClient kubernetesClient) {
    this.kubernetesClient = kubernetesClient;
  }
  
  @GET
  @Path("/namespaces")
  public List<GNamespace> getNamespaces() {
    final List<GNamespace> items = kubernetesClient.resources(GNamespace.class).list().getItems();
    return items;
  }
  
}
