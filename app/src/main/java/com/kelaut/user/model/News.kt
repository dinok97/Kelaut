package com.kelaut.user.model

import com.google.firebase.firestore.Exclude
import java.util.*


data class News(
    @get:Exclude var newsId: String = "",
    val title: String = "",
    val writer: String = "",
    val contentUrl: String = "",
    val imageUrl: String = "",
    val writeAt: Date = Calendar.getInstance().time
)