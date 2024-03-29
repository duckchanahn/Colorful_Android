package com.colorful.colorful_android.Color;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.colorful.colorful_android.DTO.TourSpot;
import com.colorful.colorful_android.R;

public class TourCardDetailView extends Activity {

    private TextView tourSpotName;
    private TextView tourSpotAddress;
    private TextView tourCount;
    private ImageView cardImg;

    private TourSpot palette;

    private View card;

    private LayoutInflater layoutInflater;

    public TourCardDetailView(@NonNull Context context, TourSpot palette) {
        this.palette = palette;
        this.layoutInflater = LayoutInflater.from(context);
        this.card = (ConstraintLayout)layoutInflater.inflate(R.layout.tour_detail_card_view, null, false);
        this.init(context);



//        this.setOnClickListener(v -> {
//            Log.e("click tour card", "move tourSpot Detail!!");
//            Intent intent = new Intent(context, ColorDetailActivity.class);
//            intent.putExtra("palette", palette);
//            context.startActivity(intent);
//        });
    }

    private void init(Context context){

        this.tourSpotName = card.findViewById(R.id.detail_name);
        this.tourSpotName.setText(this.palette.getName());
        this.tourSpotAddress = card.findViewById(R.id.detail_adress);
        this.tourSpotAddress.setText(this.palette.getAddress());

        this.cardImg = card.findViewById(R.id.detail_img);


    }

    public ConstraintLayout getCard() {
        return (ConstraintLayout)card;
    }
    public ImageView getImageView() {return cardImg;}
}