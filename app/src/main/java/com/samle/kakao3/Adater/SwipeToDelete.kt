package com.samle.kakao3.Adater

import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
    }
}