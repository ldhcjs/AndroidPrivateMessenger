package com.ldhcjs.androidprivatemessenger

import java.text.SimpleDateFormat
import java.util.*

class Util {
    companion object {
        fun getCurrentTime(): String {
            val sdf = SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS", Locale.KOREA)
            return sdf.format(System.currentTimeMillis())
        }
        fun getCurrentHour(): String {
            val sdf = SimpleDateFormat("HH:mm", Locale.KOREA)
            return sdf.format(System.currentTimeMillis())
        }
    }
}