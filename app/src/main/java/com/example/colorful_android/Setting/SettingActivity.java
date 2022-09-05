package com.example.colorful_android.Setting;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.R;

public class SettingActivity extends AppCompatActivity {

    private ImageView prevButton;

    private Button withdrawal;
    private Button logout;
    private Button policy;
    private Button license;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_setting);

        this.prevButton = findViewById(R.id.btn_back);
        this.prevButton.setOnClickListener( v -> {
            finish();
        });

        this.withdrawal = findViewById(R.id.withdrawal);
        this.logout = findViewById(R.id.logout);
        this.policy = findViewById(R.id.policy);
        this.license = findViewById(R.id.license);

        this.withdrawal.setOnClickListener( v -> {

        });

        this.logout.setOnClickListener( v -> {

        });

        this.policy.setOnClickListener( v -> {
            this.startActivity(new Intent(this, PolicyActivity.class));
        });

        this.license.setOnClickListener( v -> {
            this.startActivity(new Intent(this, LicenseActivity.class));
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0); //애니메이션 없애기
    }


}
