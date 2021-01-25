package com.samle.kakao3

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.bottomnavi.HomeFragement
import com.example.bottomnavi.MiddleFragement
import com.google.api.LogDescriptor
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.fragement_home.*


class MyCustomDialog(context: Context)
    : Dialog(context)
{
    private lateinit var homeFragement: HomeFragement
    val TAG: String = "로그"

    var db = FirebaseFirestore.getInstance()

    var uniqueQuestion : String = ""
    companion object{
        var currentExp : Int = 0
        var userContent = ArrayList<String>()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.custom_dialog)

        Log.d(TAG, "MyCustomDialog - onCreate() called")
        var check=true

        loadData()


        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //버튼 저장
        save_btn.setOnClickListener {

            val updateData= hashMapOf(
                "answer" to edit_text.text.toString(),
                "contents" to dialog_text.text.toString()

            )
            db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("사이즈 검색", it.result?.size().toString())
                        updateData["index"]= it.result?.size().toString()
                        MiddleFragement.check = 0
                    }
                }


            db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
                .get().addOnCompleteListener {
                    if (it.isSuccessful) {
                        db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
                            .document(dialog_text.text.toString()).set(updateData)
                            .addOnCompleteListener {
                                Log.d("확인해야함", "update로 들어감 ")

                            }

                    }
                }

            db.collection("user").document(LoginActivity.currentUserEmail)
                .get().addOnCompleteListener {
                    if(it.isSuccessful){
                        var exp= it.result?.get("exp").toString()
                        var tmp=exp.toInt()+5
                        db.collection("user").document(LoginActivity.currentUserEmail)
                            .update("exp",tmp)
                    }
                }

            currentExp = currentExp + 5
            Log.d("저장위치", "MyCustomDialog : onCreate() called");


            dismiss()
            Toast.makeText(context,"저장 완료",Toast.LENGTH_SHORT).show()


        }
        cancel_btn.setOnClickListener {
            Toast.makeText(context,"취소",Toast.LENGTH_SHORT).show()
            dismiss()
        }
        setOnDismissListener {


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






