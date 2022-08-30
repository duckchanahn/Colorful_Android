package com.example.colorful_android.TestColor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.R;

public class TestMainActivity extends AppCompatActivity {
    public static Activity testMainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_test_select);

        testMainActivity = this;

        Button startPsyTest = findViewById(R.id.psy_color_test_button);
        startPsyTest.setOnClickListener(v -> {
            Intent next_button_intent = new Intent(this, PsycoloficalTestActivity.class);
            startActivity(next_button_intent);
        }) ;

        Button startPersonalTest = findViewById(R.id.personal_color_test_button);
        startPersonalTest.setOnClickListener(v -> {
            Intent next_button_intent = new Intent(this, PersonalTestSelectImageActivity.class);
            startActivity(next_button_intent);
        }) ;
    }
}
