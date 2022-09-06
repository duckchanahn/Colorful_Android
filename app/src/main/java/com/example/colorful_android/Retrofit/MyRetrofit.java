package com.example.colorful_android.Retrofit;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public class MyRetrofit  extends Application {
    private static String result;

//    private jsonPlaceHolderApi jsonPlaceHolderApi;

    public static RetrofitAPI getApiService(){return create().create(RetrofitAPI.class);}
    
//    private String result;

    public static Retrofit create() {
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
//                .baseUrl(Constatnts_url.BASE_URL_EC2)
                .baseUrl(Constatnts_url.BASE_URL_MYDEVICE_DH)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}