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

        db.collection("questions")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {

                        if(document.get("isUsed")== true) continue
                        Log.d(TAG, "document.get(\"contents\").toString()${document.get("contents").toString()}");
                        Log.d(TAG,"document.id is String : ${document.id is String}")
                            document_Id = document.id
                            dialog_text.text = document.get("contents").toString()
                            if(document.get("isUsed")== false){
                                break
                        }


                    }

                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }




        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        save_btn.setOnClickListener {
            Toast.makeText(context,"저장",Toast.LENGTH_SHORT).show()
            Log.d(TAG, "what is ${document_Id}");
           // var map = mutableMapOf<ArrayList<String>, Any>()
//            map["isUsed"] = true
//            map["answer"] = edit_text.text.toString()
            lateinit var users: Array<Map<String,String>>



            db.collection("questions").document(document_Id).get().addOnCompleteListener {
                if (it.isSuccessful) {
                    users= it.result?.get("users") as Array<Map<String, String>>
                    }
            }
            var user= mutableMapOf<String,String>()
            user["answer"]=edit_text.text.toString()
            user["email"]=LoginActivity.currentUserEmail

            users[users.size]=user

            db.collection("questions").document(document_Id).update("users",users)
                    .addOnCompleteListener {

                    }


            dismiss()

        }
        cancel_btn.setOnClickListener {
            Toast.makeText(context,"취소",Toast.LENGTH_SHORT).show()
            dismiss()
        }
        setCancelable(false);


    }

}






