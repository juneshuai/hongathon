package com.example.bottomnavi

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btnnavirecycler.Adater.RecyclerAdapter
import com.example.btnnavirecycler.Adater.eachData
import com.google.api.LogDescriptor
import com.google.firebase.firestore.FirebaseFirestore
import com.samle.kakao3.LoginActivity
import com.samle.kakao3.R
import kotlinx.android.synthetic.main.custom_dialog.*
//알겠습니다.

class MiddleFragement : Fragment() {

    lateinit var recyclerView: RecyclerView
    var memory = mutableListOf<eachData>()

    var db = FirebaseFirestore.getInstance()
    companion object{
        const val TAG : String = "로그"
        var check : Int = 0
        val adapter = RecyclerAdapter()
        fun newInstance() : MiddleFragement{
            return MiddleFragement()
        }
        var data =ArrayList<String>()
    }
    //메모리에 올라갈때
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"ProfileFragement - onCreate() called")

    }
    //메인엑티비에 붇음
    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG,"ProfileFragement - onAttach() called")
    }


    val RecoList = arrayListOf(
        eachData(R.mipmap.ic_launcher, "안녕","반갑다")


    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG,"ProfileFragement - onCreateView() called")
        val view = inflater.inflate(R.layout.fragement_middle, container, false)
        recyclerView = view.findViewById(R.id.recylerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        db.collection("answer").document(LoginActivity.currentUserEmail).get().addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("에러에ㅓㄹ어러","addOnCompleteListener${it!!.result}")
            }
        }


        //파이어베이스 추가
        /*db.collection("answer").document(LoginActivity.currentUserEmail).get().addOnSuccessListener {
            if (it != null) {
                Log.d(TAG, "DocumentSnapshot data: ${it.data!!.toMutableMap()}")
                Log.d(TAG, "tqtqtqtq${it.data!!.toMutableMap().get("가장 좋아하는 액세서리는?") is MutableList<String, String>}");




            } else {
                Log.d(TAG, "No such document")
            }
        }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }*/

        db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("성공성공", document.id + " => " + document.data)
                        var img = R.drawable.profile
                        var answer = document.data.get("answer").toString()
                        var question = document.data.get("contents").toString()
                        Log.d(TAG, "확인 : ${document.data.get("index").toString().toInt()}");
                        Log.d(TAG, "메모리 확인 : ${memory.size}");
                        Log.d(TAG, "메모리 확인 : ${document.data.get("index").toString().toInt() == memory.size-1}");
                        if(!data.contains(question)){

                            memory.add(eachData(img, question, answer))
                            data.add(question)
                        }



                    }
                } else {
                    Log.w("실패실패", "Error getting documents.", task.exception)
                }
            }




        Handler().postDelayed(
            {
                adapter.modelList.addAll(memory)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)

            }
            ,
            2000)


        recyclerView.setOnClickListener{

        }

        return view
    }

}