package com.marcnuri.demo.fabric8.generic;

import io.fabric8.kubernetes.api.model.NamespaceBuilder;
import io.fabric8.kubernetes.api.model.NamespaceConditionBuilder;
import io.fabric8.kubernetes.api.model.NamespaceSpecBuilder;
import io.fabric8.kubernetes.api.model.NamespaceStatusBuilder;
import io.fabric8.kubernetes.api.model.ObjectMetaBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.dsl.NonDeletingOperation;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;

@QuarkusTest
@TestProfile(MockServerTestProfile.class)
public class KubernetesResourceTest {
  
  @Inject
  KubernetesClient kubernetesClient;
  
  @Test
  void listNamespaces() {
    // Create sample namespace using the standard API model types
    kubernetesClient.namespaces().resource(new NamespaceBuilder()
      .withMetadata(new ObjectMetaBuilder().withName("test-namespace").build())
      .withSpec(new NamespaceSpecBuilder().addToFinalizers("one", "two").build())
      .withStatus(new NamespaceStatusBuilder()
        .withPhase("Active")
        .addToConditions(new NamespaceConditionBuilder().withMessage("Message 1").build())
        .addToConditions(new NamespaceConditionBuilder().withStatus("Status 2").build())
        .build())
      .build()).createOr(NonDeletingOperation::update);
    // Retrieve the namespace using the new GNamespace API model types
    given()
      .when().get("/namespaces")
      .then()
      .statusCode(200)
      .body(
        "[0].metadata.name", is("test-namespace"),
        "[0].spec.finalizers", contains("one", "two"),
        "[0].status.phase", is("Active"),
        "[0].status.conditions[0].message", is("Message 1"),
        "[0].status.conditions[1].status", is("Status 2")
      );
  }
}
