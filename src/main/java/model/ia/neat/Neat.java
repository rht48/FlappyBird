package model.ia.neat;

import java.io.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Neat {
    public int inputs = 2;
    public int outputs = 1;
    public int hiddenNodes = 1000000;
    public int population = 300;

    public float compatibilityThreshold = 1;
    public float excessCoefficent = 2;
    public float disjointCoefficent = 2;
    public float weightCoefficent = 0.4f;

    public float staleSpecies = 15;


    public float steps = 0.1f;
    public float perturbChance = 0.9f;
    public float weightChance = 0.3f;
    public float weightMutationChance = 0.9f;
    public float nodeMutationChance = 0.03f;
    public float connectionMutationChance = 0.05f;
    public float biasConnectionMutationChance = 0.15f;
    public float disableMutationChance = 0.1f;
    public float enableMutationChance = 0.2f ;
    public float crossoverChance = 0.75f;

    public int stalePool = 20;

    private static Neat instance;

    public static Neat getInstance() {
        if (instance == null) {
            instance = new Neat();
        }
        return instance;
    }

    public static void load(String file) {
        load(new File(file));
    }

    public static void load(File file) {
        instance = fromFile(file);

        // no config file found
        if (instance == null) {
            instance = new Neat();
        }
    }

    private static Neat fromFile(File configFile) {
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(configFile)));
            return gson.fromJson(reader, Neat.class);
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
