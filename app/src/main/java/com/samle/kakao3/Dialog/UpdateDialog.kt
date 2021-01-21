package com.samle.kakao3

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.bottomnavi.MiddleFragement
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.custom_dialog.cancel_btn
import kotlinx.android.synthetic.main.custom_dialog.dialog_text
import kotlinx.android.synthetic.main.custom_dialog.edit_text
import kotlinx.android.synthetic.main.custom_dialog.save_btn
import kotlinx.android.synthetic.main.update_dialog.*
import org.w3c.dom.Text
import kotlin.concurrent.timer
import kotlin.math.log


class UpdateDialog(context: Context,question:String,answer: String)
    : Dialog(context)
{

    val TAG: String = "로그"
    var que=question
    var ans=answer
    var db = FirebaseFirestore.getInstance()
    var question_list : MutableMap<Int, String> = mutableMapOf()
    var uniqueQuestion : String = ""
    companion object{

        var userContent = ArrayList<String>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.update_dialog)

        Log.d(TAG, "MyCustomDialog - onCreate() called")
        var check=true
        dialog_text.text=que
        edit_text.setText(ans)
        loadData()


        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //버튼 저장
        save_btn.setOnClickListener {


           db.collection("answer").document(LoginActivity.currentUserEmail)
               .collection("userData").document(dialog_text.text.toString())
               .update("answer",edit_text.text.toString()).addOnCompleteListener {
                   Toast.makeText(context,"우와성공쓰",Toast.LENGTH_SHORT).show()
               }

            Toast.makeText(context,"수정완료",Toast.LENGTH_SHORT).show()

            dismiss()

        }
        cancel_btn.setOnClickListener {
            Toast.makeText(context,"취소",Toast.LENGTH_SHORT).show()
            dismiss()
        }
        setCancelable(false);


    }

    fun loadData() {
        db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
            .get().addOnCompleteListener{
                for(documents in it.result!!){
                    userContent.add(documents.get("contents").toString())
                    for (i in userContent){
                        Log.d("리스트 값 확인하자 ",i)
                    }
                }
            }

    }



}






