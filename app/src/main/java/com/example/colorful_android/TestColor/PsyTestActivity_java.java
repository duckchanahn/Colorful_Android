package com.example.colorful_android.TestColor;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.DTO.PersonalColorTestDTO;
import com.example.colorful_android.DTO.PsycologicaltestAnswerDTO;
import com.example.colorful_android.DTO.PsycologicaltestQuestionDTO;
import com.example.colorful_android.R;
import com.example.colorful_android.Retrofit.MyRetrofit;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PsyTestActivity_java extends AppCompatActivity {

    private List<PsycologicaltestQuestionDTO> questions;
    private List<PsycologicaltestAnswerDTO> answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main_layout);



    }

    private void excute() {
        //Retrofit 호출

        Call<PsycologicaltestQuestionDTO> call = MyRetrofit.getApiService().getPsycologicalTestQuestion();
        call.enqueue(new Callback<PsycologicaltestQuestionDTO>() {
            @Override
            public void onResponse(Call<PsycologicaltestQuestionDTO> call, Response<PsycologicaltestQuestionDTO> response) {
                if(!response.isSuccessful()){
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                //                String checkAlready = response.body();
                Log.d("연결이 성공적 : ", response.body().toString());
//                questions = response.body();
                //                    return response.body();
            }
            @Override
            public void onFailure(Call<PsycologicaltestQuestionDTO> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
//        } catch (JSONException e) {
//            Log.e(TAG, "Json put failed");
//            e.printStackTrace();
//        }
//        return result;
    }

}
