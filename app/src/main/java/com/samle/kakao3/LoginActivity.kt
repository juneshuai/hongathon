package com.samle.kakao3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import com.samle.kakao3.databinding.ActivityLoginBinding


private const val TAG = "LoginActivity"

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

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
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                        }

                }
            } else {
                LoginClient.instance.loginWithKakaoAccount(this) { token, error ->
                    Log.i(TAG, "loginWithKakaoAccount $token $error")
                    //updateLoginInfo()
                    if(token != null) {
                        val intent = Intent(applicationContext, MainActivity::class.java)
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