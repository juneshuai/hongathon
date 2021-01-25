package com.example.bottomnavi

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.btnnavirecycler.Adater.RecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.samle.kakao3.LoginActivity
import com.samle.kakao3.MyCustomDialog
import com.samle.kakao3.R
import kotlinx.android.synthetic.main.fragement_home.*
import kotlinx.android.synthetic.main.fragement_home.view.*
import kotlin.math.log
import kotlin.random.Random


class HomeFragement : Fragment() {




    var db = FirebaseFirestore.getInstance()
    companion object{
        var ListOf = listOf<Int>(R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d)
        const val TAG : String = "생명주기"
        var exp : Int = 0

        //자기 자신을 가져온다.
        fun newInstance() : HomeFragement{
            return HomeFragement()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("생명주기","HomeFragement - onCreate() called")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("생명주기","HomeFragement - onAttach() called")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("생명주기", "HomeFragement : onActivityCreated() called");
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d("생명주기","HomeFragement - onCreateView() called")

        val view = inflater.inflate(R.layout.fragement_home, container, false)
        view.dialog_button.setOnClickListener {
            MyCustomDialog(view.context).show()

            Log.d("생명주기","커스텀 다이어로그")

            db.collection("answer")
                .get()
                .addOnCompleteListener{
                    if(it.isSuccessful)
                    if(it.result != null){
                        db.collection("answer").document(LoginActivity.currentUserEmail)
                            .get()
                            .addOnCompleteListener {
                                Log.d("확인해야함", "처음 만들어지나 ")
                    }


                }
        }





                }
        db.collection("user").document(LoginActivity.currentUserEmail).get().addOnSuccessListener {
            Log.d("값확인", "${it.data!!.get("exp").toString().toInt()}");
            exp = it.data!!.get("exp").toString().toInt()
            Log.d("생명주기", "exp${exp}");


        }



        //view.progress_Bar.setProgress(MyCustomDialog.currentExp)

        view.progress_Bar.setProgress(exp%20)
        if(exp%20 == 0){
            Glide.with(this).load(ListOf.get(1)).into(view.homeImg)
        }else{
            val random = Random(System.currentTimeMillis())
            val num = random.nextInt(3)+1
            Log.d("난수값", "${num}")
            Glide.with(this).load(ListOf.get(num)).into(view.homeImg)
        }




        return view
    }


    override fun onPause() {
        super.onPause()
        Log.d("생명주기", "HomeFragement : onPause() called");
    }

    override fun onStop() {
        super.onStop()
        Log.d("생명주기", "HomeFragement : onStop() called");
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("생명주기", "HomeFragement : onDestroyView() called");
    }
}