# Enhanced RDF2Vec - Feature Updates

This repository contains an enhanced version of RDF2Vec developed for our research project. The improvements focus on walk generation and embedding generation with weighted edges.

## Repository Overview
- **Branch:** `feature-updates`  
- **Original Repository:** Forked from [dwslab/jRDF2Vec](https://github.com/dwslab/jRDF2Vec)  
- **Enhanced Features:**  
  - Weighted walk generation  
  - Improved performance on large knowledge graphs  
  - Added embedding generation using weighted walk files
- ** run
module load devel/python/3.8.6_intel_19.1
mvn -Dtest=TestWalkGenerationWithWeights test
## New Additions and Modifications
### 1.  Walk Generation with Weights
- **File:**  
  [`TestWalkGenerationWithWeights.java`](src/test/java/de/uni_mannheim/informatik/dws/jrdf2vec/util/TestWalkGenerationWithWeights.java)  
- **Description:**  
  This file tests the generation of walk files with weighted edges to improve embedding quality.  
  **File Path:**  
  `/pfs/work7/workspace/scratch/ma_wezhu-ws_spreading2/jRDF2Vec/src/test/java/de/uni_mannheim/informatik/dws/jrdf2vec/util/TestWalkGenerationWithWeights.java`

### 2. Embedding Generation Command
- **Command to Generate Embeddings from Walk Files:**  
   mvn exec:java -Dexec.mainClass="de.uni_mannheim.informatik.dws.jrdf2vec.util.EmbeddingGenerator"

For perparing the file, please check : https://github.com/Weiweihere/Spreading-activation_rdf2vec, to generate the weight file and subgraph for your interesting node.
