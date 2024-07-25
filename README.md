# Fabric8 Kubernetes Client model types backed by common storage

This is an example to illustrate what's discussed in [Kubernetes Objects/Model types backed by generic resource with accessors for (de)serialization and API/UX](https://github.com/fabric8io/kubernetes-client/discussions/6199).

With an approach that extends what's presented here, no reflection should be needed to deserialize and serialize Kubernetes objects.

In this case, everything is backed by a JsonNode, although it could be a standard Java Map or anything else.

Considering the new initiative to generate model classes (See [[EPIC] Model Generation using OpenAPI](https://github.com/fabric8io/kubernetes-client/issues/6130)), generating the model types with the required structure should be straightforward.

Issues I see at the moment:
1. Top-level classes (HasMetadata implementations) would likely still be needed to be registered for reflection.
   Which means that the class tree would be registered too.
   - Related to the problem `kubernetesClient.load($inputStream).item() instanceof XxxResource`
   - A more aggressive approach to the KubernetesClient API could solve this by always returning a generic type and let the user cast it through the GKubernetesResource(JsonNode node) constructor of the implementation type.
2. Sundrio **is not** able to generate the builder classes unless they have fields (Should require changes in Sundrio).
3. Performance: Since the values are not cached and there's computation (especially for lists), performance might not be as good.
   In any case, I'm sure this can be polished if we find that this approach is interesting.
