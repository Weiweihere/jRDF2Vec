package de.uni_mannheim.informatik.dws.jrdf2vec.testing;

import de.uni_mannheim.informatik.dws.jrdf2vec.walk_generation.walk_generators.NtMemoryWalkGenerator;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

public class NtMemoryWalkGeneratorTest {

    public static void main(String[] args) {
        try {
            // 1. Instantiate the walk generator with the RDF data file
            NtMemoryWalkGenerator walkGenerator = new NtMemoryWalkGenerator("test_graph.nt");
            
            // 2. Load edge weights
            walkGenerator.loadEdgeWeights("edge_weights.csv");
            
            // 3. Generate walks
            String entity = "<http://example.org/A>"; // Starting node
            int numberOfWalks = 1000; // Number of walks to generate
            int depth = 1; // Depth of each walk
            
            List<String> walks = walkGenerator.generateRandomWalksForEntity(entity, numberOfWalks, depth);
            
            // 4. Analyze the walks
            analyzeWalks(walks);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Method to analyze and print the walk statistics
    private static void analyzeWalks(List<String> walks) {
        Map<String, Integer> edgeCounts = new HashMap<>();
        for (String walk : walks) {
            String[] components = walk.split(" ");
            if (components.length >= 3) {
                String predicate = components[1];
                String object = components[2];
                String edgeKey = predicate + " " + object;
                edgeCounts.put(edgeKey, edgeCounts.getOrDefault(edgeKey, 0) + 1);
            }
        }
        
        // Calculate and print probabilities
        int totalWalks = walks.size();
        for (Map.Entry<String, Integer> entry : edgeCounts.entrySet()) {
            String edge = entry.getKey();
            int count = entry.getValue();
            double probability = (double) count / totalWalks * 100;
            System.out.printf("Edge %s selected %.2f%% of the time%n", edge, probability);
        }
    }
}
