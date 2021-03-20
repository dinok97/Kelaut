package com.kelaut.user.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kelaut.user.BookingActivity
import com.kelaut.user.R
import com.kelaut.user.model.Service
import java.util.*
import kotlin.collections.ArrayList

class ServiceAdapter(
    private val context: Context,
    private val serviceList: ArrayList<Service>
) : RecyclerView.Adapter<ServiceAdapter.ItemViewHolder>() {

    var useAt: Date = Calendar.getInstance().time
        private set

    var personCount: Int = 0
        private set

    fun setUseAt(_useAt: Date) = apply { this.useAt = _useAt }
    fun setPersonCount(_personCount: Int) = apply { this.personCount = _personCount }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rv_item_service, parent, false
            )
        )
    }

    override fun getItemCount(): Int = serviceList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        holder.tvServiceName.text = serviceList[position].name
        holder.tvLocation.text = serviceList[position].location.detail
        holder.tvOwner.text = serviceList[position].fishermanName
        holder.tvRates.text = serviceList[position].rating.toString()
        Glide.with(context).load(serviceList[position].imageURL).into(holder.ivService)
        holder.cvService.setOnClickListener(onClickListener(position))
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvService: ConstraintLayout = itemView.findViewById(R.id.cl_item_service)
        val ivService: ImageView = itemView.findViewById(R.id.iv_service_image)
        val tvServiceName: TextView = itemView.findViewById(R.id.tv_service_name)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_location)
        val tvOwner: TextView = itemView.findViewById(R.id.tv_service_owner)
        val tvRates: TextView = itemView.findViewById(R.id.tv_service_rates)
    }

    /*this method must refactor to view task*/
    private fun onClickListener(position: Int): View.OnClickListener {
        return View.OnClickListener {
            context.startActivity(
                Intent(context, BookingActivity::class.java)
                    .putExtra(BookingActivity.SERVICE_ID, serviceList[position].Id)
                    .putExtra(BookingActivity.USE_AT, useAt.time)
                    .putExtra(BookingActivity.PERSON_COUNT, personCount)
            )
        }
    }

}