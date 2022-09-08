package com.colorful.colorful_android.TestColor;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.colorful.colorful_android.DTO.Customer;
import com.colorful.colorful_android.DTO.PersonalColorTestDTO;
import com.colorful.colorful_android.R;
import com.colorful.colorful_android.Retrofit.MyRetrofit;
import com.gun0912.tedpermission.PermissionListener;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.bumptech.glide.Glide;

public class PersonalTestSelectImageActivity extends AppCompatActivity
                implements ActivityCompat.OnRequestPermissionsResultCallback{

    private final String TAG = this.getClass().getSimpleName();

    private static final int PERMISSIONS_REQUEST_CODE = 100;

    private String[] REQUIRED_PERMISSIONS;  // 외부 저장

    private final String PROVIDER = "com.example.colorful_android.provider";

    private ImageView imageview;

    private Uri uri;
    private PermissionListener permissionlistener;
    private String filePath;

    private byte[] user_image_binary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_color_select_image_layout);

        this.permissionlistener = getPermissionListener();
        this.imageview = findViewById(R.id.test_user_imageview);

        Button get_image_camera_button = findViewById(R.id.get_image_camera_button);
        get_image_camera_button.setOnClickListener(v -> {

            int permission_camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            int permission_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(permission_camera == PackageManager.PERMISSION_GRANTED && permission_storage == PackageManager.PERMISSION_GRANTED) {
                test_getImage_inCamera();
            }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

                REQUIRED_PERMISSIONS = new String[] {Manifest.permission.CAMERA, // 카메라
                        Manifest.permission.WRITE_EXTERNAL_STORAGE};

                ActivityCompat.requestPermissions( PersonalTestSelectImageActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            }

        }) ;

        Button get_image_gallery_button = findViewById(R.id.get_image_gallery_button);
        get_image_gallery_button.setOnClickListener(v -> {

            int permission_camera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
            int permission_storage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if(permission_camera == PackageManager.PERMISSION_GRANTED && permission_storage == PackageManager.PERMISSION_GRANTED) {
                test_getImage_inGallery();
            }else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

                REQUIRED_PERMISSIONS = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                ActivityCompat.requestPermissions( PersonalTestSelectImageActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            }
        }) ;

//        Button next_button = findViewById(R.id.next_personal_color_select_image);
//        next_button.setOnClickListener(v -> {
//
//            if(filePath == null) {
//                Toast.makeText(getBaseContext(), "사진을 선택해주세요!", Toast.LENGTH_SHORT).show();
//            } else {
//                Log.e("nextButton", filePath);
//                Intent next_button_intent = new Intent(this, PersonalTestResultActivity.class);
////                user_image_binary = new byte['a'];
//                next_button_intent.putExtra("filePath", filePath);
//                startActivity(next_button_intent);
//
//                finish();
//            }
//        }) ;

    }


    String result = "";
    private void nextPage() {

        Thread mThread = new Thread() {
            public void run() {
                result = init(filePath);
            }
        };

        mThread.start();

        try {
            mThread.join();
            Intent next_button_intent = new Intent(this, PersonalTestResultActivity.class);
//                user_image_binary = new byte['a'];
            next_button_intent.putExtra("personalColor", result);
            startActivity(next_button_intent);

            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



//        Intent next_button_intent = new Intent(this, PersonalTestResultActivity.class);
////                user_image_binary = new byte['a'];
//        next_button_intent.putExtra("personalColor", init(filePath));
//        startActivity(next_button_intent);
//
//        finish();
    }


    private void test_getImage_inCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace(); // 실패하면 어떡해? 쓰지 말라고 해?
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(PersonalTestSelectImageActivity.this, PROVIDER, photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                launcher_camera.launch(takePictureIntent);
            }
        }
    }

    private void test_getImage_inGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher_gallery.launch(intent);
    }

    ActivityResultLauncher<Intent> launcher_camera = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Log.d(TAG, "success get filePath : " + filePath);

                        nextPage();
                    }
                }
            });

    ActivityResultLauncher<Intent> launcher_gallery = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        uri = intent.getData();
                        filePath = getRealPathFromURI(uri);
                        Log.d(TAG, "success get filePath : " + filePath);

                        nextPage();
                    }
                }
            });


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("HHmmss").format(new Date());
        String imageFileName = "colorful_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"");
        storageDir.mkdirs();

        File image = File.createTempFile(imageFileName, ".png", storageDir);
        this.filePath = image.getAbsolutePath();

        return image;
    }


    // Image의 절대경로를 가져오는 메소드
    private String getRealPathFromURI(Uri contentUri) {
        if (contentUri.getPath().startsWith("/storage")) {
            return contentUri.getPath();
        }
        String id = DocumentsContract.getDocumentId(contentUri).split(":")[1];
        String[] columns = { MediaStore.Files.FileColumns.DATA };
        String selection = MediaStore.Files.FileColumns._ID + " = " + id;
        Cursor cursor = this.getContentResolver().query(MediaStore.Files.getContentUri("external"), columns, selection, null, null);
        try {
            int columnIndex = cursor.getColumnIndex(columns[0]);
            if (cursor.moveToFirst())
            { return cursor.getString(columnIndex);
            }
        } finally {
            cursor.close();
        } return null;
    }


    private PermissionListener getPermissionListener() {
        return permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(PersonalTestSelectImageActivity.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(PersonalTestSelectImageActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            boolean check_result = true;

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            if (check_result) {

                if(REQUIRED_PERMISSIONS.length == 1) {test_getImage_inGallery();}
                else {test_getImage_inCamera();}

            } else {
                boolean check_rationale = false;
                for(String s : REQUIRED_PERMISSIONS) {
                   if(ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0])) {
                       check_rationale = true;
                   }
                }
                if (check_rationale) {
                    Toast.makeText(getBaseContext(), "권한을 허용하지 않을 경우 테스트가 불가능합니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getBaseContext(), "퍼스널컬러 테스트를 위해 앱 설정에서 권한을 허용해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    /////////////////////////////////////////////////////////////
    // 결과 저장
    /////////////////////////////////////////////////////////////

    private String init(String filePath) {
//        this.filePath = getIntent().getStringExtra("filePath");
        this.user_image_binary = this.pathToBinary(filePath);

        Log.e(TAG, "filePath : " + filePath);

        return excute(user_image_binary);
    }

//    private String result;

    private String excute(byte[] binary) {
        boolean isRight = false;

        //Retrofit 호출
        JSONObject json = new JSONObject();
//        try {
        PersonalColorTestDTO personalColorTestDTO = new PersonalColorTestDTO(Customer.getInstance().getCustomerId(), binary);

        try {
            json.put("customerId", Customer.getInstance().getCustomerId());
            json.put("binary", binary);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Call<String> call = MyRetrofit.getApiService().getPersonalColor(personalColorTestDTO);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }
                //                String checkAlready = response.body();
                Log.d("연결이 성공적 : ", response.body().toString());
                result = response.body();
                TestMainActivity.testMainActivity.finish();
                //                    return response.body();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
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