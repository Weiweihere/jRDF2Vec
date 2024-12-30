package de.uni_mannheim.informatik.dws.jrdf2vec.walk_generation.walk_generators;

/**
 * Interface for walk generators that support edge weights.
 */
public interface IEdgeWeightedWalkGenerator extends IWalkGenerator {

    /**
     * Loads edge weights from a specified file path.
     *
     * @param weightsFilePath The path to the edge weights file.
     */
    void loadEdgeWeights(String weightsFilePath);
}
