package com.example.colorful_android.Color;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.colorful_android.DTO.Customer;
import com.example.colorful_android.DTO.Palette;
import com.example.colorful_android.Fragment.NaviActivity;
import com.example.colorful_android.Home.HomeMainDialog;
import com.example.colorful_android.MainActivity;
import com.example.colorful_android.Mypage.MypageActivity;
import com.example.colorful_android.R;
import com.example.colorful_android.Retrofit.MyRetrofit;
import com.example.colorful_android.Search.SerachActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ColorActivity extends AppCompatActivity {

    //팔레트 리스트뷰
    private List<Palette> tourList;

    private LinearLayout listView;

    private String TAG_HOME = "home_fragment";
    private String TAG_SEARCH = "search_fragment";
    private String TAG_COLOR = "color_fragment";
    private String TAG_MYPAGE = "mypage_fragment";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_palette_list);

//        view = layoutInflater.inflate(R.layout.layout_complex, null, false);
//        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        this.listView = layoutInflater.inflate(R.id.linearLayout, null, false);
        this.listView = findViewById(R.id.linearLayout);
        this.tourList = new ArrayList<>();

        BottomNavigationView navigation = findViewById(R.id.nav_main);

        navigation.setOnItemSelectedListener(
                new NavigationBarView.OnItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                 switch (item.getItemId()) {
                     case R.id.homeFragment:
                         selected_navi(TAG_HOME);
                         break;
                     case R.id.searchFragment:
                         selected_navi(TAG_SEARCH);
                         break;
                     case R.id.colorFragment:
                         selected_navi(TAG_COLOR);
                         break;
                     case R.id.mypageFragment:
                         selected_navi(TAG_MYPAGE);
                         break;
                 }

                 return false;
             }
         }
        );

        excute();
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0); //애니메이션 없애기
    }

    public void selected_navi(String TAG) {
        Intent intent = null;

        if(TAG == TAG_HOME){
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else if(TAG == TAG_SEARCH){
            intent = new Intent(getApplicationContext(), SerachActivity.class);
        }
        else if(TAG == TAG_COLOR){
//            intent = new Intent(getApplicationContext(), ColorActivity.class);
            return;
        }
        else if(TAG == TAG_MYPAGE){
            intent = new Intent(getApplicationContext(), MypageActivity.class);

        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
        getApplicationContext().startActivity(intent);
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

                    ConstraintLayout card = cardView.getCard();
                    card.findViewById(R.id.card_img).setOnClickListener(v -> {
                        Log.e("click card", "click!! (palette id : " + palette.getPaletteId() + ")");
                        Intent intent = new Intent(getApplicationContext(), ColorDetailActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
                        intent.putExtra("palette", palette);
                        getApplicationContext().startActivity(intent);
                    });

                    listView.addView(card);

                }

//                listView.setOnClickListener(v -> {
//                    Log.e("click card", "click!! (palette id : )");
//                    Intent intent = new Intent(getApplicationContext(), ColorDetailActivity.class);
////                    intent.putExtra("palette", palette);
//                    getApplicationContext().startActivity(intent);
//                });
            }
            @Override
            public void onFailure(Call<List<Palette>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }
}
