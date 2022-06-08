package com.restauran.utils

import android.content.Context
import com.google.gson.Gson
import java.util.*

object AppSharedData {
    private const val SHARED_APP_DATA = "app_data"
    private const val SHARED_USER_DATA = "user"
    private const val SHARED_SETTINGS = "settings"
    private const val SHARED_IS_APP_OPENED_BEFORE = "is_app_opened_before"
    private const val SHARED_IS_USER_LOGIN_BY_SOCIAL = "login_social"
    private const val SHARED_IS_USER_LOGIN = "is_user_login"
    private const val SHARED_LAT = "lat"
    private const val SHARED_FCM_TOKEN = "fcmToken"
    private const val SHARED_LNG = "lng"
    private const val DEVICE_ID = "device_id"
    private const val PASSWORD = "password"
    private const val SHARED_BADGE_COUNT = "BadgeCount"
    private const val SHARED_LANGUAGE = "lang"
    private const val SHARED_NOTIFICATION_COUNT = "NotificationCount_"
    private val gson = Gson()

    fun isOpenBeforeThat(): Boolean? {
        return RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)
            ?.getBoolean(SHARED_IS_APP_OPENED_BEFORE, false)
    }

    fun setOpenBeforeThat(open: Boolean) {
        RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)?.edit()
            ?.putBoolean(SHARED_IS_APP_OPENED_BEFORE, open)?.apply()
    }

    fun isUserLogin(): Boolean? {
        return RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)
            ?.getBoolean(SHARED_IS_USER_LOGIN, false)
    }

    fun setUserLogin(login: Boolean) {
        RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)?.edit()
            ?.putBoolean(SHARED_IS_USER_LOGIN, login)?.apply()
    }

    fun isLoginBySocial(): Boolean? {
        return RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)
            ?.getBoolean(SHARED_IS_USER_LOGIN_BY_SOCIAL, false)
    }

    fun setLoginBySocial(login: Boolean) {
        RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)?.edit()
            ?.putBoolean(SHARED_IS_USER_LOGIN_BY_SOCIAL, login)?.apply()
    }

    fun setLat(latitude: String?) {
        RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)?.edit()
            ?.putString(SHARED_LAT, latitude)?.apply()
    }

    fun getLat(): String? {
        return RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)
            ?.getString(SHARED_LAT, "")
    }

    fun getDeviceId(): String? {
        return RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)
            ?.getString(DEVICE_ID, "")
    }

    fun saveDeviceId(deviceID: String?) {
        RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)?.edit()
            ?.putString(DEVICE_ID, deviceID)?.apply()
    }

    fun setBadgeCount(userId: String, count: Int) {
        RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)?.edit()
            ?.putInt(SHARED_BADGE_COUNT + userId, count)?.apply()
    }

    fun getBadgeCount(userId: String): Int? {
        return RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)
            ?.getInt(SHARED_BADGE_COUNT + userId, 0)
    }

    fun saveLanguage(language: String?) {
        RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)?.edit()
            ?.putString(SHARED_LANGUAGE, language)?.apply()
    }

    fun getLanguage(): String? {
        var currentLanguage = Locale.getDefault().displayLanguage
        currentLanguage = if (currentLanguage == "العربية") {
            ConstantApp.AR_ISO
        } else {
            ConstantApp.EN_ISO
        }
        return RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)
            ?.getString(SHARED_LANGUAGE, currentLanguage)
    }

    fun setFcmToken(fcmToken: String?) {
        RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)?.edit()
            ?.putString(SHARED_FCM_TOKEN, fcmToken)?.apply()
    }

    fun getFcmToken(): String? {
        return RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)
            ?.getString(SHARED_FCM_TOKEN, "")
    }

    fun setNotificationCount(userId: Int, count: Int) {
        RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)?.edit()
            ?.putInt(SHARED_NOTIFICATION_COUNT + userId, count)?.apply()
    }

    fun getNotificationCount(userId: Int): Int? {
        return RestaurantsApp.getInstance()?.getSharedPreferences(SHARED_APP_DATA, Context.MODE_PRIVATE)
            ?.getInt(SHARED_NOTIFICATION_COUNT + userId, 0)
    }
}