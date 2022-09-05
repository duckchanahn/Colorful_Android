package com.example.colorful_android.Search;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.colorful_android.Color.ColorActivity;
import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.MainActivity;
import com.example.colorful_android.Mypage.MypageActivity;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SerachActivity extends AppCompatActivity {

    private String TAG_HOME = "home_fragment";
    private String TAG_SEARCH = "search_fragment";
    private String TAG_COLOR = "color_fragment";
    private String TAG_MYPAGE = "mypage_fragment";

    private ChipGroup chipGroup;
    private GridLayout gridLayout;

    private List<TourSpot> tourSpotList;

    private List<ImageView> imageViewList;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_search_page);

        this.chipGroup = findViewById(R.id.chip_group);
        this.gridLayout = findViewById(R.id.gridview);

        this.imageViewList = new ArrayList<>();
        this.page = 0;

        excute_getTourList();

//        SwipeRefreshLayout swipeRefreshLayout;
//        swipeRefreshLayout = findViewById(R.id.swipeLayout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadList();
//                swipeRefreshLayout.setRefreshing(false);
////                swipeRefreshLayout.isRefreshing() = false;
//             }
//        });


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

        Menu menu = navigation.getMenu();
        for (int i = 0; i < navigation.getMenu().size(); i++) {
            MenuItem menuItem = menu.getItem(i);

            switch (menuItem.getItemId()) {
                case R.id.homeFragment:
//                    Log.e("nav", "homeFragment"  + menuItem.getItemId());
                    menuItem.setIcon(R.drawable.ic_nav_home);
                    break;
                case R.id.searchFragment:
//                    Log.e("nav", "searchFragment"  + menuItem.getItemId());
                    menuItem.setIcon(R.drawable.ic_nav_search_selected);
                    menuItem.setChecked(true);
                    break;
                case R.id.colorFragment:
//                    Log.e("nav", "colorFragment"  + menuItem.getItemId());
                    menuItem.setIcon(R.drawable.ic_nav_color);
                    break;
                case R.id.mypageFragment:
//                    Log.e("nav", "mypageFragment"  + menuItem.getItemId());
                    menuItem.setIcon(R.drawable.ic_nav_mypage);
                    break;
            }
        }

    }

    /////////////////////////////////////////////////////////////////
    // 여행지 목록 받아오기
    /////////////////////////////////////////////////////////////////

    private void excute_getTourList() {
        Call<List<TourSpot>> call = MyRetrofit.getApiService().randomTourSpot();
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
                loadList();
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
        Log.e("page", "page num : " + page);

        for (int i = page; i < page + 9; i++) {
            ImageView imageView = new ImageView(getBaseContext());
            imageView.setId(i);
            getOriginalBitmap(tourSpotList.get(i).getImages(), imageView);

            imageView.setOnClickListener( v -> {
                Log.e("select image", "id : " + imageView.getId() + ", url : " + tourSpotList.get(imageView.getId()).getImages());
            });

            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageViewList.add(imageView);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams(gridLayout.getLayoutParams());

            Log.e("gird", "i%3 : " + i%3 + ", i/3 : " + i/3);
            GridLayout.Spec rowSpec;
            GridLayout.Spec columnSpec;
//            if((page-i) == 2 || (page-i) == 7) {
//                rowSpec = GridLayout.spec(i/3, 3); //   btn
//                columnSpec = GridLayout.spec(i%3, 3);//   btn
//                params = new GridLayout.LayoutParams(rowSpec, columnSpec);
////                params.width = (gridLayout.getWidth() / 3 )* 2; //  btn
//                params.width = (gridLayout.getWidth()  ); //  btn
//                params.height = params.width;//  btn
//                params.setMargins(5, 0, 5, 10);//  btn
//                params.setGravity(Gravity.FILL);//  btn  （  ）
//            } else {
                rowSpec = GridLayout.spec(i / 3); //   btn
                columnSpec = GridLayout.spec(i % 3);//   btn
                params = new GridLayout.LayoutParams(rowSpec, columnSpec);
                params.width = gridLayout.getWidth() / 3; //  btn
                params.height = params.width;//  btn
                params.setMargins(5, 0, 5, 10);//  btn
                params.setGravity(Gravity.LEFT);//  btn  （  ）
//            }

//            gridLayout.setLayoutParams(params);
            gridLayout.addView(imageView, params);

//            gridLayout.addView(imageView, layoutParams);
        }
        page += 9;
    }

    /////////////////////////////////////////////////////////////////
    // 이미지 받아오기
    /////////////////////////////////////////////////////////////////

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


    /////////////////////////////////////////////////////////////////
    // 내비
    /////////////////////////////////////////////////////////////////

    public void selected_navi(String TAG) {
        Log.e("search", TAG);
        Intent intent = null;

        if(TAG == TAG_HOME){
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else if(TAG == TAG_SEARCH){
//            intent = new Intent(getApplicationContext(), SerachActivity.class);
            return;
        }
        else if(TAG == TAG_COLOR){
            intent = new Intent(getApplicationContext(), ColorActivity.class);
            finish();
        }
        else if(TAG == TAG_MYPAGE){
            intent = new Intent(getApplicationContext(), MypageActivity.class);
            finish();
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
        getApplicationContext().startActivity(intent);
        overridePendingTransition(0, 0); //애니메이션 없애기

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);

        finish();
        overridePendingTransition(0, 0); //애니메이션 없애기

    }
}
