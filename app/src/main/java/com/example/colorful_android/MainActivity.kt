package com.example.colorful_android

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.colorful_android.TestColor.TestMainActivity

class MainActivity : AppCompatActivity() {


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


    }

}
