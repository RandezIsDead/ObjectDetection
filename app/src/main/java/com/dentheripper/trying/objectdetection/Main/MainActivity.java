package com.dentheripper.trying.objectdetection.Main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.dentheripper.trying.objectdetection.NeuralNet.DataSet;
import com.dentheripper.trying.objectdetection.NeuralNet.NeuralNetwork;
import com.dentheripper.trying.objectdetection.R;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

    SurfaceView surfaceView;
    SurfaceHolder holder;
    CameraPreview cameraPreview;
    Camera camera;
    TextView classificate;

    NeuralNetwork neuralNetwork;

    final int CAMERA_ID = 0;

    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        surfaceView = findViewById(R.id.surface_view);
        holder = surfaceView.getHolder();
        classificate = findViewById(R.id.classificate);

        cameraPreview = new CameraPreview();
        holder.addCallback(cameraPreview);

        neuralNetwork = new NeuralNetwork();

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            bitmap = getBitmapFromView();
                            surfaceView.post(new Runnable() {
                                @Override
                                public void run() {
                                    Bitmap bmp = bitmap;
                                    float[] inputs = finishImg(bmp);
                                    DataSet dataSet = new DataSet(inputs);
                                    neuralNetwork.forward(dataSet.data);
                                    float[] outputs = new float[3];
                                    for (int i = 0; i < neuralNetwork.layers[neuralNetwork.layers.length - 1].neurons.length; i++) {
                                        outputs[i] = neuralNetwork.layers[neuralNetwork.layers.length - 1].neurons[i].value;
                                    }
                                    classificate.setText(classificate(outputs));
                                }
                            });
                        }
                    });
                }
            }
        };

        thread.start();
    }

    public float[] finishImg(Bitmap image) {
        Bitmap bmp = getResizedBitmap(image);
        Bitmap resized = toGrayscale(bmp);
        return convertToArray(resized);
    }

    private Bitmap getResizedBitmap(Bitmap bm) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) 50) / width;
        float scaleHeight = ((float) 50) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    private static Bitmap toGrayscale(Bitmap srcImage) {
        Bitmap bmpGrayscale = Bitmap.createBitmap(srcImage.getWidth(), srcImage.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpGrayscale);
        Paint paint = new Paint();

        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(srcImage, 0, 0, paint);

        return bmpGrayscale;
    }

    private float[] convertToArray(Bitmap image) {
        int width = image.getWidth();
        int height = image.getHeight();
        float[][] result = new float[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                result[row][col] = image.getPixel(col, row) > 0 ? 1 : 0;
            }
        }

        ArrayList<Float> list = new ArrayList<>();
        for (float[] floats : result) {
            for (float aFloat : floats) {
                list.add(aFloat);
            }
        }

        float[] vector = new float[list.size()];
        for (int i = 0; i < vector.length; i++) {
            vector[i] = list.get(i);
        }
        return vector;
    }

    public Bitmap getBitmapFromView() {
        return Bitmap.createBitmap(surfaceView.getWidth(), surfaceView.getHeight(), Bitmap.Config.ARGB_8888);
    }

    public String classificate(float[] outputs) {
        String object = null;
        if (outputs[0] > outputs[1] && outputs[0] > outputs[2]) {
            object = "Human" + "       " + outputs[0];
        }
        if (outputs[1] > outputs[0] && outputs[1] > outputs[2]) {
            object = "Bottle" + "       " + outputs[1];
        }
        if (outputs[2] > outputs[1] && outputs[2] > outputs[0]) {
            object = "Laptop" + "       " + outputs[2];
        }
        return object;
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
