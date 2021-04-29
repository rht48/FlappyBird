package model.ia;

import graphics.TexturedModel;
import model.game.Game;
import model.game.IAGame;
import model.ia.neat.Chromosome;
import model.ia.neat.Population;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import processing.core.PApplet;
import processing.core.PImage;

import static org.junit.jupiter.api.Assertions.*;

public class TestPopulation {
    private Game game;

    private Population population;

    private Chromosome chromosome1;
    private Chromosome chromosome2;

    @BeforeEach
    void initPopulation() {
        population = new Population();

        chromosome1 = new Chromosome();
        chromosome1.setFitness(0.5f);
        chromosome2 = new Chromosome();
        chromosome2.setFitness(0.8f);

        population.addChromosome(chromosome1);
        population.addChromosome(chromosome2);
    }

    @Test
    void testInitPopulation() {
        assertNotNull(population);
        assertEquals(2, population.getSize());
    }

    @Test
    void testPeekChromosome() {
        assertEquals(2, population.getSize());
        assertEquals(chromosome2, population.peekChromosome());
        assertEquals(2, population.getSize());
    }

    @Test
    void testPollChromosome() {
        assertEquals(2, population.getSize());
        assertEquals(chromosome2, population.pollChromosome());
        assertEquals(1, population.getSize());
        assertEquals(chromosome1, population.pollChromosome());
        assertEquals(0, population.getSize());
    }

    @Test
    void testComputeChromosomeAdjustedFitness() {
        population.computeChromosomeAdjustedFitness();
        assertEquals(0.4f, population.peekChromosome().getAdjustedFitness());

    }

    @Test
    void testGetTotalAdjustedFitness() {
        population.computeChromosomeAdjustedFitness();
        assertEquals(0.65f, population.getTotalAdjustedFitness());
    }

    @Test
    void testRemoveAllButOneChromosomes() {
        assertEquals(2, population.getSize());
        assertEquals(chromosome2, population.peekChromosome());
        population.removeWeakChromosomes(true);
        assertEquals(1, population.getSize());
        assertEquals(chromosome2, population.peekChromosome());
    }
}
