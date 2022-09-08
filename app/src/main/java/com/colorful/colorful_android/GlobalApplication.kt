package com.colorful.colorful_android

import android.app.Application
import com.colorful.colorful_android.R
import com.kakao.sdk.common.KakaoSdk
import com.navercorp.nid.NaverIdLoginSDK

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this,getString(R.string.KAKAO_NATIVE_APP_KEY))
        NaverIdLoginSDK.initialize(this, "yQKn6g6D78V9Yv7SCBrC", "sL9_QBm2bH", "알로록달로록")
    }
}