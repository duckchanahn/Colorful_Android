package com.example.colorful_android.TestColor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.R;

import java.io.ByteArrayOutputStream;

public class PersonalTestResultActivity_java extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private byte[] user_image_binary;
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_result_personal);

        Log.e(TAG, "결과 액티비티.java -----------");

        this.filePath = getIntent().getStringExtra("filePath");
        this.user_image_binary = this.pathToBinary(filePath);

        Log.e(TAG, "filePath : " + filePath);
        Log.e(TAG, "byte[] : " + user_image_binary.length);

    }


    private byte[] pathToBinary(String path) {
        Bitmap bitmap= BitmapFactory.decodeFile(path);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

        return stream.toByteArray();
//        imageview.setImageBitmap(bitmap);
    }

}
