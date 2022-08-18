package com.example.colorful_android.TestColor;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.R;

public class PsycologicalTestResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String mbti = getIntent().getStringExtra("result");
        setContentView(R.layout.activity_main); // 결과창 입력
    }

}
