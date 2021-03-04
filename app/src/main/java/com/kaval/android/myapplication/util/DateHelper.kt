package com.kaval.android.myapplication.util

import java.sql.Date
import java.text.SimpleDateFormat

class DateHelper {

    fun convertLongToTime(time: Long?): String {
        val date = Date(time!!)
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm:ss")
        return format.format(date)
    }

}