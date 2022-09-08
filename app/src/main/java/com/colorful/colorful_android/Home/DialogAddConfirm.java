package com.colorful.colorful_android.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.colorful.colorful_android.R;

public class DialogAddConfirm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_confirm);

        Intent intent = getIntent();


        TextView tourSpotName = findViewById(R.id.tour_name);
        tourSpotName.setText(intent.getStringExtra("tourSpotName"));
        TextView paletteName = findViewById(R.id.palette_name);
        paletteName.setText(intent.getStringExtra("paletteName"));
        Button confirm = findViewById(R.id.btn_confirm);
        confirm.setOnClickListener(v -> {
            finish();
        });
    }

}
