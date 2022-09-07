package com.example.colorful_android.Home;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.colorful_android.DTO.Palette;
import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.R;

public class PaletteCard extends Activity {

    private TextView paletteName;
    private TextView paletteDue;

    private Palette palette;

    private CardView card;

    private LayoutInflater layoutInflater;

    public PaletteCard(@NonNull Context context, Palette palette) {
        this.palette = palette;
        this.layoutInflater = LayoutInflater.from(context);
        this.card = (CardView)layoutInflater.inflate(R.layout.add_palette_card_view, null, false);


        this.paletteName = card.findViewById(R.id.detail_name);
        this.paletteName.setText(palette.getName());
        this.paletteDue = card.findViewById(R.id.detail_adress);
        this.paletteDue.setText(palette.getDue());
    }

    public CardView getCard() {return card;}
}
