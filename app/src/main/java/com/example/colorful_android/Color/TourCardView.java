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
import com.example.colorful_android.R;

public class TourCardView extends ConstraintLayout {

    private TextView tourName;
    private TextView tourDate;
    private TextView tourCount;
    private ImageView cardImg;

    private Palette palette;
    private View card;

    private LayoutInflater layoutInflater;

    public TourCardView(@NonNull Context context, Palette palette) {
        super(context);

        this.palette = palette;
        layoutInflater = LayoutInflater.from(context);
        init(context);

//        this.setOnClickListener(v -> {
//            Intent intent = new Intent(context, ColorDetailActivity.class);
//            intent.putExtra("palette", palette);
//            context.startActivity(intent);
//        });
    }

    private void init(Context context){
//        ConstraintLayout inflater =(ConstraintLayout)context.getSystemService(Context.);
        card = (ConstraintLayout)layoutInflater.inflate(R.layout.tour_card_view, null, false);

//        card.set

        this.tourName = card.findViewById(R.id.tour_name);
        this.tourName.setText(this.palette.getName());
        this.tourDate = card.findViewById(R.id.tour_date);
        this.tourDate.setText(this.palette.getDue());
        this.tourCount = card.findViewById(R.id.tour_count);
        this.tourCount.setText("3");
        this.cardImg = card.findViewById(R.id.card_img);

        Log.e("palette", "name : " + this.palette.getName() + ", due : " + this.palette.getDue() + ", img : " );
//        this.tourName.setText(palette.);


    }

    public View getCard(Context context) {
        this.card.setOnClickListener(v -> {
            Log.e("click card", "click!! (palette id : " + this.palette.getPaletteId() + ")");
            Intent intent = new Intent(context, ColorDetailActivity.class);
            intent.putExtra("palette", palette);
            context.startActivity(intent);
        });
        return card;
    }
}
