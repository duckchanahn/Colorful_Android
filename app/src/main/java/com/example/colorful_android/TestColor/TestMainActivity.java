package com.example.colorful_android.TestColor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.R;

public class TestMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_layout);

        Button startPsyTest = findViewById(R.id.psy_color_test_button);
        startPsyTest.setOnClickListener(v -> {
            Intent next_button_intent = new Intent(this, PsyTestActivity_java.class);
            startActivity(next_button_intent);
        }) ;

        Button startPersonalTest = findViewById(R.id.personal_color_test_button);
        startPersonalTest.setOnClickListener(v -> {
            Intent next_button_intent = new Intent(this, PersonalTestSelectImageActivity_java.class);
            startActivity(next_button_intent);
        }) ;
    }
}
