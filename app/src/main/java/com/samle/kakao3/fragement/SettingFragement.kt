package com.example.bottomnavi

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.samle.kakao3.R

class SettingFragement : Fragment() {

    companion object{
        const val TAG : String = "로그"

        fun newInstance() : SettingFragement{
            return SettingFragement()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"RankingFragement - onCreate() called")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"RankingFragement - onAttach() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG,"RankingFragement - onCreateView() called")

        val view = inflater.inflate(R.layout.fragement_setting, container, false)

        return view
    }

}