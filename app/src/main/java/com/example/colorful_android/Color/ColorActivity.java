package com.example.colorful_android.Color;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.colorful_android.DTO.Customer;
import com.example.colorful_android.DTO.Palette;
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

    private ImageView menu;

    private List<TourCardView> cardList;

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

        this.menu = findViewById(R.id.btnCancel);
//        this.menu.

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, tourList) ;

        // listview 생성 및 adapter 지정.
//        this.listView.setAdapter(adapter) ;

        this.menu.setOnClickListener( v -> {
            Log.e("colorActivity menu" , "click : " + v);
            PopupMenu popup = new PopupMenu(this , menu);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.dialog_choose_edit, popup.getMenu());
            popup.show();
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    Log.e("color activity menu", "start : " + item.getItemId());
                    switch (item.getItemId()) {
                        case R.id.palette_create:
                            Log.e("color activity menu", "create palette");
                            Intent intentPopup = new Intent(getBaseContext(), PopupCreatePaletteName.class);
                            startactivityCreatePaletteName.launch(intentPopup);

                            return true;
                        case R.id.palette_delete:
                            for (TourCardView c : cardList) {
                                Log.e("delete palette", c.getClass().toString());
                                c.getDeleteButton().setVisibility(View.VISIBLE);
                            }
                            return true;
                        case R.id.palette_update:
                            Log.e("color activity menu", "update palette");
                            return true;
                    }
                    return false;
                }
            });
        });

        BottomNavigationView navigation = findViewById(R.id.nav_main);
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
                    menuItem.setIcon(R.drawable.ic_nav_search);
                    break;
                case R.id.colorFragment:
//                    Log.e("nav", "colorFragment"  + menuItem.getItemId());
                    menuItem.setIcon(R.drawable.ic_nav_color_selected);
                    menuItem.setChecked(true);
                    break;
                case R.id.mypageFragment:
//                    Log.e("nav", "mypageFragment"  + menuItem.getItemId());
                    menuItem.setIcon(R.drawable.ic_nav_mypage);
                    break;
            }
        }

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


    public void selected_navi(String TAG) {

        Intent intent = null;

        if(TAG == TAG_HOME){
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else if(TAG == TAG_SEARCH){
            intent = new Intent(getApplicationContext(), SerachActivity.class);
        }
        else if(TAG == TAG_COLOR){
            return;
        }
        else if(TAG == TAG_MYPAGE){
            intent = new Intent(getApplicationContext(), MypageActivity.class);

        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
        getApplicationContext().startActivity(intent);
        overridePendingTransition(0, 0); //애니메이션 없애기
        finish();
    }

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

                cardList = new ArrayList<>();
                tourList = response.body();
                menuRefresh();

            }

            @Override
            public void onFailure(Call<List<Palette>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }


    /////////////////////////////////////////////////////////////////
    // 팔레트 생성
    /////////////////////////////////////////////////////////////////


    private Palette newPalette;
    ActivityResultLauncher<Intent> startactivityCreatePaletteName = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    newPalette = new Palette();
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData().getBooleanExtra("result", true)) {
                            newPalette.setName(result.getData().getStringExtra("paletteName"));
//                            Log.e("newPalette", result.getData().getStringExtra("paletteName"));
                            Intent intentPopup = new Intent(getBaseContext(), PopupCreatePaletteDue.class);
                            startactivityCreatePaletteDue.launch(intentPopup);
                        }
                    }

                }
            });

    ActivityResultLauncher<Intent> startactivityCreatePaletteDue = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
//                    Palette palette = new Palette();
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData().getBooleanExtra("result", true)) {
                            newPalette.setDue(result.getData().getStringExtra("paletteDue"));
                            newPalette.setCustomerId(Customer.getInstance().getCustomerId());
                            excute_createPalette();
                        }
                    }

                }
            });

    private void excute_createPalette() {
        Call<Palette> call = MyRetrofit.getApiService().addPalette(newPalette);
        call.enqueue(new Callback<Palette>() {
            @Override
            public void onResponse(Call<Palette> call, Response<Palette> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());
                tourList.add(response.body());

                TourCardView cardView = new TourCardView(getApplicationContext(), response.body());
                cardView.getCard().setId(response.body().getPaletteId());

                cardView.getDeleteButton().setOnClickListener( v -> {
//                Intent intent = new Intent(getBaseContext(), PopupDeletePalette.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
//                    intent.putExtra("cardView", cardView);
//                startActivityForResult(intent, 1);
                    selectCardId = cardView.getCard().getId();
                    Intent intentPopup = new Intent(getBaseContext(), PopupDeletePalette.class);
                    startactivityDeletePalette.launch(intentPopup);
                });

                ConstraintLayout card = cardView.getCard();
                card.findViewById(R.id.card_img).setOnClickListener(v -> {
                    Intent intent = new Intent(getApplicationContext(), ColorDetailActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
                    intent.putExtra("palette", response.body());
                    getApplicationContext().startActivity(intent);
                });
                cardList.add(cardView);
//                    listView.notifyAll();
                listView.addView(card);
            }

            @Override
            public void onFailure(Call<Palette> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    /////////////////////////////////////////////////////////////////
    // 팔레트 지우기
    /////////////////////////////////////////////////////////////////

    ActivityResultLauncher<Intent> startactivityDeletePalette = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        if (result.getData().getBooleanExtra("result", true)) {
                            menuDelete();
                            deletePaletteEnd();
                        } else {
                            deletePaletteEnd();
                        }
                    }

                }
            });


    private int selectCardId;
    private void menuRefresh() {
//        int id = 1;
        for(Palette palette : tourList) {
            TourCardView cardView = new TourCardView(getApplicationContext(), palette);
            cardView.getCard().setId(palette.getPaletteId());
//            id++;

            cardView.getDeleteButton().setOnClickListener( v -> {
//                Intent intent = new Intent(getBaseContext(), PopupDeletePalette.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
//                    intent.putExtra("cardView", cardView);
//                startActivityForResult(intent, 1);
                selectCardId = cardView.getCard().getId();
                Intent intentPopup = new Intent(this, PopupDeletePalette.class);
                startactivityDeletePalette.launch(intentPopup);
            });

            ConstraintLayout card = cardView.getCard();
            card.findViewById(R.id.card_img).setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), ColorDetailActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
                intent.putExtra("palette", palette);
                getApplicationContext().startActivity(intent);
            });
            cardList.add(cardView);
//                    listView.notifyAll();
            listView.addView(card);
        }
    }

        private void menuDelete() {
            for(TourCardView cv : cardList) {
                if(cv.getCard().getId() == selectCardId) {
                    listView.removeView(cv.getCard());
                    excute_deletePalette(selectCardId);
//                        cardList.remove(cardView);
                }
            }
        }

        private void deletePaletteEnd() {
            for (TourCardView card : cardList) {
                card.getDeleteButton().setVisibility(View.GONE);
            }
        }

    private void excute_deletePalette(int selectCardId) {
        Call<Integer> call = MyRetrofit.getApiService().deletePalette(selectCardId);
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);

        finish();
        overridePendingTransition(0, 0); //애니메이션 없애기

    }
}