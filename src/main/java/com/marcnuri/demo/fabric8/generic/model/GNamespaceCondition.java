package com.marcnuri.demo.fabric8.generic.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

@JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "lastTransitionTime",
  "message",
  "reason",
  "status",
  "type"
})
public class GNamespaceCondition extends GKubernetesResource {

  public GNamespaceCondition(JsonNode jsonNode) {
    super(jsonNode);
  }

  public GNamespaceCondition() {
    this(JsonNodeFactory.instance.objectNode());
  }

  // TODO: See note in GNamespace
//  @Buildable(editableEnabled = false, validationEnabled = false, generateBuilderPackage = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
//    @BuildableReference(ObjectMeta.class),
//  })
  public GNamespaceCondition(String lastTransitionTime, String message, String reason, String status, String type) {
    this();
    setLastTransitionTime(lastTransitionTime);
    setMessage(message);
    setReason(reason);
    setStatus(status);
    setType(type);
  }

  @JsonProperty("lastTransitionTime")
  public String getLastTransitionTime() {
    return node().path("lastTransitionTime").asText();
  }

  @JsonProperty("lastTransitionTime")
  public void setLastTransitionTime(String lastTransitionTime) {
    node().put("lastTransitionTime", lastTransitionTime);
  }

  @JsonProperty("message")
  public String getMessage() {
    return node().path("message").asText();
  }

  @JsonProperty("message")
  public void setMessage(String message) {
    node().put("message", message);
  }

  @JsonProperty("reason")
  public String getReason() {
    return node().path("reason").asText();
  }

  @JsonProperty("reason")
  public void setReason(String reason) {
    node().put("reason", reason);
  }

  @JsonProperty("status")
  public String getStatus() {
    return node().path("status").asText();
  }

  @JsonProperty("status")
  public void setStatus(String status) {
    node().put("status", status);
  }

  @JsonProperty("type")
  public String getType() {
    return node().path("type").asText();
  }

  @JsonProperty("type")
  public void setType(String type) {
    node().put("type", type);
  }
}
