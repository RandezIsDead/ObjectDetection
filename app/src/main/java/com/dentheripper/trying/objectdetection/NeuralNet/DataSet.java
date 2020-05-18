package com.dentheripper.trying.objectdetection.NeuralNet;

public class DataSet {

    public float[] data;
    float[] expectedOutput;

    public DataSet(float[] data) {
        this.data = data;
    }

    DataSet(float[] data, float[] expectedOutput) {
        this.data = data;
        this.expectedOutput = expectedOutput;
    }
}
