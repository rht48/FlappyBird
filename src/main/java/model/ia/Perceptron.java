package model.ia;

import java.util.Arrays;

import static java.lang.Math.random;

public class Perceptron {
    /**
     *
     */
    private double[] weights;

    /**
     *
     */
    private double learning_const;

    /**
     * Minimum weight that can be assigned during training process
     */
    private static final int MIN_WEIGHT = -1;

    /**
     * Maximum weight that can be assigned during training process
     */
    private static final int MAX_WEIGHT = 1;



    /**
     * Constructor
     */
    public Perceptron(final int nb_weights, final double learning_const) {
        this.weights = new double[nb_weights];
        Arrays.stream(this.weights).forEach(weight -> weight = random() * (MAX_WEIGHT - MIN_WEIGHT) + MIN_WEIGHT);
        this.learning_const = learning_const;
    }

    /**
     *
     * @param inputs
     * @return
     */
    int guess(final double[] inputs) {
        double sum = 0;
        for(int ii = 0; ii < this.weights.length; ++ii) {
            sum += inputs[ii] * this.weights[ii];
        }
        final int output = this.activationFunction(sum);
        return output;
    }

    void train(final double[] inputs, final int target) {
        final int guess = this.guess(inputs);
        final int error = target - guess;

        for(int ii = 0; ii < this.weights.length; ++ii) {
            this.weights[ii] += error * inputs[ii] * learning_const;
        }
    }

    /**
     *
     * @param n
     * @return
     */
    int activationFunction(final double n) {
        if (n >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public double[] getWeights() {
        return weights.clone();
    }

    public void setWeights(final double[] weights) {
        this.weights = weights.clone();
    }

    public double getLearning_const() {
        return learning_const;
    }

    public void setLearning_const(final double learning_const) {
        this.learning_const = learning_const;
    }

    public int getMin_weight() {
        return MIN_WEIGHT;
    }

    public int getMax_weight() {
        return MAX_WEIGHT;
    }
}
