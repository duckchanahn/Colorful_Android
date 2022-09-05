package com.example.colorful_android.Setting;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.R;

public class PolicyActivity  extends AppCompatActivity {

    private ImageView prevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_guideline);

        this.prevButton = findViewById(R.id.btn_back);
        this.prevButton.setOnClickListener( v -> {
            finish();
        });
    }
}