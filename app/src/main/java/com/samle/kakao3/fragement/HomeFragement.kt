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
import com.google.firebase.firestore.FirebaseFirestore
import com.samle.kakao3.LoginActivity
import com.samle.kakao3.MyCustomDialog
import com.samle.kakao3.R
import kotlinx.android.synthetic.main.fragement_home.*
import kotlinx.android.synthetic.main.fragement_home.view.*


class HomeFragement : Fragment() {




    var db = FirebaseFirestore.getInstance()
    companion object{
        const val TAG : String = "로그"
        var previousExp : Int = 0
        var exp : Int = 0
        //자기 자신을 가져온다.
        fun newInstance() : HomeFragement{
            return HomeFragement()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"HomeFragement - onCreate() called")

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"HomeFragement - onAttach() called")
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG,"HomeFragement - onCreateView() called")

        val view = inflater.inflate(R.layout.fragement_home, container, false)
        view.dialog_button.setOnClickListener {
            MyCustomDialog(view.context).show()

            db.collection("answer")
                .get()
                .addOnCompleteListener{
                    if(it.isSuccessful )
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


        }
        Handler().post(Runnable { view.progress_Bar.setProgress(MyCustomDialog.currentExp) })
        //view.progress_Bar.setProgress(MyCustomDialog.currentExp)
        if(exp == 20){
            view.progress_Bar.setProgress(0)
            view.homeImg.setImageResource(R.drawable.c)
            MyCustomDialog.currentExp = 0
        }

        /*view.swipeToRefresh.setOnRefreshListener {
            Toast.makeText(activity,"새로고침",Toast.LENGTH_SHORT).show()
            view.progress_Bar.setProgress(exp)
            swipeToRefresh.isRefreshing = false
        }*/

        Glide.with(this).load(R.drawable.a).into(view.homeImg)

        return view
    }






}