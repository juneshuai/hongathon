package com.samle.kakao3

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.bottomnavi.HomeFragement
import com.example.bottomnavi.MiddleFragement
import com.example.bottomnavi.SettingFragement
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var homeFragement: HomeFragement
    private lateinit var middleFragement: MiddleFragement
    private lateinit var settingFragement: SettingFragement
    var mBackWait:Long = 0


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
        if(System.currentTimeMillis()-mBackWait>=2000){
            mBackWait = System.currentTimeMillis()
            Snackbar.make(main_activity,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Snackbar.LENGTH_SHORT).show()
        }else{
            finish()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(TAG,"MainActivity - onNavigationItemReselected() called")

        when(item.itemId){
            R.id.menu_home ->{
                Log.d(TAG,"MainActivity - 홈버튼 클릭")
                homeFragement = HomeFragement.newInstance()
                setFrag(homeFragement)
                Log.d("새로고침확인", "${supportFragmentManager.beginTransaction().detach(homeFragement).attach(homeFragement).commit()}");



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


