package com.kelaut.user

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kelaut.user.model.Service
import com.kelaut.user.model.Transaction
import com.kelaut.user.model.User
import com.kelaut.user.utils.ConfirmationDialog
import com.kelaut.user.utils.Constant.Collection.Companion.SERVICE
import com.kelaut.user.utils.Constant.Collection.Companion.TRANSACTION
import com.kelaut.user.utils.Constant.Collection.Companion.USER
import com.kelaut.user.utils.Constant.Progress.Companion.SUBMITED
import kotlinx.android.synthetic.main.activity_booking.*
import kotlinx.android.synthetic.main.activity_booking.progress_bar
import kotlinx.android.synthetic.main.activity_booking.tv_location
import kotlinx.android.synthetic.main.activity_booking.tv_phone_number
import java.util.*

class BookingActivity : AppCompatActivity() {

    companion object {
        const val SERVICE_ID = "serviceId"
        const val PERSON_COUNT = "personCount"
        const val USE_AT = "useAt"
    }

    private lateinit var serviceId: String
    private lateinit var userId: String
    private lateinit var user: User
    private lateinit var service: Service

    private lateinit var useAt: Date
    private var personCount: Int = 0

    private var qty: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking)

        init()

    }

    private fun init (){
        initObject()
        initEventUI()
    }

    private fun initObject() {
        userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        serviceId = intent.getStringExtra(SERVICE_ID) ?: ""
        personCount = intent.getIntExtra(PERSON_COUNT, 0)
        useAt = Date(intent.getLongExtra(USE_AT, -1))
        initServiceData()
    }

    private fun initUI() {
        tv_service_name.text = service.name
        tv_rates.text = service.rating.toString()
        tv_service_owner.text = service.fishermanName
        tv_phone_number.text = service.phoneNumber
        tv_location.text = service.location.detail
        tv_desc.text = service.description
        tv_additional_service.text = service.additionalService
        tv_operational_schedule.text = service.operationalSchedule
        tv_price.text = service.price.toString()
        tv_price_desc.text = service.priceDescription
        Glide.with(this).load(service.imageURL).into(iv_service_image)
    }

    private fun initEventUI() {

        btn_plus.setOnClickListener {
            ++qty
            tv_quantity.text = qty.toString().trim()
        }

        btn_minus.setOnClickListener {
            if (qty == 1) {
                Toast.makeText(this, "Tidak boleh kurang dari satu", Toast.LENGTH_SHORT).show()
                Log.d("QTY", qty.toString())
            } else {
                --qty
                tv_quantity.text = qty.toString().trim()
            }
        }

        btn_booking.setOnClickListener {
            ConfirmationDialog.Builder(this)
                .setTitle("Info")
                .setDescription("Apakah Anda sudah yakin dengan pemesanan ini?")
                .setOkText("Oke")
                .setCancelText("Batal")
                .setListener(object : ConfirmationDialog.ConfirmationDialogListener{
                    override fun setOnOkListener() {
                        val transaction = generateTransaction()
                        saveTransactionToFirebase(transaction)
                    }
                    override fun setOnCancelListener() {

                    }
                })
                .build()
                .show()
        }

    }

    private fun initServiceData(){
        progress_bar.visibility = View.VISIBLE
        FirebaseFirestore.getInstance().collection(SERVICE).document(serviceId).get()
            .addOnSuccessListener {
                if (it != null) {
                    service = it.toObject(Service::class.java)!!
                    service.Id = it.id
                    initUI()
                } else {
                    Log.d("DETAIL-POST", "No Service data")
                }
                progress_bar.visibility = View.GONE
                loadUserInformation(userId)
            }.addOnFailureListener {
                progress_bar.visibility = View.GONE
                Log.e("DETAIL-POST", it.message?: "")
            }
    }

    private fun loadUserInformation(userId: String?) {
        val firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection(USER).document(userId ?: "user").get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    user = document.toObject(User::class.java)!!
                } else {
                    Log.d("USER-DATA", "fail to catch user data")
                }
            }.addOnFailureListener {
                Log.d("USER-DATA", "failure catch data from firebase")
            }
    }

    private fun saveTransactionToFirebase(transaction: Transaction) {
        progress_bar.visibility = View.VISIBLE
        FirebaseFirestore.getInstance().collection(TRANSACTION).add(transaction)
            .addOnSuccessListener {
                progress_bar.visibility = View.GONE
                Toast.makeText(this, "success save transaction", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                progress_bar.visibility = View.GONE
            }
    }

    private fun generateTransaction(): Transaction{
        return Transaction(
            "tranId",
            userId,
            "service.fishermanId",
            serviceId,
            service.name,
            service.price,
            service.priceDescription,
            service.imageURL,
            service.location,
            useAt,
            0,
            et_message_to_owner.text.toString(),
            false,
            SUBMITED,
            Calendar.getInstance().time,
            Calendar.getInstance().time
        )
    }

}