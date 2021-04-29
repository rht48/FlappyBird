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

    /**
     * A random seed
     */
    private static Random rand = new Random();

    /**
     * Fitness score (equivalent to game score): the higher the better
     */
    private float fitness = 1;

    /**
     * Fitness score taking into account the number of birds used
     */
    private float adjustedFitness;

    /**
     * Represents all the connections, containing the weights
     */
    private ArrayList<ConnectionGene> connectionGeneList = new ArrayList<>();

    /**
     * Represents the network nodes
     */
    private TreeMap<Integer, NodeGene> nodes = new TreeMap<>();

    /**
     *
     */
    private HashMap<MutationKeys, Float> mutationRates = new HashMap<>();

    /**
     *
     */
    private enum MutationKeys {
        STEPS,
        PERTURB_CHANCE,
        WEIGHT_CHANCE,
        WEIGHT_MUTATION_CHANCE,
        NODE_MUTATION_CHANCE,
        CONNECTION_MUTATION_CHANCE,
        BIAS_CONNECTION_MUTATION_CHANCE,
        DISABLE_MUTATION_CHANCE,
        ENABLE_MUTATION_CHANCE
    }

    public Chromosome(final IAGame game) {
        this.neatSingleton = Neat.getInstance();
        this.fitness = 1;
        this.game = game;
        this.bird = new IABird(this.game.getBirdModel(), new PVector(0, 0));

        this.mutationRates.put(MutationKeys.STEPS, this.neatSingleton.steps);
        this.mutationRates.put(MutationKeys.PERTURB_CHANCE, this.neatSingleton.perturbChance);
        this.mutationRates.put(MutationKeys.WEIGHT_CHANCE, this.neatSingleton.weightChance);
        this.mutationRates.put(MutationKeys.WEIGHT_MUTATION_CHANCE, this.neatSingleton.weightMutationChance);
        this.mutationRates.put(MutationKeys.NODE_MUTATION_CHANCE, this.neatSingleton.nodeMutationChance);
        this.mutationRates.put(MutationKeys.CONNECTION_MUTATION_CHANCE, this.neatSingleton.connectionMutationChance);
        this.mutationRates.put(MutationKeys.BIAS_CONNECTION_MUTATION_CHANCE, this.neatSingleton.biasConnectionMutationChance);
        this.mutationRates.put(MutationKeys.DISABLE_MUTATION_CHANCE, this.neatSingleton.disableMutationChance);
        this.mutationRates.put(MutationKeys.ENABLE_MUTATION_CHANCE, this.neatSingleton.enableMutationChance);

        this.game.addBird(this.bird);
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

        this.fitness = parent.fitness;
        this.adjustedFitness = parent.adjustedFitness;

        this.mutationRates = (HashMap<MutationKeys, Float>) parent.mutationRates.clone();

    }

    /**
     * Cross 2 parent chromosomes to create a new chromosome
     *
     * @param parent1 one of the parents
     * @param parent2 the other parent
     * @return a child Chromosome
     */
    public static Chromosome crossOver(Chromosome parent1, Chromosome parent2) {
        // Parent1 must have a higher fitness than parent2
        if (parent1.fitness < parent2.fitness) {
            final Chromosome temp = parent1;
            parent1 = parent2;
            parent2 = temp;
        }

        final Chromosome child = new Chromosome(parent1.game);
        // Associates for each parent an innovation with a connection
        final TreeMap<Integer, ConnectionGene> geneMap1 = new TreeMap<>();
        final TreeMap<Integer, ConnectionGene> geneMap2 = new TreeMap<>();

        for (ConnectionGene con : parent1.connectionGeneList) {
            geneMap1.put(con.getInnovation(), con);
        }

        for (ConnectionGene con : parent2.connectionGeneList) {
            geneMap2.put(con.getInnovation(), con);
        }

        final Set<Integer> innovationP1 = geneMap1.keySet();
        final Set<Integer> innovationP2 = geneMap2.keySet();

        // Set of all different innovations made among the 2 parents
        final Set<Integer> allInnovations = new HashSet<>(innovationP1);
        allInnovations.addAll(innovationP2);

        // for each innovation
        for (int key : allInnovations) {
            final ConnectionGene trait;

            // If both parents have known this innovation
            if (geneMap1.containsKey(key) && geneMap2.containsKey(key)) {
                // trait becomes randomly the connection from parent1 or parent2
                if (rand.nextBoolean()) {
                    trait = new ConnectionGene(geneMap1.get(key));
                } else {
                    trait = new ConnectionGene(geneMap2.get(key));
                }

                // If one connection is enabled but not the other
                if ((geneMap1.get(key).isEnabled() != geneMap2.get(key).isEnabled())) {
                    // trait's connection has a 25% chance to be enabled
                    if (rand.nextFloat() < 0.75f) {
                        trait.setEnabled(false);
                    } else {
                        trait.setEnabled(true);
                    }
                }
                // else if both parents have the same fitness
            } else if (parent1.getFitness() == parent2.getFitness()) {
                // trait inherits the connection from the parent who has known the innovation
                if (geneMap1.containsKey(key)) {
                    trait = geneMap1.get(key);
                } else {
                    trait = geneMap2.get(key);
                }
                // wtf
                if (rand.nextBoolean()) {
                    continue;
                }
            } else {
                // else trait inherits the connection from parent1
                trait = geneMap1.get(key);
            }


            // finally trait becomes one of child's connections
            child.connectionGeneList.add(trait);
        }

        return child;
    }

    /**
     * Generates all the layers necessary for the network to work
     */
    private void generateNetwork() {
        nodes.clear();
        // Input layer
        for (int i = 0; i < this.neatSingleton.inputs; i++) {
            nodes.put(i, new NodeGene(0));
        }
        nodes.put(this.neatSingleton.inputs, new NodeGene(1));        // Bias

        // Output layer
        for (int i = this.neatSingleton.inputs + this.neatSingleton.hiddenNodes; i < this.neatSingleton.inputs + this.neatSingleton.hiddenNodes + this.neatSingleton.outputs; i++) {
            nodes.put(i, new NodeGene(0));
        }

        // Hidden layer
        for (ConnectionGene con : connectionGeneList) {
            if (!nodes.containsKey(con.getIn())) {
                nodes.put(con.getIn(), new NodeGene(0));
            }

            if (!nodes.containsKey(con.getOut())) {
                nodes.put(con.getOut(), new NodeGene(0));
            }

            nodes.get(con.getOut()).getIncomingCon().add(con);
        }
    }

    /**
     *
     * @param inputs
     * @return
     */
    public float[] evaluateNetwork(final float[] inputs) {
        final float output[] = new float[this.neatSingleton.outputs];
        this.generateNetwork();

        for (int i = 0; i < this.neatSingleton.inputs; i++) {
            nodes.get(i).setValue(inputs[i]);
        }

        for (Map.Entry<Integer, NodeGene> mapEntry : nodes.entrySet()) {
            float sum = 0;
            final int key = mapEntry.getKey();
            final NodeGene node = mapEntry.getValue();

            if (key > this.neatSingleton.inputs) {
                for (ConnectionGene connectionGene : node.getIncomingCon()) {
                    if (connectionGene.isEnabled()) {
                        sum += nodes.get(connectionGene.getIn()).getValue() * connectionGene.getWeight();
                    }
                }
                node.setValue(sigmoid(sum));
            }
        }

        for (int i = 0; i < this.neatSingleton.outputs; i++) {
            output[i] = nodes.get(this.neatSingleton.inputs + this.neatSingleton.hiddenNodes + i).getValue();
        }
        return output;
    }
    
    /**
     *
     */
    public void mutate() {
        // Mutate mutation rates
        for (Map.Entry<MutationKeys, Float> entry : mutationRates.entrySet()) {
            if (rand.nextBoolean()) {
                mutationRates.put(entry.getKey(), 0.95f * entry.getValue());
            } else {
                mutationRates.put(entry.getKey(), 1.05263f * entry.getValue());
            }
        }


        if (rand.nextFloat() <= mutationRates.get(MutationKeys.WEIGHT_MUTATION_CHANCE)) {
            mutateWeight();
        }
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.CONNECTION_MUTATION_CHANCE)) {
            mutateAddConnection(false);
        }
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.BIAS_CONNECTION_MUTATION_CHANCE)) {
            mutateAddConnection(true);
        }
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.NODE_MUTATION_CHANCE)) {
            mutateAddNode();
        }
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.DISABLE_MUTATION_CHANCE)) {
            disableMutate();
        }
        if (rand.nextFloat() <= mutationRates.get(MutationKeys.ENABLE_MUTATION_CHANCE)) {
            enableMutate();
        }
    }

    /**
     *
     */
    void mutateWeight() {

        for (ConnectionGene c : connectionGeneList) {
            if (rand.nextFloat() < this.neatSingleton.weightChance) {
                if (rand.nextFloat() < this.neatSingleton.perturbChance) {
                    c.setWeight(c.getWeight() + (2 * rand.nextFloat() - 1) * this.neatSingleton.steps);
                } else {
                    c.setWeight(4 * rand.nextFloat() - 2);
                }
            }
        }
    }

    /**
     * @param forceBais
     */
    void mutateAddConnection(final boolean forceBais) {
        generateNetwork();
        int i = 0;
        int j = 0;
        final int random2 = rand.nextInt(nodes.size() - this.neatSingleton.inputs - 1) + this.neatSingleton.inputs + 1;
        int random1 = rand.nextInt(nodes.size());
        if (forceBais) {
            random1 = this.neatSingleton.inputs;
        }
        int node1 = -1;
        int node2 = -1;

        for (int k : nodes.keySet()) {
            if (random1 == i) {
                node1 = k;
                break;
            }
            i++;
        }

        for (int k : nodes.keySet()) {
            if (random2 == j) {
                node2 = k;
                break;
            }
            j++;
        }
//	System.out.println("random1 = "+random1 +" random2 = "+random2);
//	System.out.println("Node1 = "+node1 +" node 2 = "+node2);


        if (node1 >= node2) {
            return;
        }


        for (ConnectionGene con : nodes.get(node2).getIncomingCon()) {
            if (con.getIn() == node1) {
                return;
            }
        }

        //if (node1 < 0 || node2 < 0)
        //throw new RuntimeErrorException(null);          // TODO Pool.newInnovation(node1, node2)
        connectionGeneList.add(new ConnectionGene(node1, node2, 4 * rand.nextFloat() - 2, true, InnovationCounter.newInnovation()));                // Add innovation and weight

    }

    /**
     *
     */
    void mutateAddNode() {
        generateNetwork();
        if (connectionGeneList.size() > 0) {
            int timeoutCount = 0;
            ConnectionGene randomCon = connectionGeneList.get(rand.nextInt(connectionGeneList.size()));
            while (!randomCon.isEnabled()) {
                randomCon = connectionGeneList.get(rand.nextInt(connectionGeneList.size()));
                timeoutCount++;
                if (timeoutCount > this.neatSingleton.hiddenNodes) {
                    return;
                }

            }
            final int nextNode = nodes.size() - this.neatSingleton.outputs;
            randomCon.setEnabled(false);
            connectionGeneList.add(new ConnectionGene(randomCon.getIn(), nextNode, 1, true, InnovationCounter.newInnovation()));        // Add innovation and weight
            connectionGeneList.add(new ConnectionGene(nextNode, randomCon.getOut(), randomCon.getWeight(), true, InnovationCounter.newInnovation()));
        }
    }

    /**
     *
     */
    void disableMutate() {
        //generateNetwork();                // remove laters
        if (connectionGeneList.size() > 0) {
            final ConnectionGene randomCon = connectionGeneList.get(rand.nextInt(connectionGeneList.size()));
            randomCon.setEnabled(false);
        }
    }

    /**
     *
     */
    void enableMutate() {
        //generateNetwork();                // remove laters
        if (connectionGeneList.size() > 0) {
            final ConnectionGene randomCon = connectionGeneList.get(rand.nextInt(connectionGeneList.size()));
            randomCon.setEnabled(true);
        }
    }

    public float getFitness() {
        return fitness;
    }

    public void setFitness(final float fitness) {
        this.fitness = fitness;
    }

    public ArrayList<ConnectionGene> getConnectionGeneList() {
        return connectionGeneList;
    }

    public void setConnectionGeneList(final ArrayList<ConnectionGene> connectionGeneList) {
        this.connectionGeneList = connectionGeneList;
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

        if( res > 0.5) {
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

    public boolean isSamePopulation(Chromosome other)  {
        TreeMap<Integer, ConnectionGene> geneMap1 = new TreeMap<>();
        TreeMap<Integer, ConnectionGene> geneMap2 = new TreeMap<>();

        int matching = 0;
        int disjoint = 0;
        int excess = 0;
        float weight = 0;
        int lowMaxInnovation;
        float delta = 0;

        for(ConnectionGene con: this.connectionGeneList) {
            assert  !geneMap1.containsKey(con.getInnovation());             //TODO Remove for better performance
            geneMap1.put(con.getInnovation(), con);
        }

        for(ConnectionGene con: other.connectionGeneList) {
            assert  !geneMap2.containsKey(con.getInnovation());             //TODO Remove for better performance
            geneMap2.put(con.getInnovation(), con);
        }
        if(geneMap1.isEmpty() || geneMap2.isEmpty())
            lowMaxInnovation = 0;
        else
            lowMaxInnovation = Math.min(geneMap1.lastKey(),geneMap2.lastKey());

        Set<Integer> innovationP1 = geneMap1.keySet();
        Set<Integer> innovationP2 = geneMap2.keySet();

        Set<Integer> allInnovations = new HashSet<Integer>(innovationP1);
        allInnovations.addAll(innovationP2);

        for(int key : allInnovations){

            if(geneMap1.containsKey(key) && geneMap2.containsKey(key)){
                matching ++;
                weight += Math.abs(geneMap1.get(key).getWeight() - geneMap2.get(key).getWeight());
            }else {
                if(key < lowMaxInnovation){
                    disjoint++;
                }else{
                    excess++;
                }
            }
        }

        //System.out.println("matching : "+matching + "\ndisjoint : "+ disjoint + "\nExcess : "+ excess +"\nWeight : "+ weight);

        int N = matching+disjoint+excess ;

        if(N>0)
            delta = (this.neatSingleton.excessCoefficent * excess + this.neatSingleton.disjointCoefficent * disjoint) / N + (this.neatSingleton.weightCoefficent * weight) / matching;

        return delta < this.neatSingleton.compatibilityThreshold;
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
