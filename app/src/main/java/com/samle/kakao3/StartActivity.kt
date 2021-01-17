package com.samle.kakao3

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.samle.kakao3.databinding.ActivityStartBinding
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.android.synthetic.main.activity_start.view.*
import java.util.*

class StartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        start_main.setOnTouchListener { v, event ->
            when(event?.action){
                MotionEvent.ACTION_DOWN ->{
                    false
                }
                else -> true
            }
        }

        val timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run(){
                val intent = Intent(applicationContext,LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        },4000)



    }




}