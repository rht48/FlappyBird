package model.ia.neat;

import java.io.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicReference;

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
    private int distance = 0;

    private static final Object lock = new Object();
    private static Neat instance;

    private Neat() {
        this.inputs = 2;
        this.outputs = 1;
        this.hiddenNodes = 5;
        this.population = 10;

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

    public void init(final IAGame game) {
        this.game = game;
        for(int ii = 0; ii < this.population; ii++) {
            final Chromosome chromosome = new Chromosome(this.game);
            this.addToPopulation(chromosome);
        }
    }

    public void addToPopulation(final Chromosome chromosome) {
        for (Population population : this.populationList) {
            if(population.getSize() >= 0) {
                final Chromosome chromosome0 = population.peekChromosome();
                if(chromosome0.isSamePopulation(chromosome)) {
                    population.addChromosome(chromosome);
                    return;
                }
            }
        }
        final Population newPopulation = new Population(this.game);
        newPopulation.addChromosome(chromosome);
        this.populationList.add(newPopulation);
    }

    public void evaluateFitness() {
        for (Population population : this.populationList) {
            for (Chromosome chromosome : population.getChromosomes()) {
                chromosome.setFitness(0.01f * distance + 1000f * chromosome.getBird().getScore().getScore());
            }
        }
    }

    public void computePopulationAdjustedFitness() {
        this.populationList.forEach(p -> p.computeChromosomeAdjustedFitness());
    }

    public float computeGlobalAdjustedFitness() {
        float result = 0;
        for (Population population : this.populationList) {
            result += population.getTotalAdjustedFitness();
        }
        return result;
    }

    public void removeWeakChromosomesFromPopulations(boolean allButOne) {
        this.populationList.forEach(population -> {
            population.removeWeakChromosomes(allButOne);
        });
    }

//    public void removeStalePopulation() {
//        if(this.searchTopFitness() > this.topFitness) {
//            this.neatStaleness = 0;
//        }
//
//        this.populationList.forEach(population -> {
//            final Chromosome topChromosome = population.peekChromosome();
//            // Check if the top chromosome of this population has
//            if(topChromosome.getFitness() > population.getTopFitness()) {
//
//            }
//        });
//    }

//    public ArrayList<Chromosome> generateGeneration() {
//        this.computePopulationAdjustedFitness();
//        this.removeWeakChromosomesFromPopulations(false);
//        // this.removeStalePopulation();
//        final ArrayList<Population> survived = new ArrayList<>();
//
//        final float globalAdjustedFitness = this.computeGlobalAdjustedFitness();
//        final ArrayList<Chromosome> children = new ArrayList<>();
//        float carryOver = 0;
//
//        for (Population population : this.populationList) {
//            float fChild = globalAdjustedFitness != 0 ? this.population * (population.getTotalAdjustedFitness() / globalAdjustedFitness) : 0;
//            int nChild = (int) fChild;
//            carryOver += (fChild - nChild);
//            if(carryOver > 1) {
//                nChild++;
//                carryOver -= 1;
//            }
//
//            if(nChild >= 1) {
//                final Chromosome chromosome = population.peekChromosome();
//                population.removeChromosome(chromosome);
//                survived.add(new Population(chromosome));
//
//                for(int ii = 0; ii < nChild; ii++) {
//                    final Chromosome child = population.generateChromosome();
//                    children.add(child);
//                }
//            }
//        }
//
//        this.populationList = survived;
//        for (Chromosome child : children) {
//            this.addToPopulation(child);
//        }
//        this.nbGeneration++;
//        return children;
//    }

    public void generateGeneration() {
        this.computePopulationAdjustedFitness();
        this.removeWeakChromosomesFromPopulations(false);
        for(Population pop : this.populationList) {
            while(pop.getSize() < this.population) {
                final Chromosome chromosome = pop.generateChromosome();
                pop.addChromosome(chromosome);
            }
        }
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
        this.distance++;
        if(this.game.isFinished()) {
            this.evaluateFitness();
            this.topFitness = this.getTopChromosome().getFitness();
            // Affichage
            System.out.println("TopFitness : ---------------------------------" + this.topFitness);

            this.generateGeneration();
            this.game.reset();
            this.distance = 0;
            this.populationList.forEach(pop -> {
                System.out.println(pop.getChromosomes());
            });
        } else {
            for (Population population : this.populationList) {
                for (Chromosome chromosome : population.getChromosomes()) {
                    chromosome.play();
                }
            }
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

    public float searchTopFitness() {
        AtomicReference<Float> topFitness = new AtomicReference<>((float) 0);
        this.populationList.forEach(population -> {
            final Chromosome topChromosome = population.peekChromosome();
            if (topChromosome != null) {
                float tmpFitness = topChromosome.getFitness();
                if(tmpFitness > topFitness.get()) {
                    topFitness.set(tmpFitness);
                }
            }
        });
        return topFitness.get();
    }

    public float getTopFitness() {
        return this.topFitness;
    }

    public void setTopFitness(final float topFitness) {
        this.topFitness = topFitness;
    }

    public void setGame(final IAGame game) {
        this.game = game;
    }

    @Override
    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
