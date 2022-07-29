package com.example.colorful_android

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.colorful_android.Model.User
import com.example.colorful_android.TestColor.TestColorActivity
import com.example.colorful_android.TestColor.TestColorActivity_java

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
            startActivity(Intent(this, TestColorActivity_java::class.java))
        }

    }

}