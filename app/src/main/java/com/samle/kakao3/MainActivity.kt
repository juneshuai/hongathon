package com.samle.kakao3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.bottomnavi.HomeFragement
import com.example.bottomnavi.MiddleFragement
import com.example.bottomnavi.SettingFragement
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var homeFragement: HomeFragement
    private lateinit var middleFragement: MiddleFragement
    private lateinit var settingFragement: SettingFragement


    companion object{

        const val TAG:String = "로그"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG,"MainActivity - onCreate() called")

        bottom_nav.setOnNavigationItemSelectedListener(this)
        homeFragement = HomeFragement.newInstance()
        supportFragmentManager.beginTransaction().add(R.id.fragment_frame,homeFragement).commit()


    }

    override fun onBackPressed() {

    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(TAG,"MainActivity - onNavigationItemReselected() called")

        when(item.itemId){
            R.id.menu_home ->{
                Log.d(TAG,"MainActivity - 홈버튼 클릭")
                homeFragement = HomeFragement.newInstance()
                setFrag(homeFragement)
            }
            R.id.menu_ranking -> {
                Log.d(TAG,"MainActivity - 미들 클릭")
                middleFragement = MiddleFragement.newInstance()
                setFrag(middleFragement)
            }

            R.id.menu_profile ->{
                Log.d(TAG,"MainActivity - 세팅 클릭")
                settingFragement = SettingFragement.newInstance()
                setFrag(settingFragement)
            }
        }

        return true
    }

    fun setFrag(fragement: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.fragment_frame,fragement).commit()
    }


}


