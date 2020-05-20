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

public class CreateData {

//    private final int IMG_SIZE = 50;
//
//    private float[][] input1 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input2 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input3 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input4 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input5 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input6 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input7 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input8 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input9 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input10 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input11 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input12 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input13 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input14 = new float[3][IMG_SIZE * IMG_SIZE];
//    private float[][] input15 = new float[3][IMG_SIZE * IMG_SIZE];
//
//    private float[][] expectedOutput = new float[3][3];
//
//    public void createData(NeuralNetwork neuralNetwork) {
//        Bitmap img001 = BitmapFactory.decodeFile("assets\\DataSet\\human\\01.jpg");
//        Bitmap img002 = BitmapFactory.decodeFile("assets\\DataSet\\human\\02.jpg");
//        Bitmap img003 = BitmapFactory.decodeFile("assets\\DataSet\\human\\03.jpg");
//        Bitmap img004 = BitmapFactory.decodeFile("assets\\DataSet\\human\\04.jpg");
//        Bitmap img005 = BitmapFactory.decodeFile("assets\\DataSet\\human\\05.jpg");
//        Bitmap img006 = BitmapFactory.decodeFile("assets\\DataSet\\human\\06.jpg");
//        Bitmap img007 = BitmapFactory.decodeFile("assets\\DataSet\\human\\07.jpg");
//        Bitmap img008 = BitmapFactory.decodeFile("assets\\DataSet\\human\\08.jpg");
//        Bitmap img009 = BitmapFactory.decodeFile("assets\\DataSet\\human\\09.jpg");
//        Bitmap img0010 = BitmapFactory.decodeFile("assets\\DataSet\\human\\10.jpg");
//        Bitmap img0011 = BitmapFactory.decodeFile("assets\\DataSet\\human\\11.jpg");
//        Bitmap img0012 = BitmapFactory.decodeFile("assets\\DataSet\\human\\12.jpg");
//        Bitmap img0013 = BitmapFactory.decodeFile("assets\\DataSet\\human\\13.jpg");
//        Bitmap img0014 = BitmapFactory.decodeFile("assets\\DataSet\\human\\14.jpg");
//        Bitmap img0015 = BitmapFactory.decodeFile("assets\\DataSet\\human\\15.jpg");
//
//        Bitmap img011 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\01.jpg");
//        Bitmap img012 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\02.jpg");
//        Bitmap img013 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\03.jpg");
//        Bitmap img014 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\04.jpg");
//        Bitmap img015 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\05.jpg");
//        Bitmap img016 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\06.jpg");
//        Bitmap img017 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\07.jpg");
//        Bitmap img018 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\08.jpg");
//        Bitmap img019 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\09.jpg");
//        Bitmap img0110 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\10.jpg");
//        Bitmap img0111 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\11.jpg");
//        Bitmap img0112 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\12.jpg");
//        Bitmap img0113 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\13.jpg");
//        Bitmap img0114 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\14.jpg");
//        Bitmap img0115 = BitmapFactory.decodeFile("assets\\DataSet\\bottle\\15.jpg");
//
//        Bitmap img021 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\01.jpg");
//        Bitmap img022 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\02.jpg");
//        Bitmap img023 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\03.jpg");
//        Bitmap img024 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\04.jpg");
//        Bitmap img025 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\05.jpg");
//        Bitmap img026 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\06.jpg");
//        Bitmap img027 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\07.jpg");
//        Bitmap img028 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\08.jpg");
//        Bitmap img029 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\09.jpg");
//        Bitmap img0210 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\10.jpg");
//        Bitmap img0211 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\11.jpg");
//        Bitmap img0212 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\12.jpg");
//        Bitmap img0213 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\13.jpg");
//        Bitmap img0214 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\14.jpg");
//        Bitmap img0215 = BitmapFactory.decodeFile("assets\\DataSet\\laptop\\15.jpg");
//
//        input1[0] = finishImg(img001);
//        input1[1] = finishImg(img011);
//        input1[2] = finishImg(img021);
//
//        input2[0] = finishImg(img002);
//        input2[1] = finishImg(img012);
//        input2[2] = finishImg(img022);
//
//        input3[0] = finishImg(img003);
//        input3[1] = finishImg(img013);
//        input3[2] = finishImg(img023);
//
//        input4[0] = finishImg(img004);
//        input4[1] = finishImg(img014);
//        input4[2] = finishImg(img024);
//
//        input5[0] = finishImg(img005);
//        input5[1] = finishImg(img015);
//        input5[2] = finishImg(img025);
//
//        input6[0] = finishImg(img006);
//        input6[1] = finishImg(img016);
//        input6[2] = finishImg(img026);
//
//        input7[0] = finishImg(img007);
//        input7[1] = finishImg(img017);
//        input7[2] = finishImg(img027);
//
//        input8[0] = finishImg(img008);
//        input8[1] = finishImg(img018);
//        input8[2] = finishImg(img028);
//
//        input9[0] = finishImg(img009);
//        input9[1] = finishImg(img019);
//        input9[2] = finishImg(img029);
//
//        input10[0] = finishImg(img0010);
//        input10[1] = finishImg(img0110);
//        input10[2] = finishImg(img0210);
//
//        input11[0] = finishImg(img0011);
//        input11[1] = finishImg(img0111);
//        input11[2] = finishImg(img0211);
//
//        input12[0] = finishImg(img0012);
//        input12[1] = finishImg(img0112);
//        input12[2] = finishImg(img0212);
//
//        input13[0] = finishImg(img0013);
//        input13[1] = finishImg(img0113);
//        input13[2] = finishImg(img0213);
//
//        input14[0] = finishImg(img0014);
//        input14[1] = finishImg(img0114);
//        input14[2] = finishImg(img0214);
//
//        input15[0] = finishImg(img0015);
//        input15[1] = finishImg(img0115);
//        input15[2] = finishImg(img0215);
//
//        expectedOutput[0] = new float[] {1, 0, 0};
//        expectedOutput[1] = new float[] {0, 1, 0};
//        expectedOutput[2] = new float[] {0, 0, 1};
//
//        neuralNetwork.CreateDataSet(input1, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("===========");
//        neuralNetwork.CreateDataSet(input2, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("  ===========");
//        neuralNetwork.CreateDataSet(input3, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("===========");
//        neuralNetwork.CreateDataSet(input4, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("  ===========");
//        neuralNetwork.CreateDataSet(input5, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("===========");
//        neuralNetwork.CreateDataSet(input6, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("  ===========");
//        neuralNetwork.CreateDataSet(input7, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("===========");
//        neuralNetwork.CreateDataSet(input8, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("  ===========");
//        neuralNetwork.CreateDataSet(input9, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("===========");
//        neuralNetwork.CreateDataSet(input10, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("  ===========");
//        neuralNetwork.CreateDataSet(input11, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("  ===========");
//        neuralNetwork.CreateDataSet(input12, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("===========");
//        neuralNetwork.CreateDataSet(input13, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("  ===========");
//        neuralNetwork.CreateDataSet(input14, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("===========");
//        neuralNetwork.CreateDataSet(input15, expectedOutput);
//        neuralNetwork.train(5000, 0.5f);
//        System.out.println("  ===========");
//    }

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
            object = "Bottle";
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
