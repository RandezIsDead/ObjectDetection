package com.dentheripper.trying.objectdetection.Data;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ImageConverter {

    public float[] finishImg(Bitmap image) {
        Bitmap bmp = getResizedBitmap(image);
        Bitmap resized = toGrayscale(bmp);
        return convertToArray(resized);
    }

    public Bitmap getResizedBitmap(Bitmap bm) {
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

    public Bitmap toGrayscale(Bitmap srcImage) {
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

    private float[] castTo1dArray(float[][] result) {
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

    public float[][] castTo2dArray( final float[] array, final int rows, final int cols ) {
        if (array.length != (rows*cols))
            throw new IllegalArgumentException("Invalid array length");

        float[][] bidi = new float[rows][cols];
        for ( int i = 0; i < rows; i++ )
            System.arraycopy(array, (i*cols), bidi[i], 0, cols);

        return bidi;
    }

    public String classificate(float[] outputs) {
        String object = null;
        if (outputs[0] > outputs[1] && outputs[0] > outputs[2]) {
            object = "Human";
        }
        if (outputs[1] > outputs[0] && outputs[1] > outputs[2]) {
            object = "Car";
        }
        if (outputs[2] > outputs[1] && outputs[2] > outputs[0]) {
            object = "Laptop";
        }
        return object;
    }

    public float[] loadDataSets(String filename, int value) throws IOException {
        float[] values = new float[value];

        BufferedReader reader = new BufferedReader(new FileReader("assets/resources/" + filename + ".txt"));
        String currentLine = reader.readLine();
        reader.close();
        String[] tabOfFloatString = currentLine.split(" ");

        for(int i = 0; i < tabOfFloatString.length; i++){
            values[i] = Float.parseFloat(tabOfFloatString[i]);
        }
        return values;
    }
}
