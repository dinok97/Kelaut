package com.kelaut.user.utils

interface Constant {

    interface Collection {
        companion object{
            val USER = "user"
            val PROMOTION = "promotion"
            val SERVICE = "service"
            val TRANSACTION = "transaction"
            val NEWS = "news"
        }
    }

    interface Progress {
        companion object {
            val SUBMITED = "submited"
            val APPROVED = "approved"
            val DONE = "done"
        }
    }

}