package model.ia.neat;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class Neat {
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

    private static final Object lock = new Object();

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

    private static Neat instance;

    public static Neat getInstance() {
        synchronized (lock) {
            if (instance == null) {
                instance = new Neat();
            }
            return instance;
        }
    }

    public static void load(final String file) {
        load(new File(file));
    }

    public static void load(final File file) {
        synchronized (lock) {
            instance = fromFile(file);

            // no config file found
            if (instance == null) {
                instance = new Neat();
            }
        }
    }

    private static Neat fromFile(final File configFile) {
        try {
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            return gson.fromJson(reader, Neat.class);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
