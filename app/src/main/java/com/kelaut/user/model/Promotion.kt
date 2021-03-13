package com.kelaut.user.model

import com.google.firebase.firestore.Exclude

data class Promotion(
    @get:Exclude var promotionId: String = "",
    val title: String = "",
    val desc: String = "",
    val imageUrl: String = ""
)