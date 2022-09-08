package com.colorful.colorful_android.Setting;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.colorful.colorful_android.DTO.Customer;
import com.colorful.colorful_android.MainActivity;
import com.colorful.colorful_android.R;
import com.colorful.colorful_android.Retrofit.MyRetrofit;
import com.kakao.sdk.user.UserApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingActivity extends AppCompatActivity {

    private ImageView prevButton;

    private Button withdrawal;
    private Button logout;
    private Button policy;
    private Button license;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_setting);

        this.prevButton = findViewById(R.id.btn_back);
        this.prevButton.setOnClickListener( v -> {
            finish();
        });

        this.withdrawal = findViewById(R.id.withdrawal);
        this.logout = findViewById(R.id.logout);
        this.policy = findViewById(R.id.policy);
        this.license = findViewById(R.id.license);

        this.withdrawal.setOnClickListener( vd -> {

            if(Customer.getInstance().getLoginType().equals("카카오")) {
                UserApiClient.getInstance().unlink(throwable -> {
                    if (throwable != null) {
                        Toast.makeText(this, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show();
                        excute_withdrawal();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }
                    return null;
                });
            } else {
                // naver logout
                Toast.makeText(getBaseContext(), "네이버 로그아웃에 대한 별도의 api가 없으며 사용자가 직접 네이버 서비스에서 로그아웃 하도록 처리하셔야 합니다.\n" +
                        "이유는 이용자 보호를 위해 정책상 네이버 이외의 서비스에서 네이버 로그아웃을 수행하는 것을 허용하지 않고 있는 점 양해 부탁드립니다.", Toast.LENGTH_LONG);
            }
        });

        this.logout.setOnClickListener( vl -> {

            if(Customer.getInstance().getLoginType().equals("카카오")) {
                UserApiClient.getInstance().logout(error -> {
                    if (error != null) {
                        Log.e("kakaoLogout", "로그아웃 실패, SDK에서 토큰 삭제됨", error);
                    } else {
                        Log.e("kakaoLogout", "로그아웃 성공, SDK에서 토큰 삭제됨");
                        excute_logout();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent.setFlags(FLAG_ACTIVITY_CLEAR_TOP));
                        finish();
                    }
                    return null;
                });
            } else {
                //naver withdrawal

                Toast.makeText(getBaseContext(), "네이버 로그아웃에 대한 별도의 api가 없으며 사용자가 직접 네이버 서비스에서 로그아웃 하도록 처리하셔야 합니다.\n" +
                        "이유는 이용자 보호를 위해 정책상 네이버 이외의 서비스에서 네이버 로그아웃을 수행하는 것을 허용하지 않고 있는 점 양해 부탁드립니다.", Toast.LENGTH_LONG);
            }
        });


        this.policy.setOnClickListener( v -> {
            this.startActivity(new Intent(this, PolicyActivity.class));
        });

        this.license.setOnClickListener( v -> {
            this.startActivity(new Intent(this, LicenseActivity.class));
        });

    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, 0); //애니메이션 없애기
    }

    private void excute_logout() {
        Call<Integer> call = MyRetrofit.getApiService().logout(Customer.getInstance().getCustomerId());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());

                Customer.getInstance().setInstance(new Customer());
                finish();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

    private void excute_withdrawal() {
        Call<Integer> call = MyRetrofit.getApiService().withdrawal(Customer.getInstance().getCustomerId());
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                Log.d("연결이 성공적 : ", response.body().toString());

                Customer.getInstance().setInstance(new Customer());
                finish();

            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.getMessage());
            }
        });
    }

}
