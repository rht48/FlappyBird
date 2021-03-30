package model.ia.neat.node;

import model.ia.neat.ConnectionGene;

import java.util.ArrayList;

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
