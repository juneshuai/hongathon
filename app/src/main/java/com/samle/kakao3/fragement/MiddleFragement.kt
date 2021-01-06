package com.example.bottomnavi

import android.content.Context
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
import com.samle.kakao3.R

class MiddleFragement : Fragment() {

    lateinit var recyclerView: RecyclerView
    var modelList = ArrayList<Memo>()

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


        return view
    }

}