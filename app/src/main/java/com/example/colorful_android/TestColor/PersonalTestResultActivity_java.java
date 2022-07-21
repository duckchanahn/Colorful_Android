package com.example.colorful_android.TestColor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.R;

public class PersonalTestResultActivity_java extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private byte[] user_image_binary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_result_personal);

        Log.e(TAG, "결과 액티비티.java -----------");

//        Intent intent = getIntent();
//        this.user_image_binary = intent.getByteArrayExtra("binary");
//
//        System.out.println(user_image_binary.length);
    }
}
