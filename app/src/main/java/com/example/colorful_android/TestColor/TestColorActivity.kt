package com.example.colorful_android.TestColor

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.colorful_android.R

class TestColorActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_main_layout)

        System.out.println("!!")

        val move_personalTest_button : Button = findViewById(R.id.personal_color_test_button);
        move_personalTest_button.setOnClickListener {
            startActivity(Intent(this, PersonalTestSelectImageActivity_java::class.java))
//            setContentView(R.layout.personal_color_select_image_layout)
        }

//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            // 디바이스 내부 저장소 접근 권한을 얻었을 경우 이미지 업로드 화면으로 이동
//            startActivity(Intent(this, TestColorActivity::class.java))
//        } else {
//            // 디바이스 내부 저장소 접근 권한을 얻지 못했을 경우 토스트 출력
//            Toast.makeText(this, "디바이스 스토리지 읽기 권한이 없습니다.", Toast.LENGTH_LONG).show()
//        }
    }

    private fun startDefaultGalleryApp(activity : Activity) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val resultCode = 0
            if (resultCode == RESULT_OK) {
                
            }
        }
    }

    private fun registerForActivityResult(startActivityForResult: ActivityResultContracts.StartActivityForResult, function: () -> Unit) {

    }

}