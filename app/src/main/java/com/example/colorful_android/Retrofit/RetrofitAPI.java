package com.example.colorful_android.Retrofit;

import com.example.colorful_android.DTO.PersonalColorTestDTO;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST(Constatnts_url.PERSONAL_COLOR_TEST_URL)
    Call<String> getPersonalColor(@Body PersonalColorTestDTO personalColorTestDTO); //이건 바디 요청시 사용하는거

    //@FormUrlEncoded
    //@POST("/auth/overlapChecker")
    //Call<String> postOverlapCheck(@Field("phone") String phoneNum, @Field("message") String message); //이건 요청시 사용하는거 (*데이터를 보낼때)
}
