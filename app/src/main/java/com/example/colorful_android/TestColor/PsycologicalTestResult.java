package com.example.colorful_android.TestColor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.MainActivity;
import com.example.colorful_android.R;

public class PsycologicalTestResult extends AppCompatActivity {

    private TextView subTitle;
    private TextView title;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_test_result);
//        init();

        this.subTitle = findViewById(R.id.subTitle);
        this.title = findViewById(R.id.title);
        this.content = findViewById(R.id.content);

        String mbti = getIntent().getStringExtra("result");
        Log.e("result activity", mbti);

        Button button = findViewById(R.id.personal_color_test_button);
        button.setOnClickListener( v -> {
            startActivity(new Intent(this, PersonalTestSelectImageActivity.class));
        });

        Button button_home = findViewById(R.id.homeButton);
        button_home.setOnClickListener( v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }

}
