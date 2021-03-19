package com.kelaut.user.model

import com.google.firebase.firestore.Exclude
import java.util.*

data class Service(
    @get:Exclude var Id: String = "",
    val owner: String = "",
    val name: String = "",
    val type: String = "",
    val description: String = "",
    val imageURL: String = "",
    val additionalService: String = "",

    val price: Int = 0,
    val priceDescription: String = "",

    val phoneNumber: String = "",
    val location: Location = Location(),

    val operationalSchedule: String = "",
    val isAvailable: Boolean = true,
    val rating: Double = 0.0,
    val operationalCount: Int = 0,

    val createAt: Date = Calendar.getInstance().time,
    val updateAt: Date = Calendar.getInstance().time
)