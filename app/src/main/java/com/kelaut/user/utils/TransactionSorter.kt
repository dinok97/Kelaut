package com.kelaut.user.utils

import com.kelaut.user.model.Transaction

class TransactionSorter {
    companion object{
        @JvmStatic
        fun sort(tranList: ArrayList<Transaction>): ArrayList<Transaction>{
            val list = tranList.sortedWith(compareBy{it.useAt})
            tranList.removeAll(tranList)
            tranList.addAll(list)
            return tranList
        }
    }
}