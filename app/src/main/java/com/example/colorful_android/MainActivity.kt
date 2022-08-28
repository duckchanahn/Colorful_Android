package com.example.colorful_android

import android.content.AsyncQueryHandler
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.toolbox.ImageLoader
import com.example.colorful_android.TestColor.GetImage_url
import com.example.colorful_android.TestColor.TestMainActivity
import com.example.colorful_android.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_mypage_pick.*
import kotlinx.android.synthetic.main.home_bottom_dialog.*
import kotlinx.android.synthetic.main.home_bottom_dialog.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.async
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class MainActivity : AppCompatActivity() {

    val IMAGE_URL = "http://tong.visitkorea.or.kr/cms/resource/58/1902758_image2_1.jpg"
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /* 버튼 눌러 Intent 이동하기
            val button : Button = findViewById(R.id.id);
            button.setOnClickListener {
                startActivity(Intent(this, newActivity::class.java))
            }
         */

//        val user : User = User.getInstance();

        val startTestButton : Button = findViewById(R.id.start_test); // permission 확인하기
        startTestButton.setOnClickListener {
            startActivity(Intent(this, TestMainActivity::class.java))

        }

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
        setContentView(R.layout.home_bottom_dialog)
        var sheetBehavior = BottomSheetBehavior.from(view.home_bottom_dialog)
        sheetBehavior.addBottomSheetCallback(object  : BottomSheetBehavior.BottomSheetCallback(){
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {true
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
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
