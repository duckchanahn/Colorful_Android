package com.colorful.colorful_android.Home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.colorful.colorful_android.DTO.Customer;
import com.colorful.colorful_android.DTO.Star;
import com.colorful.colorful_android.DTO.TourSpot;
import com.colorful.colorful_android.MainActivity;
import com.colorful.colorful_android.MapView.BaseMapview;
import com.colorful.colorful_android.Mypage.MyPageTourSpotListActivity;
import com.colorful.colorful_android.R;
import com.colorful.colorful_android.Retrofit.MyRetrofit;
import com.google.android.material.appbar.AppBarLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeMainDialog extends AppCompatActivity {

    private TextView tourName;
    private TextView address;
    private TextView telephone;
    private TextView homepage;
    private TextView hours;
    private TextView park;
    private TextView content;
    private Button addPalette;
    private ImageButton prevButton;
    private ImageView addStar;
    private ImageView imgBg;

    private RelativeLayout mapview_layout;

    private boolean start;

    private Star star;

    private String prevPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_bottom_dialog);

        AppBarLayout app = findViewById(R.id.appbar);
        prevButton = findViewById(R.id.prev_button);
        prevButton.setVisibility(View.INVISIBLE);
        LinearLayout toolbar = findViewById(R.id.toolbar);
        int minHeight = findViewById(R.id.tour_name).getHeight();
        Log.e("minHeight", "minHeight : " + minHeight);

        toolbar.setMinimumHeight(minHeight);
//        CollapsingToolbarLayout cool = findViewById(R.id.collapsing_toolbar);
//
//        int minHeight = toolbar.getHeight();
//        toolbar.setMinimumHeight(minHeight);
//        cool.setMinimumHeight(minHeight);
//        app.setMinimumHeight(minHeight);
//        cool.offsetTopAndBottom(-500);


//        app.setBottom(-500);
//        app.setGravity(Gravity.CENTER);
//
//        app.offsetTopAndBottom(500);
//        findViewById(R.id.collapsing_toolbar).offsetTopAndBottom(500);
//        findViewById(R.id.toppanel).offsetTopAndBottom(500);
//        findViewById(R.id.collapsing_toolbar).offsetTopAndBottom(500);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.heightPixels); // Display 사이즈의 90%
        Log.e("app", "width: " + width);


//        app.setY(-(dm.heightPixels/2));
//        app.setMinimumHeight(60);
//        findViewById(R.id.dialog_home_main).setY(-(dm.heightPixels/2));

//        app.setScrollY(-(dm.heightPixels/2));

        start = false;
        app.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                Log.d("app", "recycler_view current offset: " + verticalOffset);
                if (verticalOffset < 0) {
//                    Log.e("app", "start!!");
                    start = true;
                }

                if (verticalOffset == 0 && start) {
                    Log.e("app", "bottom!!");
                    prevLayout();
                }

                if (Math.abs(verticalOffset) > getApplicationContext().getResources().getDisplayMetrics().heightPixels/2) {
                    Log.e("app", "top!!");
                    prevButton.setVisibility(View.VISIBLE);
                } else {
                    prevButton.setVisibility(View.INVISIBLE);
                }
            }
        });
        ;

        Intent intent = getIntent();
        TourSpot tourSpot = (TourSpot) intent.getSerializableExtra("TourSpot");
        prevPage = intent.getStringExtra("prevPage");

        Log.e("dialog", String.valueOf(tourSpot));

//        ConstraintLayout homeDial = findViewById(R.id.dialog_home_main);

        this.tourName = findViewById(R.id.tour_name);
        this.tourName.setText(tourSpot.getName());
        this.address = findViewById(R.id.address);
        this.address.setText(tourSpot.getAddress());
        this.telephone = findViewById(R.id.telephone);
        this.telephone.setText(tourSpot.getTelephone());
        this.homepage = findViewById(R.id.homepage);
        this.homepage.setText(tourSpot.getHomepage());
        this.imgBg = findViewById(R.id.bg_img);
        this.getOriginalBitmap(tourSpot.getImages(), imgBg);
//        ClipDrawable drawable = (ClipDrawable) imgBg.getDrawable();
//        drawable.setLevel(drawable.getLevel() + 5000);

        ConstraintLayout hoursTitle = findViewById(R.id.hours_title);
        this.hours = findViewById(R.id.hours);
        if (tourSpot.getHours() == null) {
            hoursTitle.setVisibility(View.GONE);
            this.hours.setVisibility(View.GONE);
        } else {
            this.hours.setText(tourSpot.getHours());
        }

        ConstraintLayout parkingTitle = findViewById(R.id.parking_title);
        this.park = findViewById(R.id.park);
        if (tourSpot.getParking() == null) {
            parkingTitle.setVisibility(View.GONE);
            this.park.setVisibility(View.GONE);
        } else {
            this.park.setText(tourSpot.getParking());
        }

        this.content = findViewById(R.id.content);
        this.content.setText(tourSpot.getContent());


        this.addPalette = findViewById(R.id.add_palette);
        this.addPalette.setOnClickListener(v -> {
            Intent intentPopup = new Intent(getBaseContext(), AddTourSpotInPalette.class);
            intentPopup.putExtra("TourSpot", tourSpot);
            startActivity(intentPopup);
        });

        this.addStar = findViewById(R.id.add_star);
        excute_starCheck(tourSpot.getTourSpotId());
        this.addStar.setOnClickListener(v -> {
            Log.e("star", "start!! bool : " + result);
            if (result) {
                excute_deleteStar(star.getStarId());
            } else {
                excute_addStar(tourSpot.getTourSpotId());
            }
        });

