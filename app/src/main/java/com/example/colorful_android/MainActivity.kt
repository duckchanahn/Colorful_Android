package com.example.colorful_android

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider

//import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.coroutine.TedPermission
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(){

    //카메라에서 찍고 가져오기
    val REQUEST_IMAGE_CAPTURE = 1
    lateinit var currentPhotoPath : String

    //갤러리에서 가져오기
    val TYPE_1 = 0
    val TYPE_2 = 1

    val PERMISSIONS_REQUEST_CODE = 100
    var REQUIRED_PERMISSIONS = arrayOf<String>( Manifest.permission.READ_EXTERNAL_STORAGE)

    val REQUEST_GET_IMAGE = 105
    var positionMain = 0
    lateinit var itemMain:DataClass
    var dataList:MutableList<DataClass> = mutableListOf()
    lateinit var recyclerAdapter : RecyclerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val start_test : Button = findViewById(R.id.start_test);

        start_test.setOnClickListener {
            
        }

//        R.id.start_test ->{
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                // 디바이스 내부 저장소 접근 권한을 얻었을 경우 이미지 업로드 화면으로 이동
//                startActivity(Intent(this, AddPhotoActivity::class.java))
//            } else {
//                // 디바이스 내부 저장소 접근 권한을 얻지 못했을 경우 토스트 출력
//                Toast.makeText(this, "디바이스 스토리지 읽기 권한이 없습니다.", Toast.LENGTH_LONG).show()
//            }
//            return true
//        };

        settingPermission() // 카메라 이미지 권한체크 시작

        btn_picture.setOnClickListener {
            startCapture()
        }

        requestPermission()

        dataList.add(DataClass("", "1번타입_1", TYPE_1))
        dataList.add(DataClass("", "1번타입_2", TYPE_1))
        dataList.add(DataClass("","2번타입_1", TYPE_2))
        dataList.add(DataClass("","2번타입_2", TYPE_2))

        recyclerAdapter = RecyclerAdapter(this, dataList){ position, item ->
            positionMain = position
            itemMain = item

            val getContent =
                registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                    binding.ivProfileImage.setImgURI(result.data?.data)
                }

            val intent = Intent(Intent.ACTION_PICK)

            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            getContent.launch(intent)

            //startActivityForResult(intent, REQUEST_GET_IMAGE)

            intent.type = "drawable/image/*"
            startActivityForResult(intent, REQUEST_GET_IMAGE)
            // getContent.launch(intent)
            // startActivityForResult 이 함수가 2020년 이후로 사용이 중지되었다고 해서 혹시 참고할만한 코드 있나 보고 해봤어요!
            // getContent.launch(intent) 이렇게 실행하나봐요
            // https://youngest-programming.tistory.com/517
            // https://developer.android.com/training/basics/intents/result?hl=ko
            // 아래에도 한 줄 있어요!

        }

//        main_recyclerVIew.adapter = recyclerAdapter
//        main_recyclerVIew.layoutManager = LinearLayoutManager(this)

    }

    //카메라 접근 권한
   fun settingPermission(){
        var permis = object  : PermissionListener{
            //            어떠한 형식을 상속받는 익명 클래스의 객체를 생성하기 위해 다음과 같이 작성
            override fun onPermissionGranted() {
                Toast.makeText(this@MainActivity, "권한 허가", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@MainActivity, "권한 거부", Toast.LENGTH_SHORT)
                    .show()
                ActivityCompat.finishAffinity(this@MainActivity) // 권한 거부시 앱 종료
            }
        }

        TedPermission.create()

        TedPermission.with(this)
            .setPermissionListener(permis)
            .setRationaleMessage("카메라 사진 권한 필요")
            .setDeniedMessage("카메라 권한 요청 거부")
            .setPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA)
            .check()
    }

    //카메라 호출 함수 (촬영버튼 누를 때 해당 함수 실행)
    fun startCapture(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try{
                    createImageFile()
                }catch(ex:IOException){
                    null
                }
                photoFile?.also{
                    val photoURI : Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.colorful_android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    //사진을 찍고 이미지를 파일로 저장
   @Throws(IOException::class)
    private fun createImageFile() : File{
        val timeStamp : String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir : File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply{
            currentPhotoPath = absolutePath
        }
    }


    //갤러리 접근 권한
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            PERMISSIONS_REQUEST_CODE -> {
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //권한 허용
                }else{
                    //권한 거부됨
                }
                return
            }
        }
    }

    private fun requestPermission(){
        var permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            //설명이 필요한지
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)){
                //설명 필요 (사용자가 요청을 거부한 적이 있음)
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE )
            }else{
                //설명 필요하지 않음
                ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE )
            }
        }else{
            //권한 허용
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(resultCode == Activity.RESULT_OK){
//            when(requestCode){
//                REQUEST_GET_IMAGE -> {
//                    try{
//                        var uri = data?.data
//                        dataList[positionMain].image = uri.toString()
//                        recyclerAdapter.notifyDataSetChanged()
//                    }catch (e:Exception){}
//                }
//            }
//        }
//    }





    /* 카메라로 찍은 이미지 뷰의 표시
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){
            val file = File(currentPhotoPath)
            if (Build.VERSION.SDK_INT < 28) {
                val bitmap = MediaStore.Images.Media
                    .getBitmap(contentResolver, Uri.fromFile(file))
                img_picture.setImageBitmap(bitmap)
            }
            else{
                val decode = ImageDecoder.createSource(this.contentResolver,
                    Uri.fromFile(file))
                val bitmap = ImageDecoder.decodeBitmap(decode)
                img_picture.setImageBitmap(bitmap)
            }
        }
    }
    */

}