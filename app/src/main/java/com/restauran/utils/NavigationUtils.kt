package com.restauran.utils

import android.app.Activity
import android.content.Intent
import com.restauran.features.ViewInvoice.view.ViewInvoiceActivity
import com.restauran.features.main.view.MainActivity
import com.restauran.features.logIn.view.LogInActivity
import com.restauran.features.menu.view.MenuActivity
import com.restauran.utils.ConstantApp.FROM_OTHERS

object NavigationUtils {

    fun goToMain(mActivity: Activity, fromWhere: Int = FROM_OTHERS) {
        val intent: Intent = MainActivity.newInstance(mActivity, fromWhere)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        mActivity.startActivity(intent)
        mActivity.finish()
    }

    fun goToLogIn(mActivity: Activity, fromWhere: Int = FROM_OTHERS) {
        val intent: Intent = LogInActivity.newInstance(mActivity, fromWhere)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        mActivity.startActivity(intent)
        mActivity.finish()
    }

    fun goToMenu(mActivity: Activity, fromWhere: Int = FROM_OTHERS) {
        val intent: Intent = MenuActivity.newInstance(mActivity, fromWhere)
        mActivity.startActivity(intent)
    }

    fun goToViewInvoice(mActivity: Activity, fromWhere: Int = FROM_OTHERS) {
        val intent: Intent = ViewInvoiceActivity.newInstance(mActivity, fromWhere)
        mActivity.startActivity(intent)
    }
}