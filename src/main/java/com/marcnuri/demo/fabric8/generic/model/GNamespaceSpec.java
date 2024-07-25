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
  "finalizers"
})
public class GNamespaceSpec extends GKubernetesResource {

  public GNamespaceSpec(JsonNode jsonNode) {
    super(jsonNode);
  }
  
  public GNamespaceSpec() {
    this(JsonNodeFactory.instance.objectNode());
  }
  
// TODO: See note in GNamespace
//  @Buildable(editableEnabled = false, validationEnabled = false, generateBuilderPackage = false, lazyCollectionInitEnabled = false, builderPackage = "io.fabric8.kubernetes.api.builder", refs = {
//    @BuildableReference(ObjectMeta.class),
//  })
  public GNamespaceSpec(List<String> finalizers) {
    this();
    setFinalizers(finalizers);
  }

  @JsonProperty("finalizers")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public List<String> getFinalizers() {
    return StreamSupport.stream(Spliterators.spliteratorUnknownSize(node().get("finalizers").elements(), Spliterator.ORDERED), false)
      .map(JsonNode::asText)
      .collect(Collectors.toList());
  }

  @JsonProperty("finalizers")
  public void setFinalizers(List<String> finalizers) {
    node().replace("finalizers",
      finalizers.stream().collect(JsonNodeFactory.instance::arrayNode, ArrayNode::add, ArrayNode::addAll));
  }
  
}
