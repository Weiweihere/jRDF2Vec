package de.uni_mannheim.informatik.dws.jrdf2vec.util;
import org.junit.jupiter.api.Test;
import de.uni_mannheim.informatik.dws.jrdf2vec.RDF2Vec;
import de.uni_mannheim.informatik.dws.jrdf2vec.training.Word2VecConfiguration;
import de.uni_mannheim.informatik.dws.jrdf2vec.training.Word2VecType;

import java.io.File;
import java.net.URI;

public class EmbeddingGenerator {

    // @Test
    @Test
    public void testMain() {
        String[] args = {}; // Pass any required arguments if necessary
        EmbeddingGenerator.main(args);
    }

    public static void main(String[] args) {
        
        // Specify the knowledge graph file (even if not used for walk generation)
        File knowledgeGraphFile = new File("/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/main/java/de/uni_mannheim/informatik/dws/jrdf2vec/testing/graph.ttl");
        URI knowledgeGraphUri = knowledgeGraphFile.toURI();


        // Directory where your existing walks are located
        File existingWalkDirectory = new File("/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/main/java/de/uni_mannheim/informatik/dws/jrdf2vec/testing/walks/test/");

        // Directory where the embeddings will be saved
        File walkDirectory = new File("/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/main/java/de/uni_mannheim/informatik/dws/jrdf2vec/testing/embedding/");
        if (!walkDirectory.exists()) {
            walkDirectory.mkdirs();
        }

        RDF2Vec rdf2Vec = new RDF2Vec(knowledgeGraphUri, walkDirectory);

        rdf2Vec.setExistingWalkDirectory(existingWalkDirectory);

        rdf2Vec.setVectorTextFileGeneration(true);
        rdf2Vec.setEmbedText(false);       
        rdf2Vec.setNumberOfThreads(4);       

        Word2VecConfiguration config = rdf2Vec.getWord2VecConfiguration();

        // Set parameters
        config.setVectorDimension(100);     // Embedding dimensions
        config.setWindowSize(5);       // Context window size
        config.setMinCount(1);         // Minimum word frequency
        config.setIterations(5);       // Number of iterations (epochs)
        config.setType(Word2VecType.SG); // Skip-Gram

        rdf2Vec.train();

        System.out.println("Embeddings have been generated and saved to " + new File(walkDirectory, "vectors.txt").getAbsolutePath());
    }
}