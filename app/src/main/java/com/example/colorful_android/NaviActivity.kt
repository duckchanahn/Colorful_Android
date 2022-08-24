package com.example.colorful_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.colorful_android.databinding.ActivityNaviBinding

private const val TAG_HOME = "home_fragment"
private const val TAG_SEARCH = "search_fragment"
private const val TAG_COLOR = "color_fragment"
private const val TAG_MYPAGE = "mypage_fragment"

class NaviActivity : AppCompatActivity() {

    private lateinit var binding :ActivityNaviBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFragment(TAG_HOME, HomeFragment())

        binding.navMain.run {
            setOnItemSelectedListener { item ->
                when(item.itemId){
                    R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                    R.id.searchFragment -> setFragment(TAG_SEARCH, SearchFragment())
                    R.id.colorFragment -> setFragment(TAG_COLOR, ColorFragment())
                    R.id.mypageFragment-> setFragment(TAG_MYPAGE, MypageFragment())
                }
                true
            }
        }

    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager : FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if(manager.findFragmentByTag(tag) == null) {
            fragTransaction.add(R.id.mainFramLayout, fragment, tag)
        }

        val home = manager.findFragmentByTag(TAG_HOME)
        val search = manager.findFragmentByTag(TAG_SEARCH)
        val color = manager.findFragmentByTag(TAG_COLOR)
        val mypage = manager.findFragmentByTag(TAG_MYPAGE)

        if(home != null){
            fragTransaction.hide(home)
        }

        if(search != null){
            fragTransaction.hide(search)
        }

        if(color != null){
            fragTransaction.hide(color)
        }

        if(mypage != null){
            fragTransaction.hide(mypage)
        }

        if(tag == TAG_HOME){
            if(home != null){
                fragTransaction.show(home)
            }
        }
        else if(tag == TAG_SEARCH){
            if(search != null){
                fragTransaction.show(search)
            }
        }
        else if(tag == TAG_COLOR){
            if(color != null){
                fragTransaction.show(color)
            }
        }
        else if(tag == TAG_MYPAGE){
            if(mypage != null){
                fragTransaction.show(mypage)
            }
        }

        fragTransaction.commitAllowingStateLoss()

    }
}