package com.restauran.utils

import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {
    private val GMTDateTimeFormatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US)
    private val LocalDateTimeFormatter = SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US)
    private val LocalDateTimeFormatter1 = SimpleDateFormat("dd MMM EEE", Locale.US)
    private val LocalDateTimeFormatter2 = SimpleDateFormat("dd MMM yyyy", Locale.US)
    private val LocalDateTimeFormatter3 = SimpleDateFormat("hh:mm a, dd MMM yyyy", Locale.US)
    private val LocalTimeFormatter = SimpleDateFormat("hh:mm a", Locale.US)
    private val GMTDateFormatter =
        SimpleDateFormat("yyyy-MM-dd", Locale.US).setTimeZone(TimeZone.getTimeZone("GMT"))
    private var GMTTimeFormatter =
        SimpleDateFormat("hh:mm:ss a", Locale.US).setTimeZone(TimeZone.getTimeZone("GMT"))
    private val TAG = TimeUtils::class.java.simpleName

    //from server expected dd/MM/yyyy hh:mm:ss a
    fun getLocalTimeString(strGMTTime: String?): String? {
        var strLocalTime: String? = null
        try {
            GMTDateTimeFormatter.timeZone = TimeZone.getTimeZone("GMT")
            val date = GMTDateTimeFormatter.parse(strGMTTime)
            LocalDateTimeFormatter.timeZone = TimeZone.getDefault() //change as view data in app
            strLocalTime = LocalDateTimeFormatter.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strLocalTime
    }

    //from server expected dd/MM/yyyy hh:mm:ss a
    fun getCreateDateFormat(strGMTTime: String?): String? {
        var strLocalTime: String? = null
        try {
            GMTDateTimeFormatter.timeZone = TimeZone.getTimeZone("GMT")
            val date = GMTDateTimeFormatter.parse(strGMTTime)
            LocalDateTimeFormatter1.timeZone = TimeZone.getDefault() //change as view data in app
            strLocalTime = LocalDateTimeFormatter1.format(date)
            //            Log.e("strLocalTime: ",strLocalTime);
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strLocalTime
    }

    //from server expected dd/MM/yyyy hh:mm:ss a
    fun getCreateDateFormat2(strGMTTime: String?): String? {
        var strLocalTime: String? = null
        try {
            GMTDateTimeFormatter.timeZone = TimeZone.getTimeZone("GMT")
            val date = GMTDateTimeFormatter.parse(strGMTTime)
            LocalDateTimeFormatter2.timeZone = TimeZone.getDefault() //change as view data in app
            strLocalTime = LocalDateTimeFormatter2.format(date)
            //            Log.e("strLocalTime: ",strLocalTime);
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strLocalTime
    }

    fun getCreateDateFormat3(strGMTTime: String?): String? {
        var strLocalTime: String? = null
        try {
            GMTDateTimeFormatter.timeZone = TimeZone.getTimeZone("GMT")
            val date = GMTDateTimeFormatter.parse(strGMTTime)
            LocalDateTimeFormatter3.timeZone = TimeZone.getDefault() //change as view data in app
            strLocalTime = LocalDateTimeFormatter3.format(date)
            //            Log.e("strLocalTime: ",strLocalTime);
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strLocalTime
    }

    fun getNotificationTime(strGMTTime: String?): String? {
        var strLocalTime: String? = null
        try {
            GMTDateTimeFormatter.timeZone = TimeZone.getTimeZone("GMT")
            val date = GMTDateTimeFormatter.parse(strGMTTime)
            LocalTimeFormatter.timeZone = TimeZone.getDefault() //change as view data in app
            strLocalTime = LocalTimeFormatter.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return strLocalTime
    }

    fun getCreateDateFormat(date: Date?): String? { // expected dd/MM/yyyy hh:mm:ss a
        var strLocalTime: String? = ""
        strLocalTime = LocalDateTimeFormatter1.format(date)
        return strLocalTime
    }

    fun getDateByString(dateStr: String?, format: String?): Date? {
        var date: Date? = null
        val simpleDateFormat = SimpleDateFormat(format, Locale.ENGLISH)
        try {
            date = simpleDateFormat.parse(dateStr)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return date
    }
}