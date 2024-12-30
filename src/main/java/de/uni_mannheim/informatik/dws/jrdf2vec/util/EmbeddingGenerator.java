package de.uni_mannheim.informatik.dws.jrdf2vec.util;

import de.uni_mannheim.informatik.dws.jrdf2vec.RDF2Vec;
import de.uni_mannheim.informatik.dws.jrdf2vec.training.Word2VecConfiguration;
import de.uni_mannheim.informatik.dws.jrdf2vec.training.Word2VecType;

import java.io.File;
import java.net.URI;

public class EmbeddingGenerator {

    public static void main(String[] args) {

        // Define the datasets and their corresponding TTL files
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
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/city_15K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/movies_41K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/Album2_61K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/AAUP_95K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/Forbes_119K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/kore_171K.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/LP50_48K.ttl"
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/activated_nodes_20240722_110544_movies_29_1_subgraph.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/album_0728k.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/activated_nodes_20240715_171549_AAUP_29_1_subgraph.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/activated_nodes_KORE_sorted_20241212_002826_29_1_subgraph.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_subgraph/activated_nodes_LP50_20241212_171104_29_1_subgraph.ttl"
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.7_subgraph/activated_nodes_Movies_DBpedia_URI15_20241227_205849_29_1_subgraph.ttl",
            // "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.7_subgraph/activated_nodes_Forbes_DBpedia_URI15_20241227_210058_29_1_subgraph.ttl"
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_Cities_DBpedia_URI15_20241227_173421_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_Movies_DBpedia_URI15_20241227_173616_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_Album_DBpedia_URI15_20241227_173208_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_AAUP_DBpedia_URI15_20241227_173344_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_Forbes_DBpedia_URI15_20241227_173651_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_KORE_sorted_20241227_173458_29_1_subgraph.ttl",
            "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/old_ws/retrive_subgraph/subgraph_withnodes/SA_standard_0.5_subgraph/activated_nodes_LP50_20241227_173541_29_1_subgraph.ttl"  
        };

        // Base directories for walks and embeddings
        String walksBasePath = "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/main/java/de/uni_mannheim/informatik/dws/jrdf2vec/testing/SA_0.5_weight_walk/";
        String embeddingBasePath = "/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/main/java/de/uni_mannheim/informatik/dws/jrdf2vec/testing/SA_0.5_weight_walk_embedding/";

        // Parameters for the embedding generation
        int vectorDim = 100;
        int windowSize = 5;
        int minCount = 1;
        int iterations = 5;
        Word2VecType vecType = Word2VecType.SG;
        int numberOfThreads = 4;

        // Loop over each dataset and create embeddings for direct and complementary
        for (int i = 0; i < datasetNames.length; i++) {
            String dataset = datasetNames[i];
            String ttlFilePath = ttlFiles[i];

            // Direct embeddings
            System.out.println("Generating embeddings for dataset: " + dataset + " (direct)...");
            generateEmbeddingsForDataset(
                    ttlFilePath,
                    new File(walksBasePath + "walk_" + dataset + "_direct/"),
                    new File(embeddingBasePath + "embedding_" + dataset + "_direct/"),
                    vectorDim, windowSize, minCount, iterations, vecType, numberOfThreads
            );

            // Complementary embeddings
            System.out.println("Generating embeddings for dataset: " + dataset + " (complementary)...");
            generateEmbeddingsForDataset(
                    ttlFilePath,
                    new File(walksBasePath + "walk_" + dataset + "_com/"),
                    new File(embeddingBasePath + "embedding_" + dataset + "_com/"),
                    vectorDim, windowSize, minCount, iterations, vecType, numberOfThreads
            );
        }

        System.out.println("All embeddings have been generated.");
    }

    private static void generateEmbeddingsForDataset(String ttlFilePath,
                                                     File existingWalkDirectory,
                                                     File embeddingOutputDirectory,
                                                     int vectorDim,
                                                     int windowSize,
                                                     int minCount,
                                                     int iterations,
                                                     Word2VecType vecType,
                                                     int numberOfThreads) {

        try {
            // Create output directory if not exists
            if (!embeddingOutputDirectory.exists()) {
                boolean created = embeddingOutputDirectory.mkdirs();
                if (!created) {
                    System.err.println("Failed to create directory: " + embeddingOutputDirectory.getAbsolutePath());
                    return;
                }
            }

            File knowledgeGraphFile = new File(ttlFilePath);
            URI knowledgeGraphUri = knowledgeGraphFile.toURI();

            RDF2Vec rdf2Vec = new RDF2Vec(knowledgeGraphUri, embeddingOutputDirectory);

            // Use existing walks; no new walks generated
            rdf2Vec.setExistingWalkDirectory(existingWalkDirectory);
            rdf2Vec.setWalkGenerationMode(null);

            // Use a valid Python server resource if needed
            rdf2Vec.setPythonServerResourceDirectory(new File("/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/main/resources/python_server.py"));

            rdf2Vec.setVectorTextFileGeneration(true);
            rdf2Vec.setEmbedText(false);
            rdf2Vec.setNumberOfThreads(numberOfThreads);
            rdf2Vec.setNumberOfWalksPerEntity(0); // Since we are not generating new walks

            Word2VecConfiguration config = rdf2Vec.getWord2VecConfiguration();
            config.setVectorDimension(vectorDim);
            config.setWindowSize(windowSize);
            config.setMinCount(minCount);
            config.setIterations(iterations);
            config.setType(vecType);

            rdf2Vec.train();

            File vectorFile = new File(embeddingOutputDirectory, "vectors.txt");
            System.out.println("Embeddings have been generated and saved to " + vectorFile.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("An error occurred during embedding generation for " + ttlFilePath + ": " + e.getMessage());
        }
    }
}
