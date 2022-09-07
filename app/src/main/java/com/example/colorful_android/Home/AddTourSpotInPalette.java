package com.example.colorful_android.Home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.colorful_android.DTO.Customer;
import com.example.colorful_android.DTO.Palette;
import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.Home.PaletteCard;
import com.example.colorful_android.R;
import com.example.colorful_android.Retrofit.MyRetrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTourSpotInPalette extends Activity {

    private List<Palette> paletteList;
    private TourSpot tourSpot;
    private CardView card;
    private Button newPalette;
    private Button cancel;
    private Button confirm;

    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_palette_dialog);

        Intent intent = getIntent();
        this.tourSpot = (TourSpot) intent.getSerializableExtra("TourSpot");

        ((TextView)findViewById(R.id.title)).setText(tourSpot.getName());

        this.newPalette = findViewById(R.id.new_palette);
        this.linearLayout = findViewById(R.id.linearLayout);
        this.selectPaletteId = -1;
        this.selectPaletteName = "";

        this.cancel = findViewById(R.id.btn_cancel);
        this.cancel.setOnClickListener(v -> {
            finish();
        });
        this.confirm = findViewById(R.id.btn_confirm);
        this.confirm.setOnClickListener(v -> {
           Log.e("addtsinp", "select : " + selectPaletteId);
           if(selectPaletteId != -1) {
               Log.e("confirm", "select : " + selectPaletteId);
               excute_add();
           } else {
               Toast.makeText(getBaseContext(), "팔레트를 선택해주세요", Toast.LENGTH_SHORT);
           }
        });



        excute();
    }



    private int selectPaletteId;
    private String selectPaletteName;
    private void excute() {
        Log.e("colorActivity", "customer id : " + Customer.getInstance().getCustomerId());
        Call<List<Palette>> call = MyRetrofit.getApiService().paletteList(Customer.getInstance().getCustomerId());
        call.enqueue(new Callback<List<Palette>>() {
            @Override
            public void onResponse(Call<List<Palette>> call, Response<List<Palette>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());


                paletteList = response.body();
                for (Palette palette : paletteList) {
                    PaletteCard paletteCard = new PaletteCard(getBaseContext(), palette);
                    paletteCard.getCard().setBackground(getResources().getDrawable(R.drawable.psy_test_answer_non_select_btn, null));
                    paletteCard.getCard().findViewById(R.id.card_layout).setOnClickListener( v -> {
                        selectPaletteId = palette.getPaletteId();
                        selectPaletteName = palette.getName();
                        paletteCard.getCard().setBackground(getResources().getDrawable(R.drawable.psy_test_answer_select_btn, null));
                    });
                    linearLayout.addView(paletteCard.getCard());
                }

            }

            @Override
            public void onFailure(Call<List<Palette>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    private void excute_add() {
        Call<Integer> call = MyRetrofit.getApiService().paletteAdd(selectPaletteId, tourSpot.getTourSpotId());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());

                Intent intent_confirm = new Intent(getBaseContext(), DialogAddConfirm.class);
                intent_confirm.putExtra("tourSpotName", tourSpot.getName());
                intent_confirm.putExtra("paletteName", selectPaletteName);

                finish();

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
