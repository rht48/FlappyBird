package model.ia.neat.node;

import model.ia.neat.ConnectionGene;

import java.util.ArrayList;

/**
 * Each element position within a chromosome is a gene, which has a specific value
 * and determines the genotype and the phenotype of the solution.
 */
public class NodeGene {
    private float value;

    private ArrayList<ConnectionGene> incomingConnections = new ArrayList<>();

    public NodeGene(final float value) {
        super();
        this.value = value;
    }

    public float getValue() {
        return this.value;
    }

    public void setValue(final float value) {
        this.value = value;
    }

    public ArrayList<ConnectionGene> getIncomingCon() {
        return this.incomingConnections;
    }

    public void setIncomingCon(final ArrayList<ConnectionGene> incomingConnections) {
        this.incomingConnections = incomingConnections;
    }

}
