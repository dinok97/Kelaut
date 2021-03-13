package com.kelaut.user.utils

import com.google.common.io.ByteStreams
import java.math.BigInteger
import java.security.MessageDigest

class MD5 {
    companion object{
        @JvmStatic
        fun encript(string: String): String{
            val md = MessageDigest.getInstance("MD5")
            return BigInteger(1, md.digest(ByteStreams.toByteArray(string.byteInputStream()))).toString(16).padStart(30,'0')
        }
    }
}