package com.colorful.colorful_android

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.colorful.colorful_android.Color.ColorActivity
import com.colorful.colorful_android.DTO.Customer
import com.colorful.colorful_android.DTO.Star
import com.colorful.colorful_android.DTO.TourSpot
import com.colorful.colorful_android.Fragment.NaviActivity
import com.colorful.colorful_android.Home.HomeMainDialog
import com.colorful.colorful_android.Login.LoginActivity
import com.colorful.colorful_android.Mypage.MypageActivity
import com.colorful.colorful_android.Retrofit.MyRetrofit
import com.colorful.colorful_android.Search.SerachActivity
import com.colorful.colorful_android.TestColor.TestMainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kakao.sdk.auth.TokenManagerProvider
import com.navercorp.nid.NaverIdLoginSDK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


class MainActivity : AppCompatActivity() {

    val IMAGE_URL = "http://tong.visitkorea.or.kr/cms/resource/58/1902758_image2_1.jpg"
    val coroutineScope = CoroutineScope(Dispatchers.Main)

    lateinit var tourSpot : TourSpot

    lateinit var userName :TextView
    lateinit var backgroundImage :ImageView
    lateinit var tourSpotName :TextView
    lateinit var detailButton :Button
    lateinit var starButton :ImageButton

    lateinit var navigation : BottomNavigationView

    private val TAG_HOME = "home_fragment"
    private val TAG_SEARCH = "search_fragment"
    private val TAG_COLOR = "color_fragment"
    private val TAG_MYPAGE = "mypage_fragment"

