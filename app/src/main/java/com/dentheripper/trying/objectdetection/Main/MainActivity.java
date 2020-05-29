package com.dentheripper.trying.objectdetection.Main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dentheripper.trying.objectdetection.Data.ImageConverter;
import com.dentheripper.trying.objectdetection.NeuralNet.DataSet;
import com.dentheripper.trying.objectdetection.NeuralNet.NeuralNetwork;
import com.dentheripper.trying.objectdetection.R;

import java.io.IOException;

public class MainActivity extends Activity {

    SurfaceView surfaceView;
    SurfaceHolder holder;
    CameraPreview cameraPreview;
    Camera camera;
    TextView classificate;
    TextView p1;
    TextView p2;
    TextView p3;

    NeuralNetwork neuralNetwork;
    ImageConverter cd;

    final int CAMERA_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surface_view);
        holder = surfaceView.getHolder();
        classificate = findViewById(R.id.classificate);
        p1 = findViewById(R.id.p1);
        p2 = findViewById(R.id.p2);
        p3 = findViewById(R.id.p3);

        cameraPreview = new CameraPreview();
        holder.addCallback(cameraPreview);

        neuralNetwork = new NeuralNetwork();
        cd = new ImageConverter();

        float[][] weights1 = new float[1000][4900];
        float[][] weights2 = new float[100][1000];
        float[][] weights3 = new float[3][100];
        try {
            weights1 = cd.castTo2dArray(cd.loadDataSets("weightsOfFirstLayer", 4900000), 1000, 4900);
            weights2 = cd.castTo2dArray(cd.loadDataSets("weightsOfSecondLayer", 100000), 100, 1000);
            weights3 = cd.castTo2dArray(cd.loadDataSets("weightsOfThirdLayer", 300), 3, 100);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < neuralNetwork.layers[1].neurons.length; i++) {
            for (int j = 0; j < neuralNetwork.layers[1].neurons[i].weights.length; j++) {
                neuralNetwork.layers[1].neurons[i].weights[j] = weights1[i][j];
            }
        }
        for (int i = 0; i < neuralNetwork.layers[2].neurons.length; i++) {
            for (int j = 0; j < neuralNetwork.layers[2].neurons[i].weights.length; j++) {
                neuralNetwork.layers[2].neurons[i].weights[j] = weights2[i][j];
            }
        }
        for (int i = 0; i < neuralNetwork.layers[3].neurons.length; i++) {
            for (int j = 0; j < neuralNetwork.layers[3].neurons[i].weights.length; j++) {
                neuralNetwork.layers[3].neurons[i].weights[j] = weights3[i][j];
            }
        }
    }

    public Bitmap getBitmapFromView() {
        return Bitmap.createBitmap(surfaceView.getWidth(), surfaceView.getHeight(), Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open(CAMERA_ID);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null)
            camera.release();
        camera = null;
    }

    public void onCapture(View view) {
        surfaceView.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = getBitmapFromView();
                float[] inputs = cd.finishImg(bmp);
                DataSet dataSet = new DataSet(inputs);
                neuralNetwork.forward(dataSet.data);
                float[] outputs = new float[3];
                for (int i = 0; i < neuralNetwork.layers[neuralNetwork.layers.length - 1].neurons.length; i++) {
                    outputs[i] = neuralNetwork.layers[neuralNetwork.layers.length - 1].neurons[i].value;
                    System.out.println(outputs[i]);
                }
                p1.setText(String.valueOf(outputs[0]*100));
                p2.setText(String.valueOf(outputs[1]*100));
                p3.setText(String.valueOf(outputs[2]*100));
                System.out.println("==================");
                classificate.setText(cd.classificate(outputs));
            }
        });
    }

    class CameraPreview implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            camera.stopPreview();
            camera.setDisplayOrientation(90);
            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {}
    }
}
