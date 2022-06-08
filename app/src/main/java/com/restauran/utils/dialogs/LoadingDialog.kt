package com.restauran.utils.dialogs

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContextWrapper
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.restauran.R
import java.lang.Exception
import java.util.*


class LoadingDialog(val activity: Activity) {
    private lateinit var alertDialog: AlertDialog
    private val TAG = LoadingDialog::class.simpleName

    init {
        newInstance()
    }

    @SuppressLint("InflateParams")
    private fun newInstance() {
        Log.e("newInctens", "newInctens")

        val dialog: AlertDialog.Builder =
            AlertDialog.Builder(activity, R.style.CustomDialogTheme)
                .setCancelable(false)
                .setView(LayoutInflater.from(activity)
                    .inflate(R.layout.loading_dialog_layout, null))
        alertDialog = dialog.create()
    }

    fun showDialog() {
        Log.e("showDialog", "showDialog")
        if (activity.isDestroyed)
            return
        if (!alertDialog.isShowing) {
            val context = (alertDialog.context as ContextWrapper).baseContext
            if (context is Activity) {
                if (!context.isFinishing) {
                    alertDialog.show()
                    setDialogDimension()
                }
            } else {
                try {
                    alertDialog.show()
                    setDialogDimension()
                } catch (ex: Exception) {
                    Log.e(TAG, "${ex.message}")
                    ex.printStackTrace()
                }
            }
        }
    }

    @SuppressLint("NewApi")
    private fun setDialogDimension() {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(Objects.requireNonNull(alertDialog.window)?.attributes)
        val width = (activity.windowManager.defaultDisplay.width * 0.4).toInt()
        val height = (activity.windowManager.defaultDisplay.height * 0.35).toInt()
        layoutParams.width = width
        layoutParams.height = height
        alertDialog.window?.attributes = layoutParams
    }

    fun hideDialog() {
        Log.e("hideDialog", "hideDialog")
//        if (activity.isDestroyed)
//            return
//        if (alertDialog.isShowing) {
//            val context =
//
//                (alertDialog.context as ContextWrapper).baseContext
//            if (context is Activity) {
//                if (!(context.isFinishing))
//                    alertDialog.dismiss()
//                else
                    alertDialog.dismiss()
            }
     //   }
  //  }

    fun setDialogCancelable() {
        alertDialog.setCancelable(true)
        alertDialog.setCanceledOnTouchOutside(false)
    }
}