package com.kelaut.user.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelaut.user.R
import com.kelaut.user.model.Promotion

class PromotionAdapter(
    private val context: Context,
    private val promotionList: ArrayList<Promotion>
) : RecyclerView.Adapter<PromotionAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rv_item_promotion, parent, false
            )
        )
    }

    override fun getItemCount(): Int = promotionList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.tvPromoTitle.text = promotionList[position].title
        holder.tvPromotDesc.text = promotionList[position].desc
        Glide.with(context).load(promotionList[position].imageUrl).into(holder.ivPromotion)
        holder.cvPromotion.setOnClickListener(onClickListener(position))
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvPromotion: CardView = itemView.findViewById(R.id.cv_promotion)
        val ivPromotion: ImageView = itemView.findViewById(R.id.iv_promotion)
        val tvPromoTitle: TextView = itemView.findViewById(R.id.tv_promotion_title)
        val tvPromotDesc: TextView = itemView.findViewById(R.id.tv_promotion_short_desc)
    }

    /*this method must refactor to view task*/
    private fun onClickListener(position: Int): View.OnClickListener {
        return View.OnClickListener {
            Toast.makeText(context, promotionList[position].title, Toast.LENGTH_SHORT).show()
        }
    }
}