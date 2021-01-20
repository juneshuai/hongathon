package com.samle.kakao3

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.IgnoreExtraProperties
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlin.math.log


class MyCustomDialog(context: Context)
                    : Dialog(context)
{

    val TAG: String = "로그"

    var db = FirebaseFirestore.getInstance()
    var question_list : MutableMap<Int, String> = mutableMapOf()
    companion object{
        private lateinit var document_Id : String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.custom_dialog)

        Log.d(TAG, "MyCustomDialog - onCreate() called")
        var check=true
        db.collection("questions")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        db.collection("answer").document(LoginActivity.currentUserEmail).get().addOnCompleteListener{
                            if(it.isSuccessful){
                                var tmpContent=document.get("contents").toString()
                                if(check && it.result?.get(tmpContent)?.toString()==null){
                                    document_Id = document.id
                                    dialog_text.text = document.get("contents").toString()
                                    check=false
                                }
                            }

                        }

                    }

                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }




        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        save_btn.setOnClickListener {

           // var map = mutableMapOf<ArrayList<String>, Any>()
//            map["isUsed"] = true
//            map["answer"] = edit_text.text.toString()

            val updateData= hashMapOf(
                    "answer" to edit_text.text.toString(),
                    "content" to dialog_text.text.toString()
            )

            val setData= hashMapOf(
                dialog_text.text.toString() to hashMapOf<String,String>(
                    "answer" to edit_text.text.toString(),
                    "content" to dialog_text.text.toString()
                )
            )

                db.collection("answer").document(LoginActivity.currentUserEmail).get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        if (it.result?.exists()!!) {
                            db.collection("answer").document(LoginActivity.currentUserEmail)
                                .update(dialog_text.text.toString(),updateData)
                                .addOnCompleteListener {
                                    Log.d("확인해야함", "update로 들어감 ")
                                }
                        } else {
                            db.collection("answer").document(LoginActivity.currentUserEmail)
                                .set(setData)
                                .addOnCompleteListener {
                                    Log.d("확인해야함", "set으로 들어감 ")
                                }
                        }
                       
                    }
                }


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






