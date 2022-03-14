package com.ldhcjs.androidprivatemessenger.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ldhcjs.androidprivatemessenger.R
import com.ldhcjs.androidprivatemessenger.databinding.ItemChatBinding

class ChatAdpater(private val dataSet: Array<String>) :
    RecyclerView.Adapter<ChatAdpater.BindingViewHolder>() {

    class BindingViewHolder(val binding: ItemChatBinding) :
        RecyclerView.ViewHolder(binding.root)
    //binding의 타입은 item ItemChatBinding 이유는 리사이클러뷰에 각각 하나씩 들어가는 itemlayout의 이름이 item_gallery 이기 때문.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat, parent, false)
        return BindingViewHolder(ItemChatBinding.bind(view))
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.binding.tvTitle.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
/*
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // TODO("make layout")
        val tv_title: TextView = view.findViewById(R.id.tv_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatAdpater.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.rv_content, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_title.text = dataSet[position]
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
 */
}