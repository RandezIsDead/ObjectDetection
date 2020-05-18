package com.dentheripper.trying.objectdetection.NeuralNet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class NeuralNetwork {

    private DataSet[] dataSet;
    public Layer[] layers;

    public NeuralNetwork() {
        layers = new Layer[4];
        layers[0] = null;
        layers[1] = new Layer(2500, 100);
        layers[2] = new Layer(100, 10);
        layers[3] = new Layer(10, 3);

//        float[] weights1 = loadDataSets("weightsOfFirstLayer", 10000000);
//        float[] weights2 = loadDataSets("weightsOfSecondLayer", 100000);
//        float[] weights3 = loadDataSets("weightsOfThirdLayer", 300);

//        for (int i = 0; i < layers[1].neurons.length; i++) {
//            for (int j = 0; j < layers[1].neurons[i].weights.length; j++) {
//                layers[1].neurons[i].weights[j] = weights1[j];
//            }
//        }
//        for (int i = 0; i < layers[2].neurons.length; i++) {
//            for (int j = 0; j < layers[2].neurons[i].weights.length; j++) {
//                layers[2].neurons[i].weights[j] = weights2[j];
//            }
//        }
//        for (int i = 0; i < layers[3].neurons.length; i++) {
//            for (int j = 0; j < layers[3].neurons[i].weights.length; j++) {
//                layers[3].neurons[i].weights[j] = weights3[j];
//            }
//        }
    }

    float[] loadDataSets(String filename, int value) throws IOException {
        float[] values = new float[value];

        BufferedReader reader = new BufferedReader(new FileReader("resources/" + filename + ".txt"));
        String currentLine = reader.readLine();
        reader.close();
        String[] tabOfFloatString = currentLine.split(" ");

        for(int i = 0; i < tabOfFloatString.length; i++){
            values[i] = Float.parseFloat(tabOfFloatString[i]);
        }
        return values;
    }

    public void CreateDataSet(float[][] imgs, float[][] expected) {
        dataSet = new DataSet[3];
        for (int p = 0; p < 3; p++){
            dataSet[p] = new DataSet(imgs[p], expected[p]);
        }
    }

    public void forward(float[] inputs) {
        layers[0] = new Layer(inputs);
        for(int i = 1; i < layers.length; i++) {
            for(int j = 0; j < layers[i].neurons.length; j++) {
                float sum = 0;
                for(int k = 0; k < layers[i-1].neurons.length; k++) {
                    sum += layers[i-1].neurons[k].value*layers[i].neurons[j].weights[k];
                }
                sum += layers[i].neurons[j].bias;
                layers[i].neurons[j].value = Functions.Sigmoid(sum);
            }
        }
    }

    private void backward(float learning_rate, DataSet tData) {
        int number_layers = layers.length;
        int out_index = number_layers-1;
        for(int i = 0; i < layers[out_index].neurons.length; i++) {
            float output = layers[out_index].neurons[i].value;
            float target = tData.expectedOutput[i];
            float derivative = output-target;
            float delta = derivative*(output*(1-output));
            layers[out_index].neurons[i].gradient = delta;
            for(int j = 0; j < layers[out_index].neurons[i].weights.length;j++) {
                float previous_output = layers[out_index-1].neurons[j].value;
                float error = delta*previous_output;
                layers[out_index].neurons[i].cache_weights[j] = layers[out_index].neurons[i].weights[j] - learning_rate*error;
            }
        }
        for(int i = out_index-1; i > 0; i--) {
            for(int j = 0; j < layers[i].neurons.length; j++) {
                float output = layers[i].neurons[j].value;
                float gradient_sum = gradientSum(j,i+1);
                float delta = (gradient_sum)*(output*(1-output));
                layers[i].neurons[j].gradient = delta;
                for(int k = 0; k < layers[i].neurons[j].weights.length; k++) {
                    float previous_output = layers[i-1].neurons[k].value;
                    float error = delta*previous_output;
                    layers[i].neurons[j].cache_weights[k] = layers[i].neurons[j].weights[k] - learning_rate*error;
                }
            }
        }
        for (Layer layer : layers) {
            for (int j = 0; j < layer.neurons.length; j++) {
                layer.neurons[j].update_weight();
            }
        }
    }

    private float gradientSum(int c_index, int n_index) {
        float gradient_sum = 0;
        Layer current_layer = layers[n_index];
        for (int i = 0; i < current_layer.neurons.length; i++) {
            Neuron current_neuron = current_layer.neurons[i];
            gradient_sum += current_neuron.weights[c_index]*current_neuron.gradient;
        }
        return gradient_sum;
    }

    public void train(int iterations, float learning_rate) {
        for (int i = 0; i < iterations; i++) {
            for (DataSet dataSet : dataSet) {
                forward(dataSet.data);
                backward(learning_rate, dataSet);
            }
        }
    }
}
