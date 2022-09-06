package com.example.colorful_android.Retrofit;

import com.example.colorful_android.DTO.Customer;
import com.example.colorful_android.DTO.Palette;
import com.example.colorful_android.DTO.PersonalColorTestDTO;
import com.example.colorful_android.DTO.PsycologicaltestAnswerDTO;
import com.example.colorful_android.DTO.PsycologicaltestQuestionDTO;
import com.example.colorful_android.DTO.Star;
import com.example.colorful_android.DTO.TourSpot;
import com.example.colorful_android.DTO.TourSpotSummary;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

    // 테스트
    @POST(Constatnts_url.PERSONAL_COLOR_TEST_URL)
    Call<String> getPersonalColor(@Body PersonalColorTestDTO personalColorTestDTO); //이건 바디 요청시 사용하는거
    @GET(Constatnts_url.PSYCHOLOGICAL_COLOR_TEST_QUESTION_URL)
    Call<List<PsycologicaltestQuestionDTO>> getPsycologicalTestQuestion(); //이건 바디 요청시 사용하는거
    @GET(Constatnts_url.PSYCHOLOGICAL_COLOR_TEST_ANSWER_URL)
    Call<List<PsycologicaltestAnswerDTO>> getPsycologicalTestAnswer(); //이건 바디 요청시 사용하는거
    @POST(Constatnts_url.PSYCHOLOGICAL_COLOR_TEST_UPDATE_RESULT_URL)
    Call<Integer> updatePsycologicalResult(@Query("customerId")int customerId, @Query("result")String result);

    // 유저
    @POST(Constatnts_url.SIGNUP_URL)
    Call<Customer> signup(@Body Customer customer);
    @POST(Constatnts_url.LOGIN_URL)
    Call<Customer> login(@Query("token") String token);
    @POST(Constatnts_url.LOGOUT_URL)
    Call<Integer> logout(String customerId);
    @POST(Constatnts_url.WITHDRAWAL_URL)
    Call<Integer> withdrawal(String customerId);

    // 여행지
    @GET(Constatnts_url.TOURSPOT_DETAIL)
    Call<TourSpot> tourspotDetail(int tourSpotId);
    @POST(Constatnts_url.TOURSPOT_RANDOM)
    Call<TourSpot> tourspotRandom(@Query("psycolor")String psycolor, @Query("personalcolor")String personalcolor);
    @GET(Constatnts_url.RECOMMEND_PSYCOLOR)
    Call<List<TourSpotSummary>> recommendPsycolor(String psy);
    @GET(Constatnts_url.RECOMMEND_PERSONALCOLOR)
    Call<List<TourSpotSummary>> recommendPersonalColor(String personal);
    @GET(Constatnts_url.RANDOM_TOURSPOT)
    Call<List<TourSpot>> randomTourSpot();
    @GET(Constatnts_url.PALETTE_DETAIL)
    Call<Palette> paletteDetail(int paletteId);
    @GET(Constatnts_url.PALETTE_TOURSPOT)
    Call<List<TourSpot>> paletteTourspot(@Query("paletteId")int paletteId);
    @GET(Constatnts_url.PALETTE_LIST)
    Call<List<Palette>> paletteList(@Path("customerId") int customerId);
    @POST(Constatnts_url.ADD_PALETTE)
    Call<Palette> addPalette(@Body Palette palette);
    @POST(Constatnts_url.DELETE_PALETTE)
    Call<Integer> deletePalette(@Query("paletteId") int paletteId);
    @POST(Constatnts_url.PALETTE_ADD)
    Call<Integer> paletteAdd();
    @POST(Constatnts_url.PALETTE_DELETE)
    Call<Integer> paletteDelete();
    @POST(Constatnts_url.STAR_ADD)
    Call<Star> starAdd(@Query("customerId")int customerId, @Query("tourSpotId")int tourSpotId);
    @POST(Constatnts_url.STAR_DELETE)
    Call<Integer> starDelete();
    @POST(Constatnts_url.STAR_TOURSPOTLIST)
    Call<List<TourSpotSummary>> starTourspotList();

    //@FormUrlEncoded
    //@POST("/auth/overlapChecker")
    //Call<String> postOverlapCheck(@Field("phone") String phoneNum, @Field("message") String message); //이건 요청시 사용하는거 (*데이터를 보낼때)
}
