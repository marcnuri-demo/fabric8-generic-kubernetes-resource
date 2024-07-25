package com.marcnuri.demo.fabric8.generic.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@JsonDeserialize(using = com.fasterxml.jackson.databind.JsonDeserializer.None.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
  "conditions",
  "phase"
})
public class GNamespaceStatus extends GKubernetesResource {

  public GNamespaceStatus(JsonNode jsonNode) {
    super(jsonNode);
  }

  public GNamespaceStatus() {
    this(JsonNodeFactory.instance.objectNode());
  }

// TODO: See note in GNamespace
//  @Buildable(editableEnabled = false, validationEnabled = false, generateBuilderPackage = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
//    @BuildableReference(ObjectMeta.class),
//  })
  public GNamespaceStatus(List<GNamespaceCondition> conditions, String phase) {
    this();
    setConditions(conditions);
    setPhase(phase);
  }

  @JsonProperty("conditions")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public List<GNamespaceCondition> getConditions() {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(node().get("conditions").elements(), Spliterator.ORDERED), false)
      .map(GNamespaceCondition::new)
      .collect(Collectors.toList());
  }

  @JsonProperty("conditions")
  public void setConditions(List<GNamespaceCondition> conditions) {
    node().replace("conditions", conditions.stream()
        .map(GKubernetesResource::node)
        .collect(JsonNodeFactory.instance::arrayNode, ArrayNode::add, ArrayNode::addAll));
  }

  @JsonProperty("phase")
  public String getPhase() {
    return node().path("phase").asText();
  }

  @JsonProperty("phase")
  public void setPhase(String phase) {
    node().put("phase", phase);
  }
}
