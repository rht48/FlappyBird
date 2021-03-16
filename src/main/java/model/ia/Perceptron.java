package model.ia;

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
    private final int min_weight = -1;

    /**
     * Maximum weight that can be assigned during training process
     */
    private final int max_weight = 1;



    /**
     * Constructor
     */
    public Perceptron(int nb_weights, double learning_const) {
        this.weights = new double[nb_weights];
        for (double weight : this.weights) {
            weight = random() * (max_weight - min_weight) + min_weight;
        }
        this.learning_const = learning_const;
    }

    /**
     *
     * @param inputs
     * @return
     */
    int guess(double[] inputs) {
        double sum = 0;
        for(int ii = 0; ii < this.weights.length; ++ii) {
            sum += inputs[ii]*this.weights[ii];
        }
        int output = this.activationFunction(sum);
        return output;
    }

    void train(double[] inputs, int target) {
        int guess = this.guess(inputs);
        int error = target - guess;

        for(int ii = 0; ii < this.weights.length; ++ii) {
            this.weights[ii] += error * inputs[ii] * learning_const;
        }
    }

    /**
     *
     * @param n
     * @return
     */
    int activationFunction(double n) {
        if (n >= 0) {
            return 1;
        } else {
            return -1;
        }
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }

    public double getLearning_const() {
        return learning_const;
    }

    public void setLearning_const(double learning_const) {
        this.learning_const = learning_const;
    }

    public int getMin_weight() {
        return min_weight;
    }

    public int getMax_weight() {
        return max_weight;
    }
}
