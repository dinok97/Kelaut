package com.kelaut.user.model

import com.google.firebase.firestore.Exclude
import com.kelaut.user.utils.Constant.Progress.Companion.SUBMITED
import java.util.*

data class Transaction (
    @get:Exclude var transactionId: String = "",
    val userName: String = "",
    val userEmail: String = "",
    val userPhoneNumber: String = "",

    val fishermanName: String = "",
    val fishermanPhoneNumber: String = "",

    val serviceName: String = "",
    val servicePrice: Int = 0,
    val servicePriceDesc: String = "",
    val serviceImageUrl: String = "",
    val serviceLocation: String = "",

    val useAt: Date = Calendar.getInstance().time,
    val personCount: Int = 0,
    val note: String = "",

    var isDOne: Boolean = false,
    var progress: String = SUBMITED,

    val createAt: Date = Calendar.getInstance().time,
    val doneAt: Date = Calendar.getInstance().time
)