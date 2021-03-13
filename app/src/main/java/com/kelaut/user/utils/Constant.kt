package com.kelaut.user.utils

interface Constant {

    interface Collection {
        companion object{
            val USER = "user"
            val TRANSACTION = "transaction"
            val TRANSACTION_BUNDLE = "transactionBundle"
            val ITEM = "item"
            val BANNER_IMAGES = "bannerImages"
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