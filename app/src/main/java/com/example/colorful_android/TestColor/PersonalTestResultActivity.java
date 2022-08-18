package com.example.colorful_android.TestColor;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.colorful_android.DTO.PersonalColorTestDTO;
import com.example.colorful_android.DTO.PersonalColorTestDTO_k;
import com.example.colorful_android.R;
import com.example.colorful_android.Retrofit.MyRetrofit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PersonalTestResultActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private byte[] user_image_binary;
    private String filePath;
    private boolean isRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_result_personal);

        Log.e(TAG, "결과 액티비티.java -----------");

        this.filePath = getIntent().getStringExtra("filePath");
        this.user_image_binary = this.pathToBinary(filePath);

        Log.e(TAG, "filePath : " + filePath);
        Log.e(TAG, "byte[] : " + user_image_binary.length);


//        Retrofit retrofit = MyRetrofit.create();
//
//        String result = MyRetrofit.createPost(retrofit);

        System.out.println(excute(user_image_binary));
    }

    private String result;

    private String excute(byte[] binary) {
        boolean isRight = false;

        //Retrofit 호출
        JSONObject json = new JSONObject();
//        try {
            PersonalColorTestDTO personalColorTestDTO = new PersonalColorTestDTO(1, binary);

            Call<String> call = MyRetrofit.getApiService().getPersonalColor(personalColorTestDTO);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(!response.isSuccessful()){
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        return;
                    }
    //                String checkAlready = response.body();
                    Log.d("연결이 성공적 : ", response.body().toString());
                    result = response.body().toString();
    //                    return response.body();
                }
                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.e("연결실패", t.getMessage());
                }
            });
//        } catch (JSONException e) {
//            Log.e(TAG, "Json put failed");
//            e.printStackTrace();
//        }
        return result;
    }


    private byte[] pathToBinary(String path){
        Bitmap bitmap= BitmapFactory.decodeFile(path);

        try {
            int orientation = new ExifInterface(filePath).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
            float angle = 0f;
            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                angle = 90f;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                angle = 180f;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                angle = 270f;
            }

            bitmap = resizeBitmap(bitmap, 900f, angle);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

            return stream.toByteArray();
        } catch (IOException e) { // error handling
            Intent intent = new Intent(this, PersonalTestResultActivity.class);
            startActivity(intent);
        }
        return null;
//        imageview.setImageBitmap(bitmap);
    }

    private Bitmap resizeBitmap(Bitmap src, Float size, Float angle){
        int width = src.getWidth();
        int height = src.getHeight();
        float newWidth = 0f;
        float newHeight = 0f;
        if(width > height) {
            newWidth = size;
            newHeight = (float)height * (newWidth / (float)width);
        } else {
            newHeight = size;
            newWidth = (float)width * (newHeight / (float)height);
        }
        float scaleWidth = (float)newWidth / width;
        float scaleHeight = (float)newHeight / height;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
        return resizedBitmap;
    }

}
