package com.example.colorful_android.Color;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.colorful_android.DTO.Palette;
import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.Home.HomeMainDialog;
import com.example.colorful_android.R;
import com.example.colorful_android.Retrofit.MyRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ColorDetailActivity extends AppCompatActivity {

    private List<TourSpot> tourSpotList;
    private LinearLayout listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palette_detail_page);

        this.listView = findViewById(R.id.linearLayout);

        Intent intent = getIntent();
        Palette palette = (Palette) intent.getSerializableExtra("palette");

        Log.e("palette id", "id : " + palette.getPaletteId());

        excute(palette.getPaletteId());
    }

    private void excute(int paletteId) {
        Call<List<TourSpot>> call = MyRetrofit.getApiService().paletteTourspot(paletteId);
        call.enqueue(new Callback<List<TourSpot>>() {
            @Override
            public void onResponse(Call<List<TourSpot>> call, Response<List<TourSpot>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());
                tourSpotList = response.body();
                for(TourSpot tourSpot : tourSpotList) {
                    TourCardDetailView cardView = new TourCardDetailView(getApplicationContext(), tourSpot);
                    ConstraintLayout card = cardView.getCard();
                    card.findViewById(R.id.card_layout).setOnClickListener(v -> {
                        Intent intent = new Intent(getApplicationContext(), HomeMainDialog.class);
                        intent.putExtra("tourspot", tourSpot);
                        getApplicationContext().startActivity(intent);
                    });
                    listView.addView(card);
                }
            }
            @Override
            public void onFailure(Call<List<TourSpot>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
