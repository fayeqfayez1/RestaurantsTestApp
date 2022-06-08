package com.restauran.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.WindowManager

class ScreenUtils(var ctx: Context) {
    private var metrics: DisplayMetrics? = null

    init {
        val wm = ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        metrics = DisplayMetrics()
        display.getMetrics(metrics)
    }

    fun getHeight(): Int? {
        return metrics?.heightPixels
    }

    fun getWidth(): Int? {
        return metrics?.widthPixels
    }

    fun getRealHeight(): Int?{
        return metrics?.densityDpi?.let { metrics?.heightPixels?.div(it) }
    }

    fun getRealWidth(): Int {
        return metrics?.densityDpi?.let { metrics?.widthPixels?.div(it) } ?:-1
    }

    fun getDensity(): Int? {
        return metrics?.densityDpi
    }

    fun getScale(picWidth: Int): Int {
        val display = (ctx.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        val width = display.width
        var v = (width / picWidth).toDouble()
        v *= 100.0
        return v.toInt()
    }
}