package com.example.colorful_android.TestColor;

import android.content.Intent;
import android.database.Cursor;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.colorful_android.Model.User;
import com.example.colorful_android.R;
import com.gun0912.tedpermission.PermissionListener;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//import com.bumptech.glide.Glide;

public class TestColorActivity_java extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

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
            test_getImage_inCamera();
        }) ;

        Button get_image_gallery_button = findViewById(R.id.get_image_gallery_button);
        get_image_gallery_button.setOnClickListener(v -> {
            test_getImage_inGallery();
        }) ;

        Button next_button = findViewById(R.id.next_personal_color_select_image);
        next_button.setOnClickListener(v -> {

            if(filePath == null) {
                Toast.makeText(getBaseContext(), "사진을 선택해주세요!", Toast.LENGTH_SHORT).show();
            } else {
                Intent next_button_intent = new Intent(this, PersonalTestResultActivity_java.class);
//                user_image_binary = new byte['a'];
                next_button_intent.putExtra("filePath", filePath);
                startActivity(next_button_intent);

                finish();
            }
        }) ;

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
                Uri photoURI = FileProvider.getUriForFile(TestColorActivity_java.this, PROVIDER, photoFile);
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
                Toast.makeText(TestColorActivity_java.this, "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(TestColorActivity_java.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }
        };
    }

}