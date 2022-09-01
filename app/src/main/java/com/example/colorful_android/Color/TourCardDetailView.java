package com.example.colorful_android.Color;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.colorful_android.DTO.Palette;
import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.Home.HomeMainDialog;
import com.example.colorful_android.R;

public class TourCardDetailView extends ConstraintLayout {

    private TextView tourSpotName;
    private TextView tourSpotAddress;
    private TextView tourCount;
    private ImageView cardImg;

    private TourSpot tourspot;

    private View card;

    private LayoutInflater layoutInflater;

    public TourCardDetailView(@NonNull Context context, TourSpot tourspot) {
        super(context);

        this.tourspot = tourspot;
        layoutInflater = LayoutInflater.from(context);
        init(context);

        this.setOnClickListener(v -> {
            Log.e("click tour card", "move tourSpot Detail!!");
//            Intent intent = new Intent(context, ColorDetailActivity.class);
//            intent.putExtra("palette", tourspot);
//            context.startActivity(intent);
        });
    }

    private void init(Context context){
//        ConstraintLayout inflater =(ConstraintLayout)context.getSystemService(Context.);
        card = (ConstraintLayout)layoutInflater.inflate(R.layout.tour_card_view, null, false);

        this.tourSpotName = card.findViewById(R.id.detail_name);
        this.tourSpotName.setText(this.tourspot.getName());
        this.tourSpotAddress = card.findViewById(R.id.detail_adress);
        this.tourSpotAddress.setText(this.tourspot.getAddress());

        this.cardImg = card.findViewById(R.id.detail_img);
//        this.cardImg.setText(this.tourspot.getName());

        this.card.setOnClickListener(v -> {
            Intent intent = new Intent(context, HomeMainDialog.class);
            intent.putExtra("tourspot", tourspot);
            context.startActivity(intent);
        });
    }
}
