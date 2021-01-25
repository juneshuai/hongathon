package com.example.bottomnavi

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.sdk.user.UserApiClient
import com.samle.kakao3.KaKaoApplication
import com.samle.kakao3.LoginActivity
import com.samle.kakao3.R
import kotlinx.android.synthetic.main.fragement_setting.*
import kotlinx.android.synthetic.main.fragement_setting.view.*
import kotlinx.android.synthetic.main.fragement_setting.view.userName
import java.net.URI

class SettingFragement : Fragment() {
    var db = FirebaseFirestore.getInstance()
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
        setUserData()
        view.logout.setOnClickListener {
            UserApiClient.instance.logout {error ->
                if(error != null){
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                    val intent = Intent(context,LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

                }
            }
        }
        view.delete.setOnClickListener {
            deleteUser()
        }
        return view
    }

    fun deleteUser(){
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e(TAG, "연결 끊기 실패", error)
            }
            else {
                db.collection("user").document(LoginActivity.currentUserEmail).delete()
                db.collection("answer").document(LoginActivity.currentUserEmail).delete()
                    .addOnCompleteListener{
                        var intent=Intent(context,LoginActivity::class.java)
                        startActivity(intent)
                    }

            }
        }
    }

    fun setUserData() {
        db.collection("user").document(LoginActivity.currentUserEmail)
            .get().addOnCompleteListener {
                if (it.isSuccessful){

                    userName.text= it.result?.get("name")?.toString()
                    userName.visibility = View.VISIBLE
                    val userProfile= Uri.parse(it.result?.get("profile")?.toString())
                    Glide.with(this).load(userProfile).into(userImage)
                }
            }
    }

}