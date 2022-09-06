package com.example.colorful_android.Retrofit;

import com.example.colorful_android.DTO.PersonalColorTestDTO;
import com.example.colorful_android.DTO.PsycologicaltestAnswerDTO;
import com.example.colorful_android.DTO.PsycologicaltestQuestionDTO;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @POST(Constatnts_url.PERSONAL_COLOR_TEST_URL)
    Call<String> getPersonalColor(@Body PersonalColorTestDTO personalColorTestDTO); //이건 바디 요청시 사용하는거

    @GET(Constatnts_url.PSYCHOLOGICAL_COLOR_TEST_QUESTION_URL)
    Call<List<PsycologicaltestQuestionDTO>> getPsycologicalTestQuestion(); //이건 바디 요청시 사용하는거

    @GET(Constatnts_url.PSYCHOLOGICAL_COLOR_TEST_ANSWER_URL)
    Call<List<PsycologicaltestAnswerDTO>> getPsycologicalTestAnswer(); //이건 바디 요청시 사용하는거

    @POST(Constatnts_url.PSYCHOLOGICAL_COLOR_TEST_UPDATE_RESULT_URL)
    Call<Integer> updatePsycologicalResult(@Query("customerId")int customerId, @Query("result")String result);

    //@FormUrlEncoded
    //@POST("/auth/overlapChecker")
    //Call<String> postOverlapCheck(@Field("phone") String phoneNum, @Field("message") String message); //이건 요청시 사용하는거 (*데이터를 보낼때)
}
