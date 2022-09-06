package com.example.colorful_android.Color;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
                        intent.putExtra("TourSpot", tourSpot);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(intent);
                    });
                    getOriginalBitmap(tourSpot.getImages(), cardView.getImageView());
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

    private Bitmap bitmap;
    private void getOriginalBitmap(String urlString, ImageView imageView) {
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);

                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true); // 서버로 부터 응답 수신
                    conn.connect();

                    InputStream is = conn.getInputStream(); // InputStream 값 가져오기
                    bitmap = BitmapFactory.decodeStream(is); // Bitmap으로 변환

                } catch (MalformedURLException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start(); // Thread 실행

        try {
            // 메인 Thread는 별도의 작업 Thread가 작업을 완료할 때까지 대기해야한다
            // join()를 호출하여 별도의 작업 Thread가 종료될 때까지 메인 Thread가 기다리게 한다
            mThread.join();

            // 작업 Thread에서 이미지를 불러오는 작업을 완료한 뒤
            // UI 작업을 할 수 있는 메인 Thread에서 ImageView에 이미지를 지정한다
            imageView.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}