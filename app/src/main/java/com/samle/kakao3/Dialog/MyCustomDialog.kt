package com.jeongdaeri.mycustomdialog_tutorial

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.samle.kakao3.R
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.fragement_home.*

class MyCustomDialog(context: Context)
                    : Dialog(context)
{

    val TAG: String = "로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.custom_dialog)

        Log.d(TAG, "MyCustomDialog - onCreate() called")
        // 배경 투명
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        save_btn.setOnClickListener {
            Toast.makeText(context,"저장",Toast.LENGTH_SHORT).show()
            dismiss()
        }
        cancel_btn.setOnClickListener {
            Toast.makeText(context,"취소",Toast.LENGTH_SHORT).show()
            dismiss()
        }
        setCancelable(false);


    }



}
