package de.uni_mannheim.informatik.dws.jrdf2vec.util;
import org.apache.jena.ontology.OntModel;


import java.util.function.UnaryOperator;

/**
 * Utility class for URI processing.
 */
public class UriUtils {

    private static UnaryOperator<String> uriShortenerFunction = s -> s; // Default to identity function

    /**
     * Sets the URI shortener function.
     *
     * @param shortenerFunction The function to apply to URIs.
     */
    public static void setUriShortenerFunction(UnaryOperator<String> shortenerFunction) {
        uriShortenerFunction = shortenerFunction;
    }

    /**
     * Processes a URI by trimming, removing angle brackets, and applying a URI shortener function.
     *
     * @param uri The URI to process.
     * @return The processed URI.
     */
    public static String processUri(String uri) {
        if (uri == null) {
            return null;
        }
        uri = uri.trim();
        if (uri.startsWith("<") && uri.endsWith(">")) {
            uri = uri.substring(1, uri.length() - 1);
        }
        // Apply URI shortening if needed
        uri = uriShortenerFunction.apply(uri);
        return uri;
    }
}
