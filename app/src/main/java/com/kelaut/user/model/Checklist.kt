package com.kelaut.user.model

import com.google.firebase.firestore.Exclude

data class Checklist (
    @get:Exclude var id: String = "",
    val title: String = "",
    val content: String = ""
)