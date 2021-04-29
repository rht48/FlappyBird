package model.ia.neat;

import model.entity.IABird;
import model.game.Game;
import model.game.IAGame;
import model.ia.IAPlayer;
import model.ia.neat.node.NodeGene;
import processing.core.PVector;

import java.util.*;

/**
 * Each candidate solution in the population is a chromosome, sometimes referred to as a genome.
 */
public class Chromosome {

    /**
     * Singleton of the Neat
     */
    private Neat neatSingleton;

    /**
     *
     */
    private IAGame game;

    /**
     *
     */
    private final IABird bird;

    private float fitness;

    /**
     * Fitness score taking into account the number of birds used
     */
    private float adjustedFitness;

    /**
     * Vector of weights
     */
    private List<Float> weights;

    public Chromosome(final List<Float> w) {
        weights = w;
    }

    /**
     * Copy Constructor
     *
     * @param parent chromosome needed to be copied
     */
    public Chromosome(final Chromosome parent) {
        this.neatSingleton = Neat.getInstance();

        this.game = parent.game;
        this.bird = new IABird(game.getBirdModel(), new PVector(0, 0));

        for (ConnectionGene c : parent.connectionGeneList) {
            this.connectionGeneList.add(new ConnectionGene(c));
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

    public float getAdjustedFitness() {
        return adjustedFitness;
    }

    public void setAdjustedFitness(final float adjustedFitness) {
        this.adjustedFitness = adjustedFitness;
    }

    public void play() {
        // Inputs
        float distanceToPipe = (int) (this.game.getDistanceToPipe() - (this.bird.getPosition().x + this.bird.getWidth()));
        float heightPipe = (int) (this.game.getHeightToPipe() - (this.bird.getPosition().y + this.bird.getHeight()));

        float[] inputs = { heightPipe, distanceToPipe };

        final float res = this.evaluateNetwork(inputs)[0];
        if( res < 0.5) {
            System.out.println(res);
            this.bird.jump();
        }
    }

    /**
     *
     * @param x
     * @return
     */
    private float sigmoid(final float x) {
        // TODO Auto-generated method stub
        return (float) (1 / (1 + Math.exp(-4.9 * x)));
    }

    public TreeMap<Integer, NodeGene> getNodes() {
        return nodes;
    }

    public IAGame getGame() {
        return this.game;
    }

    public IABird getBird() {
        return this.bird;
    }
}

class InnovationCounter {

    private static int innovation = 0;

    public static int newInnovation() {
        innovation++;
        return innovation;
    }
}
