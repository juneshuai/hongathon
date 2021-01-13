package com.samle.kakao3
import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.samle.kakao3.R
import kotlinx.android.synthetic.main.layout_intro_pager_item.view.*

class MyIntroPagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val itemImage = itemView.pager_item_image
    private val itemContent = itemView.pager_item_text
    private val itemBg = itemView.pager_item_bg

    fun bindWithView(pageItem: PageItem){
        itemImage.setImageResource(pageItem.imageSrc)
        itemContent.text = pageItem.content

        if(pageItem.bgColor != R.color.white){
            itemContent.setTextColor(Color.WHITE)
        }

        itemBg.setBackgroundResource(pageItem.bgColor)
    }

}
