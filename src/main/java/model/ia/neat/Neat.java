package model.ia.neat;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.game.IAGame;
import model.ia.IAPlayer;

public final class Neat implements IAPlayer {
    public int inputs;
    public int outputs;
    public int hiddenNodes;
    public int population;

    public float compatibilityThreshold;
    public float excessCoefficent;
    public float disjointCoefficent;
    public float weightCoefficent;

    public float staleSpecies;


    public float steps;
    public float perturbChance;
    public float weightChance;
    public float weightMutationChance;
    public float nodeMutationChance;
    public float connectionMutationChance;
    public float biasConnectionMutationChance;
    public float disableMutationChance;
    public float enableMutationChance;
    public float crossoverChance;

    public int stalePool;

    private IAGame game;
    private ArrayList<Population> populationList = new ArrayList<>();
    private int nbGeneration = 0;
    private float topFitness = 0;
    private int neatStaleness;

    private static final Object lock = new Object();
    private static Neat instance;

    private Neat() {
        this.inputs = 2;
        this.outputs = 1;
        this.hiddenNodes = 1000000;
        this.population = 300;

        this.compatibilityThreshold = 1;
        this.excessCoefficent = 2;
        this.disjointCoefficent = 2;
        this.weightCoefficent = 0.4f;

        this.staleSpecies = 15;


        this.steps = 0.1f;
        this.perturbChance = 0.9f;
        this.weightChance = 0.3f;
        this.weightMutationChance = 0.9f;
        this.nodeMutationChance = 0.03f;
        this.connectionMutationChance = 0.05f;
        this.biasConnectionMutationChance = 0.15f;
        this.disableMutationChance = 0.1f;
        this.enableMutationChance = 0.2f;
        this.crossoverChance = 0.75f;

        this.stalePool = 20;
    }

    public void generatePopulation(final IAGame game) {
        this.game = game;
        this.addPopulation(new Population(game));
    }

    public void addPopulation(final Population population) {
        this.populationList.add(population);
    }

    public void evaluateFitness() {
        this.populationList.forEach(population -> population.getChromosomes().forEach(chromosome -> {
            float fitness = chromosome.getBird().getScore().getScore();
            chromosome.setFitness(fitness);
        }));
    }

    public void computePopulationAdjustedFitness() {
        this.populationList.forEach(p -> p.computeChromosomeAdjustedFitness());
    }

    public void breedNewGeneration() {
        this.computePopulationAdjustedFitness();

        // TODO faut que ca baise un peu !!!
    }

    public Chromosome getTopChromosome(){
        PriorityQueue<Chromosome> allChromosome = new PriorityQueue<>(new ChromosomeComparator());

        this.populationList.forEach(population -> population.getChromosomes().forEach(chromosome -> {
            allChromosome.add(chromosome);
        }));

        return allChromosome.peek();
    }

    @Override
    public void update() {
        if(this.game.isFinished()) {
            this.evaluateFitness();
            this.topFitness = this.getTopChromosome().getFitness();
            System.out.println("TopFitness : " + this.topFitness);

            this.breedNewGeneration();

            this.nbGeneration++;
            this.game.reset();
        } {
            this.populationList.forEach(population -> population.getChromosomes().forEach(chromosome -> {
                chromosome.play();
            }));
        }
    }

    public static Neat getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new Neat();
            }
            return instance;
        }
    }

    public static void setInstance(Neat neat) {
        synchronized (lock) {
            instance = neat;
        }
    }

    public static void loadFromPath(final String file) {
        loadFromFile(new File(file));
    }

    public static void loadFromFile(final File file) {
        synchronized (lock) {
            final Neat neat = readFile(file);
            Neat.setInstance(neat);

            // no config file found
            if (Neat.getInstance() == null) {
                Neat.setInstance(new Neat());
            }
        }
    }

    public static Neat readFile(final File configFile) {
        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            final Neat neat = gson.fromJson(reader, Neat.class);
            return neat;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public ArrayList<Population> getPopulationList() {
        return populationList;
    }

    public int getNbGeneration() {
        return nbGeneration;
    }

    public void setNbGeneration(final int nbGeneration) {
        this.nbGeneration = nbGeneration;
    }

    public float getTopFitness() {
        return topFitness;
    }

    public void setTopFitness(float topFitness) {
        this.topFitness = topFitness;
    }

    @Override
    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
