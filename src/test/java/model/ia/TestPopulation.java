package model.ia;

import model.ia.neat.Chromosome;
import model.ia.neat.Population;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPopulation {
    private Population population;

    private Chromosome chromosome1;
    private Chromosome chromosome2;

    @Test
    void testInitPopulation() {
        assertNull(population);
        population = new Population();
        assertNotNull(population);
        chromosome1 = new Chromosome(3);
        chromosome1.setFitness(0.5f);
        chromosome2 = new Chromosome(3);
        chromosome2.setFitness(0.8f);

        population.addChromosome(chromosome1);
        population.addChromosome(chromosome2);

        assertEquals(this.population.peekChromosome(), chromosome2);
    }
}
