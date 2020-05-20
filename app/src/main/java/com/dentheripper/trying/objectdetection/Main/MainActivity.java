package com.dentheripper.trying.objectdetection.Main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dentheripper.trying.objectdetection.Data.CreateData;
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
    Thread thread;

    NeuralNetwork neuralNetwork;
    CreateData cd;

    final int CAMERA_ID = 0;
    Bitmap bmp;

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
        cd = new CreateData();

        float[][] weights1 = new float[200][2500];
        float[][] weights2 = new float[200][200];
        float[][] weights3 = new float[3][200];
        try {
            weights1 = cd.castTo2dArray(cd.loadDataSets("weightsOfFirstLayer", 500000), 200, 2500);
            weights2 = cd.castTo2dArray(cd.loadDataSets("weightsOfSecondLayer", 40000), 200, 200);
            weights3 = cd.castTo2dArray(cd.loadDataSets("weightsOfThirdLayer", 600), 3, 200);
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

        thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            surfaceView.post(new Runnable() {
                                @Override
                                public void run() {
                                    bmp = getBitmapFromView();
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
                    });
                }
            }
        };

        thread.start();
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