    private lateinit var star : Star

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        if (TokenManagerProvider.instance.manager.getToken() == null && NaverIdLoginSDK.getAccessToken() == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else if (TokenManagerProvider.instance.manager.getToken() != null) {
            excute_login(token = TokenManagerProvider.instance.manager.getToken()?.refreshToken.toString())
        } else if (NaverIdLoginSDK.getRefreshToken() != null) {
            excute_login(token = NaverIdLoginSDK.getRefreshToken().toString())
        }

        setContentView(R.layout.activity_home_main)

        tourSpot = TourSpot()

        this.userName = findViewById(R.id.user_name)
        this.backgroundImage = findViewById(R.id.image_background)
        this.tourSpotName = findViewById(R.id.tour_spot_name)
        this.detailButton = findViewById(R.id.detail)
        this.starButton = findViewById(R.id.star_button)

        this.navigation = findViewById(R.id.nav_main)

        val NaviActivity = NaviActivity()
        navigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.homeFragment ->selected_navi(TAG_HOME)
                R.id.searchFragment -> selected_navi(TAG_SEARCH)
                R.id.colorFragment -> selected_navi(TAG_COLOR)
                R.id.mypageFragment-> selected_navi(TAG_MYPAGE)
            }
            true
        }


        this.detailButton.setOnClickListener{
            val intent = Intent(this, HomeMainDialog::class.java)
            intent.putExtra("TourSpot", tourSpot)
            intent.putExtra("prevPage", "MainActivity")
            Log.e("home", tourSpot.name)
            startActivity(intent)
            finish()
        }

        this.starButton = findViewById<ImageButton>(R.id.star_button)
        excute_starCheck(tourSpot.tourSpotId)
        this.starButton.setOnClickListener(View.OnClickListener { v: View? ->
            Log.e("star", "start!! bool : $result")
            if (result) {
                excute_deleteStar(star.getStarId())
            } else {
                excute_addStar(tourSpot.tourSpotId)
            }
        })



        val swipeRefreshLayout: SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener {
            excute_home()
            excute_starCheck(tourSpot.tourSpotId)
            swipeRefreshLayout.isRefreshing = false // 새로고침을 완료하면 아이콘을 없앤다.
        }

    }

    fun selected_navi(TAG : String) {
        Log.e("home", TAG)

        if(TAG == TAG_HOME){
//            startActivity(Intent(this, ColorActivity::class.java))
//            overridePendingTransition(0, 0); //애니메이션 없애기

//            intent = new Intent(getApplicationContext(), SerachActivity.class);
            return
        }
        else if(TAG == TAG_SEARCH){
            startActivity(Intent(this, SerachActivity::class.java))
            overridePendingTransition(0, 0); //애니메이션 없애기
        }
        else if(TAG == TAG_COLOR){
            startActivity(Intent(this, ColorActivity::class.java))
            overridePendingTransition(0, 0); //애니메이션 없애기
        }
        else if(TAG == TAG_MYPAGE){
            startActivity(Intent(this, MypageActivity::class.java))
            overridePendingTransition(0, 0); //애니메이션 없애기
        }
        finish()
    }


    override fun onBackPressed() {
        finish()
        overridePendingTransition(0, 0) //애니메이션 없애기
    }

    //Url 이미지 비트맵전환
    private fun getOriginalBitmap(urlString : String) {
        var background : Bitmap? = null;
        val mThread: Thread = object : Thread() {
            override fun run() {
                try {
                    val url = URL(urlString)

                    // Web에서 이미지를 가져온 뒤
                    // ImageView에 지정할 Bitmap을 만든다
                    val conn = url.openConnection() as HttpURLConnection
                    conn.doInput = true // 서버로 부터 응답 수신
                    conn.connect()
                    val input = conn.inputStream // InputStream 값 가져오기
                    background = BitmapFactory.decodeStream(input) // Bitmap으로 변환
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        mThread.start(); // Thread 실행

        try {
            mThread.join()
            backgroundImage.setImageBitmap(background)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun loadImage(bmp: Bitmap) {
        val imageView = findViewById<ImageView>(R.id.iv_image)
        imageView.setImageBitmap(bmp)
        imageView.visibility = View.VISIBLE
    }

    private fun excute_login(token: String) {
        Log.e("token", token)
        val call_a: Call<Customer> = MyRetrofit.getApiService().login(token);
        call_a.enqueue(object : Callback<Customer> {
            override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                if (!response.isSuccessful()) {
//                    Toast.makeText( getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    return;
                }

                // 이게 돼?
                Customer.getInstance().setInstance(response.body())
                Log.e("login success", "login : " + Customer.getInstance().customerName + ", type : " + Customer.getInstance().loginType)
                excute_home()

                // 테스트!!
                try {
                    if (Customer.getInstance().personalColor.equals("") && Customer.getInstance().psycologicalColor.equals("")) {
                        startActivity(Intent(baseContext, TestMainActivity::class.java))
                    }
                } catch(e : NullPointerException) {
                    startActivity(Intent(baseContext, TestMainActivity::class.java))
                }
            }

            override fun onFailure(call: Call<Customer>, t: Throwable) {
//                Toast.makeText(MainActivity.this, "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("연결실패", t.message + "");
            }

        });

    }

    private fun excute_home() {
        val call_a: Call<TourSpot> = MyRetrofit.getApiService().tourspotRandom(
            Customer.getInstance()?.psycologicalColor,
            Customer.getInstance().personalColor
        );


        Log.e("excute_home", "psycologicalColor " + Customer.getInstance()?.psycologicalColor +
                "personalColor " + Customer.getInstance().personalColor);

        call_a.enqueue(object : Callback<TourSpot> {
            override fun onResponse(call: Call<TourSpot>, response: Response<TourSpot>) {
                if (!response.isSuccessful()) {
//                    Toast.makeText( getBaseContext(), "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                    Log.e("excute_home : ", "연결이 비정상적 : " + response.code());
                    return;
                }

                tourSpot.tourSpotId = response.body()?.tourSpotId!!
                tourSpot.name = response.body()?.name
                tourSpot.area = response.body()?.area
                tourSpot.position_x = response.body()?.position_x
                tourSpot.position_y = response.body()?.position_y
                tourSpot.address = response.body()?.address
                tourSpot.personalColor = response.body()?.personalColor
                tourSpot.psyColor = response.body()?.psyColor
                tourSpot.hours = response.body()?.hours
                tourSpot.homepage = response.body()?.homepage
                tourSpot.parking = response.body()?.parking
                tourSpot.content = response.body()?.content
                tourSpot.images = response.body()?.images
                tourSpot.telephone = response.body()?.telephone
                tourSpot.tags = response.body()?.tags

                // 이게 돼?
                Log.e("tourSpotId", tourSpot.tourSpotId.toString())
                Log.e("image", tourSpot.images)

                userName.setText(Customer.getInstance().customerName)

//                Thread {
                    getOriginalBitmap(tourSpot.images)
//                }.start()

                tourSpotName.setText(tourSpot.name)


                excute_starCheck(response.body()?.tourSpotId!!)

            }

            override fun onFailure(call: Call<TourSpot>, t: Throwable) {
//                Toast.makeText(MainActivity.this, "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT);
                Log.e("excute_home", "연결실패 : " + t.message + "");
            }

        });
    }


    /////////////////////////////////////////////////////////////////
    // 찜하기 + 찜 체크 + 찜 삭제
    /////////////////////////////////////////////////////////////////

    /////////////////////////////////////////////////////////////////
    // 찜하기 + 찜 체크 + 찜 삭제
    /////////////////////////////////////////////////////////////////
    private fun excute_addStar(tourSpotId: Int) {
        val call = MyRetrofit.getApiService().starAdd(Customer.getInstance().customerId, tourSpotId)
        call.enqueue(object : Callback<Star> {
            override fun onResponse(call: Call<Star>, response: Response<Star>) {
                if (!response.isSuccessful) {
                    Toast.makeText(baseContext, "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT)
                    Log.e("연결이 비정상적 : ", "error code : " + response.code())
                    return
                }
                Log.d("연결이 성공적 : ", response.body().toString())
                starButton.background = resources.getDrawable(R.drawable.btn_heart_fill_home, null)
                result = true
                //                starButton.setBackground(R.drawable.heart);
            }

            override fun onFailure(call: Call<Star>, t: Throwable) {
                Toast.makeText(baseContext, "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT)
                Log.e("연결실패", t.message!!)
            }
        })
    }

    private fun excute_deleteStar(starId: Int) {
        val call = MyRetrofit.getApiService().starDelete(starId)
        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (!response.isSuccessful) {
                    Toast.makeText(baseContext, "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT)
                    Log.e("연결이 비정상적 : ", "error code : " + response.code())
                    return
                }
                Log.d("연결이 성공적 : ", response.body().toString())
                starButton.background = resources.getDrawable(R.drawable.btn_heart_home, null)
                result = false
                //                starButton.setBackground(R.drawable.heart);
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Toast.makeText(baseContext, "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT)
                Log.e("연결실패", t.message!!)
            }
        })
    }

    var result = false
    private fun excute_starCheck(tourSpotId: Int): Boolean {
        result = false
        val call =
            MyRetrofit.getApiService().starCheck(Customer.getInstance().customerId, tourSpotId)
        call.enqueue(object : Callback<Star> {
            override fun onResponse(call: Call<Star>, response: Response<Star>) {
                if (!response.isSuccessful) {
                    Toast.makeText(baseContext, "연결 상태가 좋지 않습니다. 다시 시도해주세요", Toast.LENGTH_SHORT)
                    Log.e("연결이 비정상적 : ", "excute_starCheck, error code : " + response.code())
                    return
                }
                Log.d("연결이 성공적 : ", response.body()?.starId.toString())
                if (response.body()!!.starId != -1) {
                    result = true
                    star = response.body()!!
                    starButton.background = resources.getDrawable(R.drawable.btn_heart_fill_home, null)
                } else {
                    starButton.background = resources.getDrawable(R.drawable.btn_heart_home, null)
                }

//                starButton.setBackground(R.drawable.heart);
            }

            override fun onFailure(call: Call<Star>, t: Throwable) {
                Toast.makeText(
                    baseContext,
                    "excute_starCheck, D연결 상태가 좋지 않습니다. 다시 시도해주세요",
                    Toast.LENGTH_SHORT
                )
                Log.e("연결실패", t.message!!)
            }
        })
        return result
    }
}