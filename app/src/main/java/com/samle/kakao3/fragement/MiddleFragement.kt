package com.example.bottomnavi

import android.content.Context
import android.media.midi.MidiDevice
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.btnnavirecycler.Adater.Memo
import com.example.btnnavirecycler.Adater.RecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.samle.kakao3.LoginActivity
import com.samle.kakao3.MyCustomDialog
import com.samle.kakao3.R

class MiddleFragement : Fragment() {

    lateinit var recyclerView: RecyclerView
    var modelList = ArrayList<Memo>()
    var db = FirebaseFirestore.getInstance()

    companion object{
        const val TAG : String = "로그"

        fun newInstance() : MiddleFragement{
            return MiddleFragement()
        }
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
            Memo(R.mipmap.ic_launcher, "안녕"),
            Memo(R.mipmap.ic_launcher, "안녕"),
            Memo(R.mipmap.ic_launcher, "안녕"),
            Memo(R.mipmap.ic_launcher, "안녕"),
            Memo(R.mipmap.ic_launcher, "안녕")

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
        recyclerView.adapter = RecyclerAdapter(RecoList)

        loadData()
        return view
    }

    fun loadData() {
        db.collection("answer").document(LoginActivity.currentUserEmail)
            .get().addOnCompleteListener {task->
                if (task.isSuccessful){
                    db.collection("questions").get().addOnSuccessListener {
                        for(document in it.documents){
                            if(task.result?.get(document.get("contents").toString()) !=null ){
                                Log.d("값 존재",task.result?.get(document.get("contents").toString()).toString()!!)
                                var map:Map<String,String>
                                map= task.result?.get(document.get("contents").toString()) as Map<String, String>
                                Log.d("answer 값 ",map["answer"].toString())
                                Log.d("질문 값 나오기 ",map["content"].toString())
                            }
                        }
                    }
                }
            }
    }

}

