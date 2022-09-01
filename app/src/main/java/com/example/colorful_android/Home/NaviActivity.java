//package com.example.colorful_android.Home;
//
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.colorful_android.Fragment.ColorFragment;
//import com.example.colorful_android.Fragment.HomeFragment;
//import com.example.colorful_android.Fragment.MypageFragment;
//import com.example.colorful_android.Fragment.NaviActivity;
//import com.example.colorful_android.Fragment.SearchFragment;
//import com.example.colorful_android.R;
//import com.example.colorful_android.databinding.ActivityNaviBinding;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//
//
//public class NaviActivity extends AppCompatActivity {
//    private BottomNavigationView bottomNavigationView;
//    private ActivityNaviBinding binding;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//        bottomNavigationView = findViewById(R.id.bottomNavigationView);
//
//
//        HomeFragment homeFragment = new HomeFragment();
//
//        MypageFragment mypageFragment = new MypageFragment();
//        ColorFragment colorFragment = new ColorFragment();
//        SearchFragment searchFragment = new SearchFragment();
//
//        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,homeFragment).commitAllowingStateLoss();
//
////        binding = ActivityNaviBinding.inflate(layoutInflater)
////        val view = binding.root
////        setContentView(view)
////
////        //맨 처음 보여줄 프래그먼트 설정
////        setFragment(TAG_HOME, HomeFragment())
//
//        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                switch (menuItem.getItemId()){
//                    //menu_bottom.xml에서 지정해줬던 아이디 값을 받아와서 각 아이디값마다 다른 이벤트를 발생시킵니다.
//                    case R.id.tab1:{
//                        getSupportFragmentManager().beginTransaction() .replace(R.id.main_layout,fragment_home).commitAllowingStateLoss();
//                        return true;
//                    }
//
//
//                    case R.id.tab2:{
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.main_layout,fragment2).commitAllowingStateLoss();
//                        return true;
//                    }
//
//
//                    case R.id.tab3:{
//                        getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.main_layout,fragment3).commitAllowingStateLoss();
//                        return true;
//                    }
//
//
//
//                    default: return false;
//
//
//                }
//    }
//
//
//
////        setFragment(TAG_HOME, HomeFragment())
////
////        binding.navigationView.setOnItemSelectedListener { item ->
////        when(item.itemId) {
////        R.id.calenderFragment -> setFragment(TAG_CALENDER, CalenderFragment())
////        R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
////        R.id.myPageFragment-> setFragment(TAG_MY_PAGE, MyPageFragment())
////        }
////        true
////        }
//}
