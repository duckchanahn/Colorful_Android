package com.example.colorful_android.Home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class HomeMainDialog extends Activity {

    private TextView tourName;
    private TextView address;
    private TextView telephone;
    private TextView homepage;
    private TextView hours;
    private TextView park;
    private TextView content;
    private Button addPalette;
    private ImageView addStar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_bottom_dialog);


        Intent intent = getIntent();
        TourSpot tourSpot = (TourSpot) intent.getSerializableExtra("TourSpot");

        Log.e("dialog", String.valueOf(tourSpot));

        this.tourName = findViewById(R.id.tour_name);
        this.tourName.setText(tourSpot.getName());
        this.address = findViewById(R.id.address);
        this.address.setText(tourSpot.getAddress());
        this.telephone = findViewById(R.id.telephone);
        this.telephone.setText(tourSpot.getTelephone());
        this.homepage = findViewById(R.id.homepage);
        this.homepage.setText(tourSpot.getHomepage());

        this.hours = findViewById(R.id.hours);
        if (tourSpot.getHours() == null) {this.hours.setVisibility(View.GONE);}
        else {this.hours.setText(tourSpot.getHours());}

        this.park = findViewById(R.id.park);
        if (tourSpot.getParking() == null) {this.park.setVisibility(View.GONE);}
        else {this.park.setText(tourSpot.getParking());}

        this.content = findViewById(R.id.content);
        this.content.setText(tourSpot.getContent());

        this.addPalette = findViewById(R.id.add_palette);
        this.addPalette.setOnClickListener(v -> {

        });
        this.addStar = findViewById(R.id.add_star);
        this.addStar.setOnClickListener(v -> {

        });

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


//        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
//        int width = (int) (dm.widthPixels); // Display 사이즈의 90%
//        int height = (int) (dm.heightPixels); // Display 사이즈의 90%
//        getWindow().getAttributes().width = width;
//        getWindow().getAttributes().height = height;

    }

}
