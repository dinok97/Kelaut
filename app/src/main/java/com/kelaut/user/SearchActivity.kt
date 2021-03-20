package com.kelaut.user

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.kelaut.user.adapter.ServiceAdapter
import com.kelaut.user.model.Service
import com.kelaut.user.utils.Constant.Collection.Companion.SERVICE
import com.kelaut.user.utils.DateUtils
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.btn_minus
import kotlinx.android.synthetic.main.activity_search.btn_plus
import kotlinx.android.synthetic.main.activity_search.tv_quantity
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var date: Date
    private var qty: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        init()
    }

    private fun init(){
        initObject()
        initEventUI()
    }

    private fun initUI(){

    }

    private fun initObject(){

        recyclerView = findViewById(R.id.rv_search_result)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

    }

    private fun initEventUI(){

        tv_select_date.setOnClickListener {
            showDatePickerDialog()
        }

        btn_plus.setOnClickListener {
            ++qty
            tv_quantity.text = qty.toString().trim()
        }

        btn_minus.setOnClickListener {
            if (qty == 1) {
                Toast.makeText(this, "Tidak boleh kurang dari satu", Toast.LENGTH_SHORT).show()
            } else {
                --qty
                tv_quantity.text = qty.toString().trim()
            }
        }

        btn_search.setOnClickListener {
            loadServicesData()
        }

        iv_back.setOnClickListener {
            finish()
        }

    }

    private fun loadServicesData() {
        FirebaseFirestore.getInstance().collection(SERVICE).get()
            .addOnSuccessListener { documents ->
                val serviceList: ArrayList<Service> = ArrayList()
                for (document in documents) {
                    val service: Service = document.toObject(Service::class.java)
                    service.Id = document.id
                    serviceList.add(service)
                }
                fillServiceToLayout(serviceList)
            }.addOnFailureListener {

            }
    }

    private fun fillServiceToLayout(serviceList: ArrayList<Service>){
        val adapter = ServiceAdapter(this, serviceList)
        adapter.setPersonCount(qty)
        adapter.setUseAt(date)
        recyclerView.adapter = adapter
    }

    private fun showDatePickerDialog() {
        val newCalendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val tempDate = Calendar.getInstance()
                tempDate[year, monthOfYear] = dayOfMonth
                date = tempDate.time
                tv_select_date.text = DateUtils.getStringFormatedDate(date)
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }
}