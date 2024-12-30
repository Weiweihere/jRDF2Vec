package de.uni_mannheim.informatik.dws.jrdf2vec.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import de.uni_mannheim.informatik.dws.jrdf2vec.util.EdgeWeightReader;
import org.apache.jena.rdf.model.ModelFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.util.Map;
import java.util.Set;
import org.apache.jena.ontology.OntModel;
import de.uni_mannheim.informatik.dws.jrdf2vec.walk_generation.base.WalkGenerationManager;
import de.uni_mannheim.informatik.dws.jrdf2vec.walk_generation.base.WalkGenerationMode;

public class TestWalkGenerationWithWeights {

    @Test
    public void testWalkGenerationForAllDatasets() {

        // Define the datasets and their corresponding TTL files
        // The dataset name can be used to construct output directories.
        String[] datasetNames = new String[] {
            "city", 
            "movies", 
            "albums", 
            "aaup", 
            "forbes", 
            "kore", 
            "lp"
        };

        String[] ttlFiles = new String[] {
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_Cities_DBpedia_URI15_20241227_173421_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_Movies_DBpedia_URI15_20241227_173616_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_Album_DBpedia_URI15_20241227_173208_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_AAUP_DBpedia_URI15_20241227_173344_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_Forbes_DBpedia_URI15_20241227_173651_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_KORE_sorted_20241227_173458_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_LP50_20241227_173541_29_1_subgraph.ttl"  
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.7_subgraph/activated_nodes_Movies_DBpedia_URI15_20241227_205849_29_1_subgraph.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.7_subgraph/activated_nodes_Forbes_DBpedia_URI15_20241227_210058_29_1_subgraph.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/activated_nodes_20240722_110544_movies_29_1_subgraph.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/activated_nodes_20240715_171549_AAUP_29_1_subgraph.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/activated_nodes_KORE_sorted_20241212_002826_29_1_subgraph.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/activated_nodes_LP50_20241212_171104_29_1_subgraph.ttl"
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/album_0728k.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/city_15K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/movies_41K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/Album2_61K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/AAUP_95K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/Forbes_119K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/kore_171K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/LP50_48K.ttl"
        };

        // Weight file patterns for direct and complementary
        // We assume naming follows a consistent pattern: weighted_triples_output_<dataset>_direct.txt and
        // weighted_triples_output_<dataset>_com.txt
        // String weightBasePath = "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/generate_edge_list/";
        String weightBasePath = "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/generate_edge_list/weight_0.5/";
        
        // Adjust as needed for your output base directory
        String outputBasePath = "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/main/java/de/uni_mannheim/informatik/dws/jrdf2vec/testing/SA_0.5_weight_walk/";
        
        // Walk generation parameters
        int numberOfThreads = 40; // Adjust based on system
        int numberOfWalks = 100;  // total walks per entity
        int depth = 4;            // length of each walk

        // The namespace used in your data (adjust as needed)
        String namespace = "http://dbpedia.org";

        // Loop over each dataset and generate walks for both direct and complementary weights
        for (int i = 0; i < datasetNames.length; i++) {
            String dataset = datasetNames[i];
            String ttlFile = ttlFiles[i];

            // Construct the weight files
            String directWeightsFile = weightBasePath + "weighted_triples_output_" + dataset + "_direct.txt";
            String comWeightsFile = weightBasePath + "weighted_triples_output_" + dataset + "_com.txt";

            // Output directories for direct and complementary
            File directOutputDir = new File(outputBasePath + "walk_" + dataset + "_direct/");
            File comOutputDir = new File(outputBasePath + "walk_" + dataset + "_com/");

            // Generate walks with direct weights
            System.out.println("Processing dataset: " + dataset + " with direct weights.");
            generateWeightedWalks(ttlFile, directWeightsFile, directOutputDir, namespace, numberOfThreads, numberOfWalks, depth);

            // Generate walks with complementary weights
            System.out.println("Processing dataset: " + dataset + " with complementary weights.");
            generateWeightedWalks(ttlFile, comWeightsFile, comOutputDir, namespace, numberOfThreads, numberOfWalks, depth);
        }
    }

    private void generateWeightedWalks(String ttlFilePath, 
                                       String weightsFilePath, 
                                       File outputDir,
                                       String namespace,
                                       int threads, 
                                       int numberOfWalks, 
                                       int depth) {
        try {
            System.out.println("Loading RDF graph from: " + ttlFilePath);
            OntModel ontModel = ModelFactory.createOntologyModel();
            ontModel.read(new FileInputStream(ttlFilePath), null, "TTL");
            System.out.println("Graph loaded. Number of statements: " + ontModel.size());

            System.out.println("Loading edge weights from: " + weightsFilePath);
            File weightsFile = new File(weightsFilePath);
            Map<String, Double> edgeWeights = EdgeWeightReader.readEdgeWeightsAsStringKeys(weightsFile);
            System.out.println("Edge weights loaded. Number of edges: " + edgeWeights.size());

            // Ensure output directory exists
            if (!outputDir.exists()) {
                boolean dirCreated = outputDir.mkdirs();
                if (!dirCreated) {
                    throw new IOException("Failed to create output directory: " + outputDir.getAbsolutePath());
                }
            }

            // Create and configure the manager
            WalkGenerationManager manager = new WalkGenerationManager(ontModel, edgeWeights, namespace);
            assertTrue(manager != null, "WalkGenerationManager instantiation failed.");

            Set<String> entities = manager.entitySelector.getEntities();
            System.out.println("Number of entities: " + entities.size());

            System.out.println("Generating walks...");
            manager.generateWalks(WalkGenerationMode.RANDOM_WALKS, threads, numberOfWalks, depth, outputDir);
            System.out.println("Walk generation completed.");

            manager.close();

            // Check if at least one walk file was generated
            File walkFile = new File(outputDir, "walk_file_0.txt.gz");
            System.out.println("Checking if the walk file exists at: " + walkFile.getAbsolutePath());
            assertTrue(walkFile.exists(), "Walk file was not generated.");

        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred during walk generation: " + e.getMessage());
        }
    }
}
