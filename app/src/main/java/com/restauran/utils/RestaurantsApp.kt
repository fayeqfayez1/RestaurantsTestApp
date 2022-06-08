package com.restauran.utils

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication

class RestaurantsApp : MultiDexApplication() {
    companion object {
        private var instance: RestaurantsApp? = null
        fun getInstance(): RestaurantsApp? = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }
}