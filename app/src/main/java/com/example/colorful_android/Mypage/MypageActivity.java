package com.example.colorful_android.Mypage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.Color.ColorActivity;
import com.example.colorful_android.MainActivity;
import com.example.colorful_android.R;
import com.example.colorful_android.Search.SerachActivity;
import com.example.colorful_android.Setting.SettingActivity;
import com.example.colorful_android.TestColor.TestMainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.android.material.navigation.NavigationBarView;

public class MypageActivity extends AppCompatActivity {

    private String TAG_HOME = "home_fragment";
    private String TAG_SEARCH = "search_fragment";
    private String TAG_COLOR = "color_fragment";
    private String TAG_MYPAGE = "mypage_fragment";

    private Chip personalChip;
    private Chip psyChip;

    private ImageView star;
    private ImageView config;

    private TextView title;
    private TextView content;

    private ImageView imageView;
    private Button retryTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        initialize();

        star.setOnClickListener( v -> {
            this.startActivity(new Intent(this, MyPageTouSpotListActivity.class));
        });

        config.setOnClickListener( v -> {
            this.startActivity(new Intent(this, SettingActivity.class));
        });

        personalChip.setOnClickListener(v -> {
//            personalChip.setChecked(true);
            personalChip.setChecked(true);
            psyChip.setChecked(false);
//            imageView.setImageBitmap(null);
        });

        psyChip.setOnClickListener(v -> {
            personalChip.setChecked(false);
            psyChip.setChecked(true);
//            imageView.setImageBitmap(null);
        });

        retryTest.setOnClickListener(v -> {
            Intent intent = new Intent(this, TestMainActivity.class);
            this.startActivity(intent);
        });

    }

    private void initialize() {

        star = findViewById(R.id.btn_heart);
        config = findViewById(R.id.btn_setting);

        personalChip = findViewById(R.id.personal_chip);
        psyChip = findViewById(R.id.psy_chip);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        imageView = findViewById(R.id.imageView);
        retryTest = findViewById(R.id.retry_test);

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
                    menuItem.setIcon(R.drawable.ic_nav_color);
                    break;
                case R.id.mypageFragment:
//                    Log.e("nav", "mypageFragment"  + menuItem.getItemId());
                    menuItem.setIcon(R.drawable.ic_nav_mypage_selected);
                    menuItem.setChecked(true);
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
    }

    public void selected_navi(String TAG) {
        Log.e("mypage", TAG);
        Intent intent = null;

        if(TAG == TAG_HOME){
            intent = new Intent(getApplicationContext(), MainActivity.class);
        }
        else if(TAG == TAG_SEARCH){
            intent = new Intent(getApplicationContext(), SerachActivity.class);
            finish();
        }
        else if(TAG == TAG_COLOR){
            intent = new Intent(getApplicationContext(), ColorActivity.class);
            finish();
        }
        else if(TAG == TAG_MYPAGE){
//            intent = new Intent(getApplicationContext(), MypageActivity.class);
            return;

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
