package com.marcnuri.demo.fabric8.generic.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

class GKubernetesResource {
  
  private final ObjectNode node;

  GKubernetesResource(JsonNode node) {
    if (node.isObject()) {
      this.node = (ObjectNode) node;
    } else {
      this.node = JsonNodeFactory.instance.objectNode();
    }
  }
  
  public final ObjectNode node() {
    return node;
  }

}
