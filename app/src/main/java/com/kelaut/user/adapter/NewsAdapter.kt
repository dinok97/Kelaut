package com.kelaut.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelaut.user.R
import com.kelaut.user.model.News
import com.kelaut.user.utils.DateUtils

class NewsAdapter(
    private val context: Context,
    private val newsList: ArrayList<News>
) : RecyclerView.Adapter<NewsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rv_item_news, parent, false
            )
        )
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.tvNewsTitle.text = newsList[position].title
        holder.tvNewsDate.text = DateUtils.getStringFormatedDate(newsList[position].writeAt)
        holder.tvNewsWriter.text = newsList[position].writer
        Glide.with(context).load(newsList[position].imageUrl).into(holder.ivNews)
        holder.cvNews.setOnClickListener(onClickListener(position))
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvNews: ConstraintLayout = itemView.findViewById(R.id.cl_item_news)
        val ivNews: ImageView = itemView.findViewById(R.id.iv_news_image)
        val tvNewsTitle: TextView = itemView.findViewById(R.id.tv_news_title)
        val tvNewsDate: TextView = itemView.findViewById(R.id.tv_news_date)
        val tvNewsWriter: TextView = itemView.findViewById(R.id.tv_news_writer)
    }

    /*this method must refactor to view task*/
    private fun onClickListener(position: Int): View.OnClickListener {
        return View.OnClickListener {
            Toast.makeText(context, newsList[position].title, Toast.LENGTH_SHORT).show()
        }
    }

}