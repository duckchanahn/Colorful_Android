package com.colorful.colorful_android


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


public class SplashActivity : AppCompatActivity() {

    var anim_FadeIn: Animation? = null
    var anim_FadeOut: Animation? = null
    var constraintLayout: ConstraintLayout? = null
    var logo_Gray: ImageView? = null
    var logo_Main: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        constraintLayout = findViewById(R.id.constraintLayout)
        logo_Gray = findViewById<View>(R.id.splash_gray) as ImageView?
        logo_Main = findViewById<View>(R.id.splash_main) as ImageView?

        anim_FadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        anim_FadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        anim_FadeIn!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })

        logo_Main?.startAnimation(anim_FadeIn)





    }

    
    
    
    
    /*
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ac)
        
        constraintLayout = findViewById(R.id.constraintLayout)
        logo_Gray = findViewById<View>(R.id.splash_gray)
        logo_Main = findViewById<View>(R.id.lock_o)
        
        anim_FadeIn = AnimationUtils.loadAnimation(this, R.anim.anim_splash_fadein)
        anim_ball = AnimationUtils.loadAnimation(this, R.anim.anim_splash_ball)
        anim_FadeIn!!.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}
            override fun onAnimationEnd(animation: Animation) {
                startActivity(Intent(this@SplashActivity, Login::class.java))
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
        lcklockImageView.startAnimation(anim_FadeIn)
        faceRecgnitionImageView.startAnimation(anim_FadeIn)
        oImageView.startAnimation(anim_ball)
    }
     */


}