//        MapView mapView = new MapView(this);
//        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(Double.parseDouble(tourSpot.getPosition_x()), Double.parseDouble(tourSpot.getPosition_y())), true);
//        mapview_layout =  findViewById(R.id.map_view);
//        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
//        mapViewContainer.addView(mapView);

        Button moveMapview = findViewById(R.id.view_More);
        moveMapview.setOnClickListener( v -> {
            Intent intent1 = new Intent(this, BaseMapview.class);
            intent1.putExtra("tourSpot", tourSpot);
            this.startActivity(intent1);
        });

    }

    @Override
    public void onBackPressed() {
        prevLayout();
    }

    private void prevLayout() {
        if(prevPage.equals("MainActivity")) {
            this.startActivity(new Intent(this, MainActivity.class));
            finish();
        } else if(prevPage.equals("ColorDetailActivity")) {
            finish();
        } else if(prevPage.equals("SearchActivity")) {
            finish();
        } else if(prevPage.equals("MyPageTourSpotListActivity")) {
            this.startActivity(new Intent(this, MyPageTourSpotListActivity.class));
            finish();
        }
    }


    /////////////////////////////////////////////////////////////////
    // 담기
    /////////////////////////////////////////////////////////////////




    /////////////////////////////////////////////////////////////////
    // url to bitmap
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

//                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()/2);

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
//            imageView.setImageBitmap(bitmap);
            BitmapDrawable ob = new BitmapDrawable(getResources(), bitmap);
            findViewById(R.id.main_content).setBackground(ob);
