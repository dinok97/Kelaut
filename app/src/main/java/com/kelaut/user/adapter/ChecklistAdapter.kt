package com.kelaut.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kelaut.user.R
import com.kelaut.user.model.Checklist

class ChecklistAdapter(
    private val context: Context,
    private val checkList: ArrayList<Checklist>
): RecyclerView.Adapter<ChecklistAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rv_item_checklist, parent, false
            )
        )
    }

    override fun getItemCount(): Int = checkList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.tvTitle.text = checkList[position].title
        holder.tvContent.text = checkList[position].content
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_checklist_title)
        val tvContent: TextView = itemView.findViewById(R.id.tv_checklist_content)
    }

}