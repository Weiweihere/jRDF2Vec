package de.uni_mannheim.informatik.dws.jrdf2vec.util;
import de.uni_mannheim.informatik.dws.jrdf2vec.util.EdgeWeightReader;

import de.uni_mannheim.informatik.dws.jrdf2vec.util.Edge;

import java.io.File;
import java.util.Map;

public class EdgeWeightReaderTest {

    public static void main(String[] args) {
        try {
            // Provide the path to your test file
            // File edgeWeightsFile = new File("/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/main/java/de/uni_mannheim/informatik/dws/jrdf2vec/testing/test_edge_weight.txt");
            File edgeWeightsFile = new File("/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/main/java/de/uni_mannheim/informatik/dws/jrdf2vec/testing/graph.ttl");

            // Read edge weights
            Map<Edge, Double> edgeWeights = EdgeWeightReader.readEdgeWeights(edgeWeightsFile);

            // Print the edge weights to verify correctness
            for (Map.Entry<Edge, Double> entry : edgeWeights.entrySet()) {
                System.out.println("Edge: " + entry.getKey() + " | Weight: " + entry.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
