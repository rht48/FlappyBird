package model.ia;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPerceptron {
    private Perceptron perceptron;

    private int nb_weights = 4;
    private double learning_const = 0.1;

    @Test
    void testInitPerceptron() {
        assertNull(perceptron);
        perceptron = new Perceptron(this.nb_weights, this.learning_const);
        assertNotNull(perceptron);
        assertEquals(perceptron.getLearning_const(), this.learning_const);
        assertEquals(perceptron.getWeights().length, this.nb_weights);
        assertTrue(perceptron.getMin_weight() <= perceptron.getWeights()[0]);
        assertTrue(perceptron.getWeights()[0] <= perceptron.getMax_weight());
    }
}