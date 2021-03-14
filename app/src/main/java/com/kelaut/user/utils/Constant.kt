package com.kelaut.user.utils

interface Constant {

    interface Collection {
        companion object{
            val USER = "user"
            val PROMOTION = "promotion"
            val TRANSACTION = "transaction"
            val DATE_FORMAT = "dd/MM/yyyy"
        }
    }

    interface Field {
        companion object {
            val IS_AVAILABLE = "available"
            val DATE = "date"
            val USER_ID = "userId"
        }
    }

}