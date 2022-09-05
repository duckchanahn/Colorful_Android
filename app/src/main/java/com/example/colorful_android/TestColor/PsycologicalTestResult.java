package com.example.colorful_android.TestColor;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.R;

public class PsycologicalTestResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String mbti = getIntent().getStringExtra("result");
        Log.e("result activity", mbti);
        setContentView(R.layout.activity_main); // 결과창 입력
    }

}
