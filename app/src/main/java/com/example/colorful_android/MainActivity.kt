package com.example.colorful_android

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.colorful_android.Login.LoginActivity
import com.example.colorful_android.Model.User
import com.example.colorful_android.TestColor.TestMainActivity
import com.kakao.sdk.auth.TokenManagerProvider
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        if(TokenManagerProvider.instance.manager.getToken() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            Log.e("kakao", "kakao auto login complete")
        }
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



    }

}
