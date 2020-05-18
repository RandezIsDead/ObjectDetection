package com.dentheripper.trying.objectdetection.NeuralNet;

public class Layer {

    public Neuron[] neurons;

    //  Constructor for input Layer
    Layer(float[] input) {
        this.neurons = new Neuron[input.length];
        for (int i = 0; i < input.length; i++) {
            neurons[i] = new Neuron(input[i]);
        }
    }

    //  Constructor for hidden and output Layers
    Layer(int inNeurons, int insideNeurons) {
        this.neurons = new Neuron[insideNeurons];

        for (int i = 0; i < insideNeurons; i++) {
            float[] weights = new float[inNeurons];
            for (int j = 0; j < inNeurons; j++) {
                weights[j] = Functions.RandomFloat(-1, 1);
            }
            neurons[i] = new Neuron(weights, Functions.RandomFloat(0,1));
        }
    }
}
