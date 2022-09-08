package com.colorful.colorful_android.Color;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.colorful.colorful_android.DTO.Palette;
import com.colorful.colorful_android.R;

import java.io.Serializable;

public class TourCardView implements Serializable {

    private TextView tourName;
    private TextView tourDate;
    private TextView tourCount;
    private ImageView cardImg;

    private Palette palette;
    private View card;

    private ImageView deleteButton;

    private LayoutInflater layoutInflater;

    public TourCardView(@NonNull Context context, Palette palette) {
        this.palette = palette;
        this.layoutInflater = LayoutInflater.from(context);
        this.card = (ConstraintLayout)layoutInflater.inflate(R.layout.tour_card_view, null, false);
        init(context);


//        this.setOnClickListener(v -> {
//            Intent intent = new Intent(context, ColorDetailActivity.class);
//            intent.putExtra("palette", palette);
//            context.startActivity(intent);
//        });
    }

    private void init(Context context){
//        ConstraintLayout inflater =(ConstraintLayout)context.getSystemService(Context.);

        this.tourName = card.findViewById(R.id.tour_name);
        this.tourName.setText(this.palette.getName());
        this.tourDate = card.findViewById(R.id.tour_date);
        this.tourDate.setText(this.palette.getDue());
//        this.tourCount = card.findViewById(R.id.tour_count);
//        this.tourCount.setText("3");
        this.cardImg = card.findViewById(R.id.card_img);

        this.deleteButton = card.findViewById(R.id.btn_delete);

        Log.e("palette", "name : " + this.palette.getName() + ", due : " + this.palette.getDue() + ", img : " );
//        this.tourName.setText(palette.);

    }

    public ConstraintLayout getCard() {
        return (ConstraintLayout)card;
    }
    public ImageView getDeleteButton() {return deleteButton;}
}