package com.samle.kakao3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.samle.kakao3.databinding.ActivityLoginBinding


private const val TAG = "로그"

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var db = FirebaseFirestore.getInstance()

    companion object{
        var currentUserEmail=""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                LoginClient.instance.loginWithKakaoTalk(this) { token, error ->
                        Log.i(TAG, "loginWithKakaoTalk $token $error")
                        //updateLoginInfo()
                        if(token != null) {
                            UserApiClient.instance.me { user, error ->
                                if (error != null) {
                                    Log.e(TAG, "사용자 정보 요청 실패", error)
                                } else if (user != null) {
                                    Log.i(
                                            TAG, "사용자 정보 요청 성공" +
                                            "\n회원번호: ${user.id}" +
                                            "\n이메일: ${user.kakaoAccount?.email}" +
                                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                                    )
                                    currentUserEmail=user.kakaoAccount?.email.toString()
                                    val userData= hashMapOf(
                                            "email" to user.kakaoAccount?.email,
                                            "name" to user.kakaoAccount?.profile?.nickname
                                    )
                                    db.collection("user").document("2")
                                            .set(userData)
                                            .addOnSuccessListener {val intent = Intent(applicationContext, InfoActivity::class.java)
                                                startActivity(intent) }
                                            .addOnFailureListener { e -> Log.w("db값 안들어감", "Error writing document", e) }
                                }
                            }
                            val intent = Intent(applicationContext, InfoActivity::class.java)
                            startActivity(intent)
                        }

                }


            } else {
                LoginClient.instance.loginWithKakaoAccount(this) { token, error ->
                    Log.i(TAG, "loginWithKakaoAccount $token $error")
                    //updateLoginInfo()
                    if(token != null) {
                        UserApiClient.instance.me { user, error ->
                            if (error != null) {
                                Log.e(TAG, "사용자 정보 요청 실패", error)
                            } else if (user != null) {
                                Log.i(
                                        TAG, "사용자 정보 요청 성공" +
                                        "\n회원번호: ${user.id}" +
                                        "\n이메일: ${user.kakaoAccount?.email}" +
                                        "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                                )
                                val userData= hashMapOf(
                                        "email" to user.kakaoAccount?.email,
                                        "name" to user.kakaoAccount?.profile?.nickname
                                )
                                db.collection("user").document("2")
                                        .set(userData)
                                        .addOnSuccessListener {val intent = Intent(applicationContext, InfoActivity::class.java)
                                            startActivity(intent) }
                                        .addOnFailureListener { e -> Log.w("db값 안들어감", "Error writing document", e) }
                            }
                        }
                        val intent = Intent(applicationContext, InfoActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            }
        }

        /*binding.logout.setOnClickListener{
            UserApiClient.instance.logout { error ->
                updateLoginInfo()
            }
        }

        updateLoginInfo()*/
    }

   /*private fun updateLoginInfo() {
        UserApiClient.instance.me { user, error ->
            user?.let {
                Log.i(TAG, "updateLoginInfo: ${user.id} ${user.kakaoAccount?.email} ${user.kakaoAccount?.profile?.nickname} ${user.kakaoAccount?.profile?.thumbnailImageUrl}")
                binding.nickname.text = user.kakaoAccount?.profile?.nickname
                Glide.with(this).load(user.kakaoAccount?.profile?.thumbnailImageUrl).circleCrop().into(binding.profile)
                binding.login.visibility = View.GONE
                binding.logout.visibility = View.VISIBLE
            }
            error?.let {
                binding.nickname.text = null
                binding.profile.setImageBitmap(null)
                binding.login.visibility = View.VISIBLE
                binding.logout.visibility = View.GONE
            }
        }
    }*/
}