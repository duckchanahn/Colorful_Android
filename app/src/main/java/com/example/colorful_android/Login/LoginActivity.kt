package com.example.colorful_android.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.colorful_android.Model.User
import com.example.colorful_android.R
import com.gun0912.tedpermission.provider.TedPermissionProvider.context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btn_kakako : Button = findViewById(R.id.btn_kakako)
        val btn_naver : Button = findViewById(R.id.btn_naver)
        val btn_google : Button = findViewById(R.id.btn_google)

        btn_kakako.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e("kakaoLoginTry", "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.i("kakaoLoginTry", "카카오톡으로 로그인 성공 ${token.accessToken}")
                        getUserInfoKakao()
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                finish()
            }
        }

        btn_naver.setOnClickListener {
            /**
             * OAuthLoginCallback을 authenticate() 메서드 호출 시 파라미터로 전달하거나 NidOAuthLoginButton 객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
             */
            val oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {
                    // 네이버 로그인 인증이 성공했을 때 수행할 코드 추가
                    Log.i("naverLoginTry", "access token : " + NaverIdLoginSDK.getAccessToken())
                    Log.i("naverLoginTry", "refresh token : " + NaverIdLoginSDK.getRefreshToken())

                    NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse> {
                        override fun onSuccess(response: NidProfileResponse) {
                            Log.e("navergetUserInfo","response" + "$response")

                        }
                        override fun onFailure(httpStatus: Int, message: String) {
                            val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                            val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                            Log.i("navergetUserInfo","errorCode:$errorCode, errorDesc:$errorDescription")
                        }
                        override fun onError(errorCode: Int, message: String) {
                            onFailure(errorCode, message)
                        }
                    })
                }
                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    Log.i("naverLoginTry","errorCode:$errorCode, errorDesc:$errorDescription")
                }
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }

            NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
        }
    }

    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Log.e("kakaoLoginTry", "카카오계정으로 로그인 실패", error)
        } else if (token != null) {
            Log.i("kakaoLoginTry", "카카오계정으로 로그인 성공 ${token.accessToken}")

        }
    }


    fun getUserInfoKakao() {
        // 사용자 정보 요청 (기본)
        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.e("kakaoLoginGetInfo", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("kakaoLoginGetInfo", "사용자 정보 요청 성공" +
                        "\n회원번호: ${user.id}" + "\n닉네임: ${user.properties?.get("nickname")}"  + "\n토큰: ${user.properties?.get("nickname")}" )

            }
        }
    }
}