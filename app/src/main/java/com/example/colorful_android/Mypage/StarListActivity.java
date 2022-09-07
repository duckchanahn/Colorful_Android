package com.example.colorful_android.Mypage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.colorful_android.Color.ColorActivity;
import com.example.colorful_android.DTO.Customer;
import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.Home.HomeMainDialog;
import com.example.colorful_android.MainActivity;
import com.example.colorful_android.R;
import com.example.colorful_android.Retrofit.MyRetrofit;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.navigation.NavigationBarView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StarListActivity extends AppCompatActivity {

    private LinearLayout linearLayoutTop;

    private List<TourSpot> tourSpotList;

    private List<ImageView> imageViewList;
    private int page;

    private boolean position_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_pick);

//        this.chipGroup = findViewById(R.id.chip_group);
//        this.gridLayout = findViewById(R.id.gridview);
        this.linearLayoutTop = findViewById(R.id.linearLayout);

        this.imageViewList = new ArrayList<>();
        this.page = 0;

        excute_getTourList();

//        ScrollView scrollView = findViewById(R.id.scrollView);
//        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//             @Override
//             public void onScrollChange(View v, int i, int i1, int i2, int i3) {
//                if (position_flag) {
//                    if(!v.canScrollVertically(1)) {
//                        Log.e("scroll event!!", "최하단!!");
//                        if(page > 36) page = 0;
//                        loadList();
//                    }
//                    position_flag = false;
//                } else {
//                    position_flag = true;
//                }
//             }
//
//         });


    }

    /////////////////////////////////////////////////////////////////
    // 여행지 목록 받아오기
    /////////////////////////////////////////////////////////////////

    private void excute_getTourList() {
        Call<List<TourSpot>> call = MyRetrofit.getApiService().starTourspotList(Customer.getInstance().getCustomerId());
        call.enqueue(new Callback<List<TourSpot>>() {
            @Override
            public void onResponse(Call<List<TourSpot>> call, Response<List<TourSpot>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());

                Log.e("searchActivity", "api result size : " + response.body().size());
                tourSpotList = response.body();
                Log.e("mypage", "response. size : " + tourSpotList.size());
//                while(page < tourSpotList.size()) {
                    loadList();
//                }
            }

            @Override
            public void onFailure(Call<List<TourSpot>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }



    //

    private void loadList() {
        Log.e("page", "page num : " + page + " tourSpotList size : " + tourSpotList.size());

        LinearLayout linear = findViewById(R.id.linearLayout);

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels); // Display 사이즈의 90%

        int index = 0;

        if (tourSpotList.size() == 0) {
            return;
        } else if (tourSpotList.size() == 1) {
            ImageView imageView1 = new ImageView(getBaseContext());
            imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView1.setLayoutParams(new LinearLayoutCompat.LayoutParams(width / 3, width / 3));
            imageView1.setPadding(20, 10, 20, 10);
            imageView1.setCropToPadding(true);

            TourSpot tourSpot = tourSpotList.get(0);
            getOriginalBitmap(tourSpotList.get(0).getImages(), imageView1);
            imageView1.setOnClickListener( v -> {
                Intent imageViewIntent = new Intent(this, HomeMainDialog.class);
                imageViewIntent.putExtra("TourSpot", tourSpotList.get(0));
                imageViewIntent.putExtra("prevPage", "SearchActivity");
                this.startActivity(imageViewIntent);
            });

            linear.addView(imageView1);
            return;
        } else if (tourSpotList.size() == 2) {
            ImageView imageView1 = new ImageView(getBaseContext());
            imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView1.setLayoutParams(new LinearLayoutCompat.LayoutParams(width / 3, width / 3));
            imageView1.setPadding(20, 10, 20, 10);
            imageView1.setCropToPadding(true);

            TourSpot tourSpot = tourSpotList.get(0);
            getOriginalBitmap(tourSpotList.get(0).getImages(), imageView1);
            imageView1.setOnClickListener( v -> {
                Intent imageViewIntent = new Intent(this, HomeMainDialog.class);
                imageViewIntent.putExtra("TourSpot", tourSpotList.get(0));
                imageViewIntent.putExtra("prevPage", "SearchActivity");
                this.startActivity(imageViewIntent);
            });

            linear.addView(imageView1);

            imageView1 = new ImageView(getBaseContext());
            imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView1.setLayoutParams(new LinearLayoutCompat.LayoutParams(width / 3, width / 3));
            imageView1.setPadding(20, 10, 20, 10);
            imageView1.setCropToPadding(true);

            tourSpot = tourSpotList.get(1);
            getOriginalBitmap(tourSpotList.get(1).getImages(), imageView1);
            imageView1.setOnClickListener( v -> {
                Intent imageViewIntent = new Intent(this, HomeMainDialog.class);
                imageViewIntent.putExtra("TourSpot", tourSpotList.get(1));
                imageViewIntent.putExtra("prevPage", "SearchActivity");
                this.startActivity(imageViewIntent);
            });

            linear.addView(imageView1);

            return;
        }

        for (int i = 0; i < tourSpotList.size(); i++) {
            LinearLayout linearLine = null;
            if(i % 3 == 0 && i != 0) {
                linear.addView(linearLine);
                linearLine = new LinearLayout(getBaseContext());
                linearLine.setOrientation(LinearLayout.HORIZONTAL);
            }

            if (i == 0) {
                linearLine = new LinearLayout(getBaseContext());
                linearLine.setOrientation(LinearLayout.HORIZONTAL);
            }

            ImageView imageView1 = new ImageView(getBaseContext());
            imageView1.setScaleType(ImageView.ScaleType.CENTER_CROP);

            imageView1.setLayoutParams(new LinearLayoutCompat.LayoutParams(width / 3, width / 3));
            imageView1.setPadding(20, 10, 20, 10);
            imageView1.setCropToPadding(true);

            TourSpot tourSpot = tourSpotList.get(i);
            getOriginalBitmap(tourSpotList.get(i).getImages(), imageView1);
            imageView1.setOnClickListener( v -> {
                Intent imageViewIntent = new Intent(this, HomeMainDialog.class);
                imageViewIntent.putExtra("TourSpot", tourSpot);
                imageViewIntent.putExtra("prevPage", "SearchActivity");
                this.startActivity(imageViewIntent);
            });

            linearLine.addView(imageView1);
        }



    }

    /////////////////////////////////////////////////////////////////
    // 이미지 받아오기
    /////////////////////////////////////////////////////////////////

    private Bitmap bitmap;
    private void getOriginalBitmap(String urlString, ImageView imageView) {
        Log.e("select image", "tourSpot : " + " url : " + urlString);

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


    @Override
    public void onBackPressed() {
        finish();
    }
}