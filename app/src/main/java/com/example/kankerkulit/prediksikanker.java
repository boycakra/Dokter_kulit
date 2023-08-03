package com.example.kankerkulit;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.kankerkulit.R;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class prediksikanker extends AppCompatActivity {
    private static final int GALLERY_REQ_CODE = 1000;
    private ImageView imgGallery;

    private Interpreter tflite;
//    private float[][] output;
    CardView predict_card;

    ImageView kulitpengguna;
//    Button predict;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.prediksikanker);

        kulitpengguna = findViewById(R.id.imgGallery);
        Button btnGallery = findViewById(R.id.btnGallery);
        predict_card = findViewById(R.id.predictKanker);

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setType("image/*");
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });

        // Load the TensorFlow Lite model
        try {
            tflite = new Interpreter(loadModelFile(this.getAssets(), "model.tflite"));
            showToast("Model loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            showToast("Failed to load model.");
        }

        predict_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap inputImage = getBitmapFromImageView(kulitpengguna);
                if (inputImage != null) {
//                    showToast("MASUKKK");
                    predictImage(inputImage);
                } else {
                    showToast("Please select an image first.");
                }
            }
        });

//        try {
//            tflite = new Interpreter(loadModelFile());
//            showToast("Model loaded successfully!");
//            setupOutputArray();
//        } catch (IOException e) {
//            e.printStackTrace();
//            showToast("Failed to load model.");
//        }
    }
//    [(1, 256, 256, 3)]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                //gallery
                kulitpengguna.setImageURI(data.getData());
            }
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (resultCode == RESULT_OK) {
//            if (requestCode == GALLERY_REQ_CODE) {
//                try {
//                    Bitmap selectedImage = getBitmapFromUri(data.getData());
//                    imgGallery.setImageBitmap(selectedImage);
//                    predictImage(selectedImage);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    showToast("Failed to load image.");
//                }
//            }
//        }
//    }

    private MappedByteBuffer loadModelFile(AssetManager assetManager, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = assetManager.openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

//    private void setupOutputArray() {
//        Tensor outputTensor = tflite.getOutputTensor(0);
//        DataType outputDataType = outputTensor.dataType();
//        int[] outputShape = outputTensor.shape();
//
//        output = new float[outputShape[0]][outputShape[1]];
//    }

    public void predictImage(Bitmap inputImage) {
        // Preprocess the input image and pass it to the model
        ByteBuffer inputBuffer = convertBitmapToByteBuffer(inputImage);

        String classificationOutputString;

        int batchSize = 1;
        int width = 256;
        int height = 256;
        int channels = 3;

        float[][][][] reshapedArray = new float[batchSize][width][height][channels];

        int index = 0;
        for (int b = 0; b < batchSize; b++) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    for (int c = 0; c < channels; c++) {
                        reshapedArray[b][i][j][c] = inputBuffer.getFloat(index);
                        index += 4; // 4 bytes per float
                    }
                }
            }
        }

        float[][][][] segmentationOutput = new float[1][256][256][1];
        float[][] classificationOutput = new float[1][3];

        Object[] inputArray = {reshapedArray};
        Map<Integer, Object> outputMap = new HashMap<>();
        outputMap.put(0, classificationOutput);
        outputMap.put(1, segmentationOutput);

        tflite.runForMultipleInputsOutputs(inputArray, outputMap);
//        segmentationOutput[0] <- (256, 256, 1)
        classificationOutputString = getPredictedClass(classificationOutput);

//        int height_2 = segmentationOutput[0][0].length;
//        int width_2 = segmentationOutput[0][1].length;
//
//        Bitmap bitmap = Bitmap.createBitmap(width_2, height_2, Bitmap.Config.ARGB_8888);
//
//        for (int y = 0; y < height_2; y++) {
//            for (int x = 0; x < width_2; x++) {
//                float [] rgb = segmentationOutput[0][y][x];
//
//                float red = rgb[0];
//                float green = rgb[1];
//                float blue = rgb[2];
//
//                int color = Color.rgb(red, green, blue);
//
//                bitmap.setPixel(x, y, color);
//            }
//        }
//
//        ImageView kulitpengguna = findViewById(R.id.imgGallery); // Assuming you have an ImageView in your layout with the id "imageView"
//        kulitpengguna.setImageBitmap(bitmap);

        Log.d("Segmentation Output", Arrays.deepToString(segmentationOutput));
        Log.d("Classification Output", Arrays.deepToString(classificationOutput));

//        Log.d("Classification Output", classificationOutputString);
        showToast(classificationOutputString);

        // Process the outputs and display the results
        // ...
//        return classificationOutputString;
    }

    private Bitmap getBitmapFromUri(android.net.Uri uri) throws IOException {
        FileInputStream inputStream = new FileInputStream(uri.getPath());
        return BitmapFactory.decodeStream(inputStream);
    }

    private Bitmap getBitmapFromImageView(ImageView imageView) {
        return ((android.graphics.drawable.BitmapDrawable) imageView.getDrawable()).getBitmap();
    }

    private ByteBuffer convertBitmapToByteBuffer(Bitmap bitmap) {
        int inputSize = 256; // Adjust the input size based on your model
        int channelSize = 3; // RGB channels

        // Resize the bitmap to the desired input size
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputSize, inputSize, true);

//        int[] shape = resizedBitmap.getShape();
//        for (int i = 0; i < shape.length; i++) {
//            Log.d("resizedBitmap shape", String.format("%d", shape[i]));
//        }

        int bitmapWidth = resizedBitmap.getWidth();
        int bitmapHeight = resizedBitmap.getHeight();
        int[] intValues = new int[bitmapWidth * bitmapHeight];

        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1 * inputSize * inputSize * channelSize * 4);
        byteBuffer.order(ByteOrder.nativeOrder());

        resizedBitmap.getPixels(intValues, 0, bitmapWidth, 0, 0, bitmapWidth, bitmapHeight);

        int pixel = 0;
        for (int i = 0; i < inputSize; ++i) {
            for (int j = 0; j < inputSize; ++j) {
                int value = intValues[pixel++];

                byteBuffer.putFloat(((value >> 16) & 0xFF) / 255.0f);
                byteBuffer.putFloat(((value >> 8) & 0xFF) / 255.0f);
                byteBuffer.putFloat((value & 0xFF) / 255.0f);
            }
        }

        byteBuffer.rewind(); // Rewind the buffer to the beginning

        return byteBuffer;
    }

    private String getPredictedClass(float[][] prediction) {
        int predictedClass = -1; // Initialize to an invalid value
        String predictedName = "";
        float maxConfidence = prediction[0][0];

        for (int j = 0; j < 3; j++) {
            if (prediction[0][j] > maxConfidence) {
                predictedClass = j;
                maxConfidence = prediction[0][j];
            }
        }

        Log.d("Classification Output", String.valueOf(predictedClass));

        if (predictedClass == -1) {
            // Handle the case where no class has a higher confidence
            return "Unknown";
        } else {
            // Assign the predictedName based on the predictedClass value
            if (predictedClass == 0) {
                predictedName = "Melanocytic Nevi";
            } else if (predictedClass == 1) {
                predictedName = "Benign Keratosis Lesions";
            } else {
                predictedName = "Melanoma";
            }
            return predictedName;
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
