package model.ia.neat;

import java.util.PriorityQueue;

/**
 * The population is the subset of all the candidate solutions to a given problem.
 */
public class Population {
    private PriorityQueue<Chromosome> chromosomes = new PriorityQueue<>(new ChromosomeComparator());
    private float topFitness = 0;

    public Population() {
        super();
    }

    public void addChromosome(final Chromosome chromosome) {
        if(chromosome != null) {
            this.chromosomes.add(chromosome);
        }
    }

    public Chromosome peekChromosome() {
        return this.chromosomes.peek();
    }

    public void setTopFitness(final float topFitness) {
        this.topFitness = topFitness;
    }
}
