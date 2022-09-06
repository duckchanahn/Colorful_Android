package com.example.colorful_android

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.colorful_android.Login.LoginActivity
//import com.example.colorful_android.Model.User
import com.example.colorful_android.TestColor.TestMainActivity
//import com.kakao.sdk.auth.TokenManagerProvider
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_home_main.view.*
//import com.kakao.sdk.common.KakaoSdk
//import com.navercorp.nid.NaverIdLoginSDK
import kotlinx.android.synthetic.main.activity_mypage_pick.*
import kotlinx.android.synthetic.main.color_palette_list.*
import kotlinx.android.synthetic.main.dialog_home_main.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import java.net.URL

class MainActivity : AppCompatActivity() {

    val IMAGE_URL = "http://tong.visitkorea.or.kr/cms/resource/58/1902758_image2_1.jpg"
    val coroutineScope = CoroutineScope(Dispatchers.Main)


    //팔레트 리스트뷰
    var TourList = arrayListOf<TourInfo>(
        TourInfo(R.drawable.card_blue_re, "강원도 혼행","22.8.21-22.8.22","3개"),
        TourInfo(R.drawable.card_green, "예림이랑 여수","22.9.10-22.9.12","2개"),
        TourInfo(R.drawable.card_pink, "부산 가족여행","22.10.7-22.10.10","4개")
    )

    //팔레트 디테일 리스트뷰
    var DetailTourList = arrayListOf<TourInfoDetail>(
        TourInfoDetail(R.drawable.ex_detail_img, "감성공작소", "강원도 삼척시\n 두줄까지"),
        TourInfoDetail(R.drawable.ex_detail_img, "낙산 해수욕장", "강원도 양양군\n 두줄까지"),
        TourInfoDetail(R.drawable.ex_detail_img, "안반데기 마을", "강원도 강릉시\n 두줄까지")
    )

    //팔레트 추가 리스트뷰
    var AddTourList = arrayListOf<TourInfoAdd>(
        TourInfoAdd("강원도 혼행","22.8.21-22.8.22","3개"),
        TourInfoAdd("예림이랑 여수","22.9.10-22.9.12","2개"),
        TourInfoAdd("부산 가족여행","22.10.7-22.10.10","4개")
    )



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        /*로그인
        if(TokenManagerProvider.instance.manager.getToken() == null) {
=======

        if(TokenManagerProvider.instance.manager.getToken() == null && NaverIdLoginSDK.getAccessToken() == null) {
>>>>>>> 8d436c9112b8542995c4c59e6fe3b85691e5ee15
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Log.e("kakao", "kakao login : " + TokenManagerProvider.instance.manager.getToken()?.refreshToken.toString())
            Log.e("naver", "naver token : " + NaverIdLoginSDK.getRefreshToken())
        }

         */
        setContentView(R.layout.activity_main)

        /* 버튼 눌러 Intent 이동하기
            val button : Button = findViewById(R.id.id);
            button.setOnClickListener {
                startActivity(Intent(this, newActivity::class.java))
            }
         */

        val startTestButton : Button = findViewById(R.id.start_test); // permission 확인하기
        startTestButton.setOnClickListener {
            startActivity(Intent(this, TestMainActivity::class.java))

        }



        //파레트 리스트뷰
        var Adapter = ListAdapter(this, TourList)
        lv.adapter = Adapter

        //파레트 디테일 리스트뷰
        var DetailAdapter = DetailListAdapter(this, DetailTourList)
        lv.adapter = DetailAdapter

        //파레트 디테일 리스트뷰
        var AddAdapter = AddListAdapter(this, AddTourList)
        lv.adapter = AddAdapter



        //Url 이미지 비트맵전환
        setContentView(R.layout.color_search_page)
        coroutineScope.launch {
            val originalDeferred = coroutineScope.async(Dispatchers.IO){
                getOriginalBitmap()
            }

            val originalBitmap = originalDeferred.await()
            loadImage(originalBitmap)
        }


        //bottom_dialog
        setContentView(R.layout.dialog_home_main)

        var sheetBehavior = BottomSheetBehavior.from(view.bottomsheet)
        sheetBehavior.addBottomSheetCallback(object  : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {true
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // home_detail_page 로 이동
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

        })


    }


    //Url 이미지 비트맵전환
    private fun getOriginalBitmap(): Bitmap =
        URL(IMAGE_URL).openStream().use {
            BitmapFactory.decodeStream(it)
        }

    private fun loadImage(bmp: Bitmap) {
        val imageView = findViewById<ImageView>(R.id.iv_image)
        imageView.setImageBitmap(bmp)
        imageView.visibility = View.VISIBLE
    }




}
