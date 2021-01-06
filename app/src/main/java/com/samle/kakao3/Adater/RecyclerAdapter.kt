package com.example.btnnavirecycler.Adater

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samle.kakao3.R

class RecyclerAdapter(var memo : ArrayList<Memo>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reclyer_data, parent,false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return memo.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.img.setImageResource(memo.get(position).user_img)
        holder.user_text.text = memo.get(position).user_text
    }


    class MyViewHolder(itemList:View) : RecyclerView.ViewHolder(itemList) {

        val img = itemList.findViewById<ImageView>(R.id.data_img)
        val user_text = itemList.findViewById<TextView>(R.id.data_text)

    }

}



data class Memo(var user_img: Int, var user_text: String) {
}
