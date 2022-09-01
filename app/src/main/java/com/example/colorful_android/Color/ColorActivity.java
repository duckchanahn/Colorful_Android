package com.example.colorful_android.Color;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.DTO.Customer;
import com.example.colorful_android.DTO.Palette;
import com.example.colorful_android.R;
import com.example.colorful_android.Retrofit.MyRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ColorActivity extends AppCompatActivity {

    //팔레트 리스트뷰
    private List<Palette> tourList;

    private LinearLayout listView;
//    tourList.add(new TourInfo(R.drawable.card_blue_re, "강원도 혼행", "22.8.21-22.8.22", "3개"));
//    new TourInfo(R.drawable.card_green, "예림이랑 여수", "22.9.10-22.9.12", "2개");
//    new TourInfo(R.drawable.card_pink, "부산 가족여행", "22.10.7-22.10.10", "4개");


//    //팔레트 디테일 리스트뷰
//    var DetailTourList = arrayListOf<TourInfoDetail>(
//            TourInfoDetail(R.drawable.ex_detail_img, "감성공작소", "강원도 삼척시\n 두줄까지"),
//    TourInfoDetail(R.drawable.ex_detail_img, "낙산 해수욕장", "강원도 양양군\n 두줄까지"),
//    TourInfoDetail(R.drawable.ex_detail_img, "안반데기 마을", "강원도 강릉시\n 두줄까지")
//    )


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_palette_list);

//        view = layoutInflater.inflate(R.layout.layout_complex, null, false);
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        this.listView = layoutInflater.inflate(R.id.linearLayout, null, false);
        this.listView = findViewById(R.id.linearLayout);
        this.tourList = new ArrayList<>();

        excute();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0); //애니메이션 없애기
    }

    private void excute() {
        Log.e("colorActivity", "customer id : " + Customer.getInstance().getCustomerId());
        Call<List<Palette>> call = MyRetrofit.getApiService().paletteList(Customer.getInstance().getCustomerId());
        call.enqueue(new Callback<List<Palette>>() {
            @Override
            public void onResponse(Call<List<Palette>> call, Response<List<Palette>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());
                tourList = response.body();
                for(Palette palette : tourList) {
                    TourCardView cardView = new TourCardView(getApplicationContext(), palette);
                    Log.e("cardView", cardView.toString());
                    listView.addView(cardView.getCard(getBaseContext()));

                }
            }
            @Override
            public void onFailure(Call<List<Palette>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
