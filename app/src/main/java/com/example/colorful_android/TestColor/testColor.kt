package com.example.colorful_android.TestColor

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat.startActivityForResult

class testColor {


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