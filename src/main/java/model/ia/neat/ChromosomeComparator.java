package model.ia.neat;

import java.io.Serializable;
import java.util.Comparator;

public class ChromosomeComparator implements Comparator<Chromosome>, Serializable {
    @Override
    public int compare(final Chromosome c1, final Chromosome c2) {
        return c1.getFitness() < c2.getFitness() ? 1 : -1;
    }
}
