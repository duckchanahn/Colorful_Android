package com.colorful.colorful_android.TestColor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.colorful.colorful_android.DTO.Customer;
import com.colorful.colorful_android.MainActivity;
import com.colorful.colorful_android.R;
import com.colorful.colorful_android.Retrofit.MyRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalTestResultActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private byte[] user_image_binary;
    private String personalColor;
    private boolean isRight;

    private TextView subTitle;
    private TextView title;
    private TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_test_result);
        this.personalColor = getIntent().getStringExtra("personalColor");

        this.excute_UpdateResult();

        this.subTitle = findViewById(R.id.subTitle);
        this.title = findViewById(R.id.title);
        this.content = findViewById(R.id.content);
        this.content.setText(personalColor);

        Button button = findViewById(R.id.personal_color_test_button);
        button.setText("심리 컬러 찾으러 가기");
        button.setOnClickListener( v -> {
            startActivity(new Intent(this, PsycoloficalTestActivity.class));
        });

        Button button_home = findViewById(R.id.homeButton);
        button_home.setOnClickListener( v -> {
            startActivity(new Intent(this, MainActivity.class));
        });
    }



    private void excute_UpdateResult() {
        Call<Integer> call_a = MyRetrofit.getApiService().updatePersonalResult(Customer.getInstance().getCustomerId(), getIntent().getStringExtra("personalColor"));
        call_a.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }

            }
            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

}
