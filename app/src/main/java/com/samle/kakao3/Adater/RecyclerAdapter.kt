package com.example.btnnavirecycler.Adater

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavi.MiddleFragement
import com.samle.kakao3.MyCustomDialog
import com.samle.kakao3.R
import com.samle.kakao3.UpdateDialog

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {


    var modelList = mutableListOf<eachData>()
    companion object{
        var cnt : Int = 0
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reclyer_data, parent,false)
        return MyViewHolder(view)

    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.img.setImageResource(modelList.get(position).user_img)
        holder.user_question.text = modelList.get(position).user_question
        holder.user_answer.text = modelList.get(position).user_answer

        holder.user_question.setOnClickListener {
            UpdateDialog(holder.user_question.context,holder.user_question.text.toString(),holder.user_answer.text.toString()).show()
        }
    }

    // 답변 추가
    class MyViewHolder(itemList:View) : RecyclerView.ViewHolder(itemList) {

        var img = itemList.findViewById<ImageView>(R.id.data_img)
        var user_question = itemList.findViewById<TextView>(R.id.data_text)
        var user_answer = itemList.findViewById<TextView>(R.id.answer_text) // 추가

    }

    fun deleteItem(pos:Int){
        modelList.removeAt(pos)
        notifyItemRemoved(pos)
    }

}



data class eachData(var user_img: Int, var user_question: String, var user_answer:String) {
}
