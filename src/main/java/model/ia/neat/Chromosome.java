package model.ia.neat;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a vector of weights (kind of input), which actually identifies a neuronal network
 */
public class Chromosome {

    private float fitness;

    /**
     * Vector of weights
     */
    private List<Float> weights;

    public Chromosome(final List<Float> w) {
        weights = w;
    }

    public Chromosome(final int size) {
        weights = new ArrayList<>();
        for(int i = 0; i < size; i++) {
            weights.add((float) Math.random());
        }
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(final float fitness) {
        this.fitness = fitness;
    }

    /**
     * Sets the weight
     * @param rank of the weight
     * @param value to change for
     */
    public void setWeight(final int rank, final float value) {
        if(rank < weights.size()) {
            weights.set(rank, value);
        }
    }

    /**
     * Get the weight
     * @param rank of the weight to get
     * @return a weight
     * @throws Exception if the rank is out of range
     */
    public float getWeight(final int rank) throws Exception {
        return weights.get(rank);
    }

    /**
     * Get the vector of weights
     * @return A list of weights
     */
    public List<Float> getWeights() {
        return weights;
    }
}
