package com.samle.kakao3.Adater

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavi.MiddleFragement
import com.example.btnnavirecycler.Adater.RecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.samle.kakao3.LoginActivity

class SwipeToDelete (var adapter : RecyclerAdapter) : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
    var db = FirebaseFirestore.getInstance()
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        var pos = viewHolder.adapterPosition
        db.collection("answer").document(LoginActivity.currentUserEmail)
            .collection("userData").document(adapter.modelList[pos].user_question).delete()
        adapter.deleteItem(pos)
        db.collection("user").document(LoginActivity.currentUserEmail).get().addOnSuccessListener {
            MiddleFragement.currentExp = it.data!!.get("exp").toString().toInt()
            Log.d("찍히는지","currentExp")
            var map= mutableMapOf<String,Any>()
            map["exp"] = MiddleFragement.currentExp - 5
            db.collection("user").document(LoginActivity.currentUserEmail).update(map).
            addOnCompleteListener{
                if(it.isSuccessful){

                }
            }


        }
    }
}