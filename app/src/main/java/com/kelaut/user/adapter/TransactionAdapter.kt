package com.kelaut.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kelaut.user.R
import com.kelaut.user.model.Transaction
import com.kelaut.user.utils.DateUtils

class TransactionAdapter(
    private val context: Context,
    private val transactionList: ArrayList<Transaction>
) : RecyclerView.Adapter<TransactionAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.rv_item_transaction, parent, false
            )
        )
    }

    override fun getItemCount(): Int = transactionList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val price = "Rp ${transactionList[position].servicePrice}"
        val transactionDate = DateUtils.getStringFormatedDate(transactionList[position].useAt)

        holder.tvCustomerName.text = transactionList[position].serviceName
        holder.tvTranDate.text = transactionDate
        holder.tvTranPrice.text = price
        holder.cvTransaction.setOnClickListener(onClickListener(position))
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cvTransaction: CardView = itemView.findViewById(R.id.cv_transaction_item)
        val tvCustomerName: TextView = itemView.findViewById(R.id.tv_service_name)
        val tvTranDate: TextView = itemView.findViewById(R.id.tv_transaction_date)
        val tvTranPrice: TextView = itemView.findViewById(R.id.tv_transaction_price)
    }

    /*this method must refactor to view task*/
    private fun onClickListener(position: Int): View.OnClickListener {
        return View.OnClickListener {
            Toast.makeText(context, transactionList[position].serviceName, Toast.LENGTH_SHORT).show()
        }
    }
}