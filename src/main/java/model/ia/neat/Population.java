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
    private float topFitness = 1;

    Random rand = new Random();

    private IAGame game;

    public Population(final IAGame game) {
        super();
        this.neatSingleton = Neat.getInstance();
        this.game = game;
    }

    /**
     * Constructor
     */
    public Population(Chromosome topChromosome) {
        super();
        this.neatSingleton = Neat.getInstance();
        this.chromosomes.add(topChromosome);
        this.game = topChromosome.getGame();
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
        System.out.println("Taille de départ: " + this.chromosomes.size());
        final int surviveCount = allButOne ? 1 : (int) Math.ceil(this.chromosomes.size() * 0.75f);
        final PriorityQueue<Chromosome> survivors = new PriorityQueue<>(new ChromosomeComparator());
        System.out.println("Nombre à garder : " + surviveCount);

        for(int ii = 0; ii < surviveCount; ++ii) {
             survivors.add(this.chromosomes.poll());
         }
        System.out.println("Taille du restant: " + this.chromosomes.size());
        for (Chromosome chromosome : this.chromosomes) {
            this.game.removeBird(chromosome.getBird());
        }
        this.chromosomes = survivors;
        System.out.println("Nombre après removeWeakChromosomes : " + chromosomes.size());

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

    public void removeChromosome(final Chromosome chromosome) {
        this.chromosomes.remove(chromosome);
    }

    public void setTopFitness(final float topFitness) {
        this.topFitness = topFitness;
    }

    /**
     * Compute adjusted fitness for each chromosome in the population, given the size of the population
     */
    public void computeChromosomeAdjustedFitness() {
        for (Chromosome chromosome : this.chromosomes) {
            float adjustedFitness = 0;
            if(this.chromosomes.size() > 0) {
                final float fitness = chromosome.getFitness();
                adjustedFitness = fitness / chromosomes.size();
            }
            chromosome.setAdjustedFitness(adjustedFitness);
        }
    }

    public float getTotalAdjustedFitness() {
        float result = 0;
        for (Chromosome chromosome : this.chromosomes) {
            result += chromosome.getAdjustedFitness();
        }
        return result;
    }

    public Chromosome generateChromosome() {
        Chromosome chromosome = null;
        final List<Chromosome> chromosomeList = new ArrayList<>(this.chromosomes);
        if(chromosomeList.size() > 0) {
            if (rand.nextFloat() < this.neatSingleton.crossoverChance) {
                final Chromosome c1 = chromosomeList.get(rand.nextInt(chromosomeList.size()));
                final Chromosome c2 = chromosomeList.get(rand.nextInt(chromosomeList.size()));
                chromosome = Chromosome.crossOver(c1, c2);
                this.game.removeBird(chromosome.getBird());
            } else {
                final Chromosome c1 = chromosomeList.get(rand.nextInt(chromosomeList.size()));
                chromosome = c1;
                // this.game.removeBird(c1.getBird());
            }
        }

        if(chromosome == null) {
            chromosome = new Chromosome(this.game);
//            this.game.addBird(chrom//
        }else {
            chromosome = new Chromosome(chromosome);

        }
        chromosome.mutate();



        return chromosome;
    }

    public float getTopFitness() {
        return this.peekChromosome().getFitness();
    }

    public PriorityQueue<Chromosome> getChromosomes() {
        return this.chromosomes;
    }
}
