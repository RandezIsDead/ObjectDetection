package com.dentheripper.trying.objectdetection.NeuralNet;

public class Neuron {

    public float value;
    float gradient;
    public float[] weights;
    float bias;
    float[] cache_weights;

    Neuron(float[] weights, float bias){
        this.weights = weights;
        this.bias = bias;
        this.cache_weights = this.weights;
        this.gradient = 0;
    }

    Neuron(float value){
        this.weights = null;
        this.bias = -1;
        this.gradient = -1;
        this.value = value;
    }

    void update_weight() {
        this.weights = this.cache_weights;
    }
}
