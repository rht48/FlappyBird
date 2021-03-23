package model.ia.neat;

/**
 * ConnectionGene represents the axon of the neuron.
 */
public class ConnectionGene {
    private int in;
    private int out;
    private float weight;
    private boolean enabled;
    private int innovation;

    public ConnectionGene(final int in, final int out, final float weight, final boolean enabled, final int innovation) {
        this.in = in;
        this.out = out;
        this.weight = weight;
        this.enabled = enabled;
        this.innovation = innovation;
    }

    public ConnectionGene(final ConnectionGene connectionGene) {
        this.in = connectionGene.in;
        this.out = connectionGene.out;
        this.weight = connectionGene.weight;
        this.enabled = connectionGene.enabled;
        this.innovation = connectionGene.innovation;
    }

    @Override
    public String toString() {
        return "ConnectionGene{" +
                "in=" + in +
                ", out=" + out +
                ", weight=" + weight +
                ", enabled=" + enabled +
                ", innovation=" + innovation +
                '}';
    }

    public int getIn() {
        return in;
    }

    public int getOut() {
        return out;
    }

    public float getWeight() {
        return weight;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getInnovation() {
        return innovation;
    }

    public void setWeight(final float weight) {
        this.weight = weight;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
