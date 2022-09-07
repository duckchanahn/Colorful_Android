package com.example.colorful_android.Search;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import com.example.colorful_android.Color.ColorActivity;
import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.Home.HomeMainDialog;
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

public class SerachActivity_setFilter extends AppCompatActivity {

    private String TAG_HOME = "home_fragment";
    private String TAG_SEARCH = "search_fragment";
    private String TAG_COLOR = "color_fragment";
    private String TAG_MYPAGE = "mypage_fragment";

    private ChipGroup chipGroup;
    private GridLayout gridLayout;
    private LinearLayout linearLayoutTop;

    private ArrayList<TourSpot> tourSpotList;
    private ArrayList<String> chipGroup_filter;

    private List<ImageView> imageViewList;
    private int page;

    private boolean position_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_search_page);

//        this.chipGroup = findViewById(R.id.chip_group);
//        this.gridLayout = findViewById(R.id.gridview);
        this.linearLayoutTop = findViewById(R.id.linearLayout);
        this.chipGroup_filter = null;

        this.imageViewList = new ArrayList<>();
        this.page = 0;

        Intent intent_getDate = getIntent();
        this.tourSpotList = (ArrayList<TourSpot>) intent_getDate.getSerializableExtra("tourSpot");
        this.chipGroup_filter = (ArrayList<String>) intent_getDate.getSerializableExtra("chipGroup");

        Button filter = findViewById(R.id.btn_filter);
        filter.setOnClickListener( v -> {
            Intent filterIntent = new Intent(this, SearchFilterActivity.class);
            filterIntent.putExtra("tourSpot", tourSpotList);
            filterIntent.putExtra("chipGroup", chipGroup_filter);
            this.startActivity(filterIntent);
        });

//        SwipeRefreshLayout swipeRefreshLayout;
//        swipeRefreshLayout = findViewById(R.id.swipeLayout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                loadList();
//                loadList();
//                loadList();
//                swipeRefreshLayout.setRefreshing(false);
////                swipeRefreshLayout.isRefreshing() = false;
//             }
//        });

        ScrollView scrollView = findViewById(R.id.scrollView);
        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
             @Override
             public void onScrollChange(View v, int i, int i1, int i2, int i3) {
                if (position_flag) {
                    if(!v.canScrollVertically(1)) {
                        Log.e("scroll event!!", "최하단!!");
                        if(page > 36) page = 0;
                        loadList();
                    }
                    position_flag = false;
                } else {
                    position_flag = true;
                }
             }

         });

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
                tourSpotList = (ArrayList<TourSpot>) response.body();
                loadList();
                loadList();
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
        Log.e("page", "page num : " + page + " tourSpotList size : " + tourSpotList.size());

        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels); // Display 사이즈의 90%
        List<ImageView> imageList = new ArrayList<>();
        for (int i = page; i < page + 9; i++) {
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
            imageList.add(imageView1);
        }

        LinearLayout linear = findViewById(R.id.linearLayout);

        LinearLayout linearLine = new LinearLayout(getBaseContext());
        linearLine.setOrientation(LinearLayout.HORIZONTAL);
        linearLine.addView(imageList.get(0));
        linearLine.addView(imageList.get(1));
        linearLine.addView(imageList.get(2));
        linear.addView(linearLine);

        linearLine = new LinearLayout(getBaseContext());
        linearLine.setOrientation(LinearLayout.HORIZONTAL);
        linearLine.addView(imageList.get(3));
        linearLine.addView(imageList.get(4));
        linearLine.addView(imageList.get(5));
        linear.addView(linearLine);

        linearLine = new LinearLayout(getBaseContext());
        linearLine.setOrientation(LinearLayout.HORIZONTAL);
        linearLine.addView(imageList.get(6));
        linearLine.addView(imageList.get(7));
        linearLine.addView(imageList.get(8));
        linear.addView(linearLine);



//        for (int i = page; i < page + 9; i++) {



