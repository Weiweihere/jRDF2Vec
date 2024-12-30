package de.uni_mannheim.informatik.dws.jrdf2vec.walk_generation.entity_selector;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Resource;

import java.util.HashSet;
import java.util.Set;

/**
 * An entity selector that filters entities based on a specified namespace.
 */
public class FilteredEntitySelector implements EntitySelector {

    private final Set<String> entities;

    /**
     * Constructs a FilteredEntitySelector that selects entities from the given OntModel
     * whose URIs start with the specified namespace.
     *
     * @param ontModel  The ontology model containing the entities.
     * @param namespace The namespace to filter entities by.
     */
    public FilteredEntitySelector(OntModel ontModel, String namespace) {
        entities = new HashSet<>();
        ontModel.listSubjects().forEachRemaining(resource -> {
            String uri = getResourceUri(resource);
            if (uri != null && uri.startsWith(namespace)) {
                entities.add(uri);
            }
        });
    }

    /**
     * Retrieves the set of entities selected by this selector.
     *
     * @return A set of entity URIs.
     */
    @Override
    public Set<String> getEntities() {
        return entities;
    }

    /**
     * Helper method to get the URI of a resource, handling blank nodes.
     *
     * @param resource The resource from which to get the URI.
     * @return The URI as a string, or null if the resource is a blank node.
     */
    private String getResourceUri(Resource resource) {
        if (resource.isURIResource()) {
            return resource.getURI();
        } else {
            // Handle blank nodes or other non-URI resources if necessary
            return null;
        }
    }
}
