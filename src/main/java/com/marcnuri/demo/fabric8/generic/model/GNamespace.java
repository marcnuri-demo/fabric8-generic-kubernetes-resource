package com.marcnuri.demo.fabric8.generic.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.fabric8.kubernetes.api.model.HasMetadata;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Plural;
import io.fabric8.kubernetes.model.annotation.Version;
import io.sundr.transform.annotations.TemplateTransformation;
import io.sundr.transform.annotations.TemplateTransformations;

import static com.marcnuri.demo.fabric8.generic.model.CompatibilitySerialization.MAPPER;

@JsonDeserialize(using = JsonDeserializer.None.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "apiVersion",
  "kind",
  "metadata",
  "spec",
  "status"
})
@TemplateTransformations({
  @TemplateTransformation(value = "/manifest.vm", outputPath = "META-INF/services/io.fabric8.kubernetes.api.model.KubernetesResource", gather = true)
})
@Version("v1")
@Group("")
@Plural("namespaces")
public class GNamespace extends GKubernetesResource implements HasMetadata {

  public GNamespace(JsonNode jsonNode) {
    super(jsonNode);
  }
  
  public GNamespace() {
    this(JsonNodeFactory.instance.objectNode());
  }
  
// TODO: Buildables aren't generated correctly unless a real field exists, setters and constructor arguments aren't enough
// TODO: If this approach is considered, then additional changes will be required in Sundrio  
//  @Buildable(editableEnabled = false, validationEnabled = false, generateBuilderPackage = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
//    @BuildableReference(ObjectMeta.class),
//  })
  public GNamespace(String apiVersion, String kind, ObjectMeta metadata, GNamespaceSpec spec, GNamespaceStatus status) {
    this();
    setApiVersion(apiVersion);
    setKind(kind);
    setMetadata(metadata);
    setSpec(spec);
    setStatus(status);
  }

  @JsonProperty("apiVersion")
  public String getApiVersion() {
    return node().get("apiVersion").asText();
  }
  
  @Override
  @JsonProperty("apiVersion")
  public void setApiVersion(String apiVersion) {
    node().put("apiVersion", apiVersion);
  }

  @JsonProperty("kind")
  public String getKind() {
    return node().get("apiVersion").asText();
  }

  @JsonProperty("kind")
  public void setKind(String kind) {
    node().put("kind", kind);
  }

  @Override
  public ObjectMeta getMetadata() {
    // If all API was tackled, could look like:
    // return new GObjectMeta(node().path("metadata"));
    return MAPPER.convertValue(node().path("metadata"), ObjectMeta.class);
  }
  
  @Override
  public void setMetadata(ObjectMeta objectMeta) {
    // If all API was tackled, could look like:
    // node().replace("metadata", objectMeta.node());
    node().replace("metadata", MAPPER.valueToTree(objectMeta));
  }

  @JsonProperty("spec")
  public GNamespaceSpec getSpec() {
    return new GNamespaceSpec(node().path("spec"));
  }

  @JsonProperty("spec")
  public void setSpec(GNamespaceSpec spec) {
    node().replace("spec", spec.node());
  }

  @JsonProperty("status")
  public GNamespaceStatus getStatus() {
    return new GNamespaceStatus(node().path("status"));
  }

  @JsonProperty("status")
  public void setStatus(GNamespaceStatus status) {
    node().replace("status", status.node());
  }
  
}
