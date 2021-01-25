package com.example.bottomnavi

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btnnavirecycler.Adater.RecyclerAdapter
import com.example.btnnavirecycler.Adater.eachData
import com.google.api.LogDescriptor
import com.google.firebase.firestore.FirebaseFirestore
import com.samle.kakao3.Adater.SwipeToDelete
import com.samle.kakao3.LoginActivity
import com.samle.kakao3.R
import kotlinx.android.synthetic.main.custom_dialog.*
import kotlinx.android.synthetic.main.fragement_middle.view.*

//알겠습니다.

class MiddleFragement : Fragment() {

    lateinit var recyclerView: RecyclerView
    var memory = mutableListOf<eachData>()

    var db = FirebaseFirestore.getInstance()
    lateinit var viewTmp:View
    companion object{
        const val TAG : String = "로그"
        var check : Int = 0
        val adapter = RecyclerAdapter()
        var currentExp : Int = 0
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
        viewTmp=view
        recyclerView = view.findViewById(R.id.recylerView)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        db.collection("answer").document(LoginActivity.currentUserEmail).get().addOnCompleteListener {
            if(it.isSuccessful){
                Log.d("에러에ㅓㄹ어러","addOnCompleteListener${it!!.result}")
            }
        }



        adapter.modelList.clear()
        db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("성공성공", document.id + " => " + document.data)
                        var img = R.drawable.profile
                        var answer = document.data.get("answer").toString()
                        var question = document.data.get("contents").toString()
                      if(!data.contains(question)){
                            memory.add(eachData(img, question, answer))
                            data.add(question)
                        }

                        adapter.modelList.add(eachData(img, question, answer))

                    }
                } else {
                    Log.w("실패실패", "Error getting documents.", task.exception)
                }
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(context)

                swipToDelete()
            }


        restart()
        return view
    }
    private fun swipToDelete() {
        var itemTouchHelper = ItemTouchHelper(SwipeToDelete(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun restart(){
        viewTmp.swipeToRefresh.setOnRefreshListener {
            adapter.modelList.clear()
            db.collection("answer").document(LoginActivity.currentUserEmail).collection("userData")
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result!!) {
                            Log.d("성공성공", document.id + " => " + document.data)
                            var img = R.drawable.profile
                            var answer = document.data.get("answer").toString()
                            var question = document.data.get("contents").toString()
                            if(!data.contains(question)){
                                memory.add(eachData(img, question, answer))
                                data.add(question)
                            }

                            adapter.modelList.add(eachData(img, question, answer))
                            viewTmp.swipeToRefresh.isRefreshing=false
                        }
                    } else {
                        Log.w("실패실패", "Error getting documents.", task.exception)
                    }
                    recyclerView.adapter = adapter
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    swipToDelete()
                }


        }
    }

}