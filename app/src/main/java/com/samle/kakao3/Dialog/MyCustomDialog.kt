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
    var uniqueQuestion : String = ""
    companion object{

        var userContent = ArrayList<String>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.custom_dialog)

        Log.d(TAG, "MyCustomDialog - onCreate() called")
        var check=true

        loadData()



        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        save_btn.setOnClickListener {

           // var map = mutableMapOf<ArrayList<String>, Any>()
//            map["isUsed"] = true
//            map["answer"] = edit_text.text.toString()

            val updateData= hashMapOf(
                "answer" to edit_text.text.toString(),
                "content" to dialog_text.text.toString()
            )
            db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("사이즈 검색", it.result?.size().toString())
                        updateData["index"]= it.result?.size().toString()
                    }
             }



          /*  val setData= hashMapOf(
                dialog_text.text.toString() to hashMapOf<String,String>(
                    "answer" to edit_text.text.toString(),
                    "content" to dialog_text.text.toString()
                )
            )*/

                db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
                    .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                            db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
                                .document(dialog_text.text.toString()).set(updateData)
                                .addOnCompleteListener {
                                    Log.d("확인해야함", "update로 들어감 ")

                        } /*else {
                            db.collection("answer").document(LoginActivity.currentUserEmail)
                                .set(setData)
                                .addOnCompleteListener {
                                    Log.d("확인해야함", "set으로 들어감 ")
                                }
                        }*/

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

    fun loadData() {
        db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
            .get().addOnCompleteListener{
                for(documents in it.result!!){
                    userContent.add(documents.get("content").toString())
                    for (i in userContent){
                        Log.d("리스트 값 확인하자 ",i)
                    }
                }
            }

        setQuestion()
    }

    fun setQuestion(){
        db.collection("questions")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        if(!userContent.contains(document.get("contents").toString())){
                            uniqueQuestion = document.get("contents").toString()
                            dialog_text.text=uniqueQuestion
                            Log.d("통과했는데", "${uniqueQuestion}");
                            break
                        }

                    }

                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }

}