//            findViewById(R.id.main_content).scale

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /////////////////////////////////////////////////////////////////
    // 찜하기 + 찜 체크 + 찜 삭제
    /////////////////////////////////////////////////////////////////

    private void excute_addStar(int tourSpotId) {
        Call<Star> call = MyRetrofit.getApiService().starAdd(Customer.getInstance().getCustomerId(), tourSpotId);
        call.enqueue(new Callback<Star>() {
            @Override
            public void onResponse(Call<Star> call, Response<Star> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());

                ImageButton starButton = findViewById(R.id.add_star);
                starButton.setBackground(getResources().getDrawable(R.drawable.btn_heart_fill, null));
                result = true;
//                starButton.setBackground(R.drawable.heart);
            }

            @Override
            public void onFailure(Call<Star> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    private void excute_deleteStar(int starId) {
        Call<Integer> call = MyRetrofit.getApiService().starDelete(starId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());

                ImageButton starButton = findViewById(R.id.add_star);
                starButton.setBackground(getResources().getDrawable(R.drawable.btn_heart_black, null));
                result = false;
//                starButton.setBackground(R.drawable.heart);
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    boolean result;
    private void excute_starCheck(int tourSpotId) {
        result = false;

        Call<Star> call = MyRetrofit.getApiService().starCheck(Customer.getInstance().getCustomerId(), tourSpotId);
        call.enqueue(new Callback<Star>() {
            @Override
            public void onResponse(Call<Star> call, Response<Star> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "excute_starCheck, error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());

                if(response.body().getStarId() != -1) {
                    result = true;
                    star = response.body();
                    ImageButton starButton = findViewById(R.id.add_star);
                    starButton.setBackground(getResources().getDrawable(R.drawable.btn_heart_fill, null));
                }

            }

            @Override
            public void onFailure(Call<Star> call, Throwable t) {
                Toast.makeText(getBaseContext(), "excute_starCheck, D연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });

    }


//        Window window = this.getWindow();
//        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


//        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
//        int width = (int) (dm.widthPixels); // Display 사이즈의 90%
//        int height = (int) (dm.heightPixels); // Display 사이즈의 90%
//        getWindow().getAttributes().width = width;
//        getWindow().getAttributes().height = height;

    }




    /////////////////////////////////////////////////////////////////
    // 팔레트 생성
    /////////////////////////////////////////////////////////////////


//    private Palette newPalette;
//    ActivityResultLauncher<Intent> startactivityCreatePaletteName = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    newPalette = new Palette();
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        if (result.getData().getBooleanExtra("result", true)) {
//                            newPalette.setName(result.getData().getStringExtra("paletteName"));
////                            Log.e("newPalette", result.getData().getStringExtra("paletteName"));
//                            Intent intentPopup = new Intent(getBaseContext(), PopupCreatePaletteDue.class);
//                            startactivityCreatePaletteDue.launch(intentPopup);
//                        }
//                    }
//
//                }
//            });
//
//    ActivityResultLauncher<Intent> startactivityCreatePaletteDue = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
////                    Palette palette = new Palette();
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        if (result.getData().getBooleanExtra("result", true)) {
//                            newPalette.setDue(result.getData().getStringExtra("paletteDue"));
//                            newPalette.setCustomerId(Customer.getInstance().getCustomerId());
//                            excute_createPalette();
//                        }
//                    }
//
//                }
//            });
//
//    private void excute_createPalette() {
//        Call<Palette> call = MyRetrofit.getApiService().addPalette(newPalette);
//        call.enqueue(new Callback<Palette>() {
//            @Override
//            public void onResponse(Call<Palette> call, Response<Palette> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
//                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
//                    return;
//                }
//                Log.d("연결이 성공적 : ", response.body().toString());
////                tourList.add(response.body());
//
//                TourCardView cardView = new TourCardView(getApplicationContext(), response.body());
//                cardView.getCard().setId(response.body().getPaletteId());
//
//                cardView.getDeleteButton().setOnClickListener( v -> {
////                Intent intent = new Intent(getBaseContext(), PopupDeletePalette.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
////                    intent.putExtra("cardView", cardView);
////                startActivityForResult(intent, 1);
//                    selectCardId = cardView.getCard().getId();
//                    Intent intentPopup = new Intent(getBaseContext(), PopupDeletePalette.class);
//                    startactivityDeletePalette.launch(intentPopup);
//                });
//
//                ConstraintLayout card = cardView.getCard();
//                card.findViewById(R.id.card_img).setOnClickListener(v -> {
//                    Intent intent = new Intent(getApplicationContext(), ColorDetailActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
//                    intent.putExtra("palette", response.body());
//                    getApplicationContext().startActivity(intent);
//                });
//                cardList.add(cardView);
////                    listView.notifyAll();
//                listView.addView(card);
//            }
//
//            @Override
//            public void onFailure(Call<Palette> call, Throwable t) {
//                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
//                Log.e("연결실패", t.getMessage());
//            }
//        });
//    }
//
//    ActivityResultLauncher<Intent> startactivityDeletePalette = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            new ActivityResultCallback<ActivityResult>() {
//                @Override
//                public void onActivityResult(ActivityResult result) {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//                        if (result.getData().getBooleanExtra("result", true)) {
//                            menuDelete();
//                            deletePaletteEnd();
//                        } else {
//                            deletePaletteEnd();
//                        }
//                    }
//
//                }
//            });
//
//
//
//    private int selectCardId;
//    private void menuRefresh() {
////        int id = 1;
//        for(Palette palette : tourList) {
//            TourCardView cardView = new TourCardView(getApplicationContext(), palette);
//            cardView.getCard().setId(palette.getPaletteId());
////            id++;
//
//            cardView.getDeleteButton().setOnClickListener( v -> {
////                Intent intent = new Intent(getBaseContext(), PopupDeletePalette.class);
////                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
////                    intent.putExtra("cardView", cardView);
////                startActivityForResult(intent, 1);
//                selectCardId = cardView.getCard().getId();
//                Intent intentPopup = new Intent(this, PopupDeletePalette.class);
//                startactivityDeletePalette.launch(intentPopup);
//            });
//
//            ConstraintLayout card = cardView.getCard();
//            card.findViewById(R.id.card_img).setOnClickListener(v -> {
//                Intent intent = new Intent(getApplicationContext(), ColorDetailActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
//                intent.putExtra("palette", palette);
//                getApplicationContext().startActivity(intent);
//            });
//            cardList.add(cardView);
////                    listView.notifyAll();
//            listView.addView(card);
//        }
//    }
//
//    private void menuDelete() {
//        for(TourCardView cv : cardList) {
//            if(cv.getCard().getId() == selectCardId) {
//                listView.removeView(cv.getCard());
//                excute_deletePalette(selectCardId);
////                        cardList.remove(cardView);
//            }
//        }
//    }
//
//    private void deletePaletteEnd() {
//        for (TourCardView card : cardList) {
//            card.getDeleteButton().setVisibility(View.GONE);
//        }
//    }
//
//    private void excute_deletePalette(int selectCardId) {
//        Call<Integer> call = MyRetrofit.getApiService().deletePalette(selectCardId);
//        call.enqueue(new Callback<Integer>() {
//            @Override
//            public void onResponse(Call<Integer> call, Response<Integer> response) {
//                if (!response.isSuccessful()) {
//                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
//                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
//                    return;
//                }
//                Log.d("연결이 성공적 : ", response.body().toString());
//
//            }
//
//            @Override
//            public void onFailure(Call<Integer> call, Throwable t) {
//                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
//                Log.e("연결실패", t.getMessage());
//            }
//        });
//    }

//}