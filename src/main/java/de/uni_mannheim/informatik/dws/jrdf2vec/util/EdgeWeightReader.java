package de.uni_mannheim.informatik.dws.jrdf2vec.util;

import java.io.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import de.uni_mannheim.informatik.dws.jrdf2vec.util.Edge;
import de.uni_mannheim.informatik.dws.jrdf2vec.util.UriUtils;


public class EdgeWeightReader {

    /**
     * Reads edge weights from a file and stores them in a Map.
     * 
     * @param file The file containing the edge weights.
     * @return A map where the key is "<subject_URI>,<predicate_URI>,<object_URI>" and the value is the weight.
     * @throws IOException If there is an issue reading the file.
     */
    
    public static Map<Edge, Double> readEdgeWeights(File file) throws IOException {

        Map<Edge, Double> edgeWeights = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String subject = UriUtils.processUri(parts[0]);
                    String predicate = UriUtils.processUri(parts[1]);
                    String object = UriUtils.processUri(parts[2]);
                    double weight = Double.parseDouble(parts[3]);
                    Edge edge = new Edge(subject, predicate, object);
                    edgeWeights.put(edge, weight);
                }
            }
        }

        return edgeWeights;
    }

    public static Map<String, Double> readEdgeWeightsAsStringKeys(File file) throws IOException {
        Map<String, Double> edgeWeights = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by commas
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.trim().split("\\|");
                if (parts.length != 4) {
                    System.err.println("Invalid line in edge weights file: " + line);
                    continue;
                }
                String subject = parts[0].trim();
                String predicate = parts[1].trim();
                String object = parts[2].trim();
                String weightStr = parts[3].trim();
                double weight;
                try {
                    weight = Double.parseDouble(weightStr);
                } catch (NumberFormatException e) {
                    System.err.println("Invalid weight in edge weights file: " + weightStr);
                    continue;
                }

                // Construct the edge key
                // String edgeKey = parts[0] + "," + parts[1] + "," + parts[2];
                String edgeKey = subject + "," + predicate + "," + object;
                // Parse the weight
                // Double weight = Double.parseDouble(parts[3]);
                // Put the edge key and weight into the map
                edgeWeights.put(edgeKey, weight);
            }
        }
        return edgeWeights;
    }
}
