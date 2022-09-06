package com.example.colorful_android.Home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colorful_android.Color.ColorDetailActivity;
import com.example.colorful_android.Color.PopupCreatePaletteDue;
import com.example.colorful_android.Color.PopupDeletePalette;
import com.example.colorful_android.Color.TourCardView;
import com.example.colorful_android.DTO.Customer;
import com.example.colorful_android.DTO.Palette;
import com.example.colorful_android.DTO.Star;
import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.R;
import com.example.colorful_android.Retrofit.MyRetrofit;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;
import java.util.List;

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

    private boolean start;

    private Star star;

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


        app.offsetTopAndBottom(500);
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        int width = (int) (dm.heightPixels); // Display 사이즈의 90%
        Log.e("app", "width: " + width);

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
                    finish();
                }

                if (Math.abs(verticalOffset) > getApplicationContext().getResources().getDisplayMetrics().heightPixels) {
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
