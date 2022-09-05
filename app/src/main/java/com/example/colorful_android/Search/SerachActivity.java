package com.example.colorful_android.Search;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.Color.ColorActivity;
import com.example.colorful_android.MainActivity;
import com.example.colorful_android.Mypage.MypageActivity;
import com.example.colorful_android.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SerachActivity extends AppCompatActivity {

    private String TAG_HOME = "home_fragment";
    private String TAG_SEARCH = "search_fragment";
    private String TAG_COLOR = "color_fragment";
    private String TAG_MYPAGE = "mypage_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_search_page);

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
    }

    public void selected_navi(String TAG) {
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
        }
        else if(TAG == TAG_MYPAGE){
            intent = new Intent(getApplicationContext(), MypageActivity.class);

        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 1줄 추가함
        getApplicationContext().startActivity(intent);
        overridePendingTransition(0, 0); //애니메이션 없애기

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0); //애니메이션 없애기
    }
}