//            LayoutInflater layoutInflater = LayoutInflater.from(getBaseContext());
//            ConstraintLayout linearLayout = (ConstraintLayout)layoutInflater.inflate(R.layout.search_page_grid, null, false);
//
//            ImageView imageView1 = linearLayout.findViewById(R.id.image1);
//            imageView1.setX(width/3);
//            imageView1.setY(width/3);
////            imageView1.setLayoutParams(new LinearLayoutCompat.LayoutParams(width/3, width/3));
//            getOriginalBitmap(tourSpotList.get(page).getImages(), imageView1);
//            Log.e("select image", "page : " + page + "id : " + tourSpotList.get(page).getTourSpotId() + ", url : " + tourSpotList.get(page).getImages());
//            imageView1.setOnClickListener( v -> {
//                Log.e("select image", "id : " + imageView1.getId() + ", url : " + tourSpotList.get(imageView1.getId()).getImages());
//            });
//            ImageView imageView2 = linearLayout.findViewById(R.id.image2);
//        imageView2.setX(width/3);
//        imageView2.setY(width/3);
//            getOriginalBitmap(tourSpotList.get(page+1).getImages(), imageView2);
//        Log.e("select image", "page1 : " + page + "id : " + tourSpotList.get(page).getTourSpotId() + ", url : " + tourSpotList.get(page+1).getImages());
//            imageView2.setOnClickListener( v -> {
//
//            });
//            ImageView imageView3 = linearLayout.findViewById(R.id.image3);
//        imageView3.setX(width/3);
//        imageView3.setY(width/3);
//            getOriginalBitmap(tourSpotList.get(page+2).getImages(), imageView3);
//        Log.e("select image", "page2 : " + page + "id : " + tourSpotList.get(page).getTourSpotId() + ", url : " + tourSpotList.get(page+2).getImages());
//            imageView3.setOnClickListener( v -> {
//
//            });
//            ImageView imageView4 = linearLayout.findViewById(R.id.image4);
//        imageView4.setX(width/3);
//        imageView4.setY(width/3);
//            getOriginalBitmap(tourSpotList.get(page+3).getImages(), imageView4);
//        Log.e("select image", "page3 : " + page + "id : " + tourSpotList.get(page).getTourSpotId() + ", url : " + tourSpotList.get(page+3).getImages());
//            imageView4.setOnClickListener( v -> {
//
//            });
//            ImageView imageView5 = linearLayout.findViewById(R.id.image5);
//        imageView5.setX(width/3);
//        imageView5.setY(width/3);
//            getOriginalBitmap(tourSpotList.get(page+4).getImages(), imageView5);
//            imageView5.setOnClickListener( v -> {
//
//            });
//            ImageView imageView6 = linearLayout.findViewById(R.id.image6);
//        imageView6.setX(width/3);
//        imageView6.setY(width/3);
//            getOriginalBitmap(tourSpotList.get(page+5).getImages(), imageView6);
//            imageView6.setOnClickListener( v -> {
//
//            });
//            ImageView imageView7 = linearLayout.findViewById(R.id.image7);
//        imageView7.setX(width/3);
//        imageView7.setY(width/3);
//            getOriginalBitmap(tourSpotList.get(page+6).getImages(), imageView7);
//            imageView7.setOnClickListener( v -> {
//
//            });
//            ImageView imageView8 = linearLayout.findViewById(R.id.image8);
//        imageView8.setX(width/3);
//        imageView8.setY(width/3);
//            getOriginalBitmap(tourSpotList.get(page+7).getImages(), imageView8);
//            imageView8.setOnClickListener( v -> {
//
//            });
//            ImageView imageView9 = linearLayout.findViewById(R.id.image9);
//        imageView9.setX(width/3);
//        imageView9.setY(width/3);
//            getOriginalBitmap(tourSpotList.get(page+8).getImages(), imageView9);
//            imageView9.setOnClickListener( v -> {
//
//            });
//
//            Log.e("linearLayoutTop", "start add !! ");
//            linearLayoutTop.addView(linearLayout);
//            Log.e("linearLayoutTop", "start end !! ");




//            ImageView imageView = new ImageView(getBaseContext());
//            imageView.setId(i);
//            getOriginalBitmap(tourSpotList.get(i).getImages(), imageView);
//
//            imageView.setOnClickListener( v -> {
//                Log.e("select image", "id : " + imageView.getId() + ", url : " + tourSpotList.get(imageView.getId()).getImages());
//            });
//
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            imageViewList.add(imageView);

//            GridLayout.LayoutParams params = new GridLayout.LayoutParams(gridLayout.getLayoutParams());
//
//            Log.e("gird", "i%3 : " + i%3 + ", i/3 : " + i/3);
//            GridLayout.Spec rowSpec;
//            GridLayout.Spec columnSpec;
////            if((page-i) == 2 || (page-i) == 7) {
////                rowSpec = GridLayout.spec(i/3, 3); //   btn
////                columnSpec = GridLayout.spec(i%3, 3);//   btn
////                params = new GridLayout.LayoutParams(rowSpec, columnSpec);
//////                params.width = (gridLayout.getWidth() / 3 )* 2; //  btn
////                params.width = (gridLayout.getWidth()  ); //  btn
////                params.height = params.width;//  btn
////                params.setMargins(5, 0, 5, 10);//  btn
////                params.setGravity(Gravity.FILL);//  btn  （  ）
////            } else {
//                rowSpec = GridLayout.spec(i / 3); //   btn
//                columnSpec = GridLayout.spec(i % 3);//   btn
//                params = new GridLayout.LayoutParams(rowSpec, columnSpec);
//                params.width = gridLayout.getWidth() / 3; //  btn
//                params.height = params.width;//  btn
//                params.setMargins(5, 0, 5, 10);//  btn
//                params.setGravity(Gravity.LEFT);//  btn  （  ）
////            }
//
////            gridLayout.setLayoutParams(params);
//            gridLayout.addView(imageView, params);

//            gridLayout.addView(imageView, layoutParams);
//        }
        page += 9;
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