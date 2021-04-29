package model.ia.neat;

import model.game.IAGame;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * The population is the subset of all the candidate solutions to a given problem.
 */
public class Population {
    /**
     * Singleton of the Neat
     */
    private Neat neatSingleton;

    /**
     * Ordered set with all the candidate solutions, compared with fitness score
     */
    private PriorityQueue<Chromosome> chromosomes = new PriorityQueue<>(new ChromosomeComparator());

    /**
     * At the beginning the top score is the minimal possible score
     */
    private float topFitness = 0;

    Random rand = new Random();

    public Population() {
        super();
        this.neatSingleton = Neat.getInstance();
    }

    /**
     * Constructor
     */
    public Population(final IAGame game) {
        super();
        this.neatSingleton = Neat.getInstance();

        for(int ii = 0; ii < this.neatSingleton.population; ++ii) {
            this.addChromosome(new Chromosome(game));
        }
    }

    /**
     * Add a new candidate solution in the population, aka the priorityQueue
     * @param chromosome
     */
    public void addChromosome(final Chromosome chromosome) {
        if(chromosome != null) {
            this.chromosomes.add(chromosome);
        }
    }

    public void removeWeakChromosomes(final boolean allButOne) {
        final int surviveCount = allButOne ? 1 : (int) Math.ceil(this.chromosomes.size() / 2f);
        final PriorityQueue<Chromosome> survivors = new PriorityQueue<>(new ChromosomeComparator());
         for(int ii = 0; ii < surviveCount; ++ii) {
             survivors.add(this.chromosomes.poll());
         }
        this.chromosomes = survivors;
    }

    public int getSize() {
        return this.chromosomes.size();
    }

    public Chromosome peekChromosome() {
        return this.chromosomes.peek();
    }

    public Chromosome pollChromosome() {
        return this.chromosomes.poll();
    }

    public void setTopFitness(final float topFitness) {
        this.topFitness = topFitness;
    }

    /**
     * Compute adjusted fitness for each chromosome in the population, given the size of the population
     */
    public void computeChromosomeAdjustedFitness() {
        this.chromosomes.stream().forEach(chromosome -> chromosome.setAdjustedFitness(chromosome.getFitness() / chromosomes.size()));
    }

    public float getTotalAdjustedFitness() {
        return this.chromosomes.stream().map(chromosome -> chromosome.getAdjustedFitness()).reduce(0f, (a, b) -> a + b);
    }

    public Chromosome generateChromosome() {
        Chromosome chromosome;
        final List<Chromosome> chromosomeList = new ArrayList<>(this.chromosomes);
        final Chromosome c1 = chromosomeList.get(rand.nextInt(chromosomeList.size()));
        if (rand.nextFloat() < this.neatSingleton.crossoverChance) {
            final Chromosome c2 = chromosomeList.get(rand.nextInt(chromosomeList.size()));
            chromosome = Chromosome.crossOver(c1, c2);
        } else {
            chromosome = c1;
        }

        chromosome = new Chromosome(chromosome);
        chromosome.mutate();

        return chromosome;
    }

    public PriorityQueue<Chromosome> getChromosomes() {
        return this.chromosomes;
    }
}
