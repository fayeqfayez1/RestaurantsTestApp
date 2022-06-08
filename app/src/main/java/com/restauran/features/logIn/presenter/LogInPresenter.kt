package com.restauran.features.logIn.presenter

import android.app.Activity
import android.widget.EditText
import com.restauran.R
import com.restauran.features.logIn.view.LogInView
import com.restauran.network.StringRequestListener
import com.restauran.network.firebase.feature.User
import com.restauran.utils.AppShareMethods
import com.restauran.utils.AppSharedData
import com.restauran.utils.ConstantApp.FROM_SIGN_IN
import com.restauran.utils.NavigationUtils
import com.restauran.utils.ToolUtils

class LogInPresenter(var mActivity: Activity, var mView: LogInView) {

    fun validateSignIn(edEmail: EditText, edPassword: EditText) {
        edEmail.error = null
        edPassword.error = null
        if (AppShareMethods.isEmptyEditText(edEmail)) {
            AppShareMethods.errorInput(edEmail, mActivity.getString(R.string.isRequiredF))
            return
        }
        if (!AppShareMethods.isValidEmailAddress(edEmail)) {
            AppShareMethods.errorInput(edEmail, mActivity.getString(R.string.invalidEmail))
            return
        }
        if (AppShareMethods.isEmptyEditText(edPassword)) {
            AppShareMethods.errorInput(edPassword, mActivity.getString(R.string.isRequiredF))
            return
        }
        if (!AppShareMethods.isValidPassword(edPassword)) {
            AppShareMethods.errorInput(edPassword, mActivity.getString(R.string.password_invalid))
            return
        }
        ToolUtils.hideKeyboard(mActivity)
        normalLogin(AppShareMethods.getText(edEmail), AppShareMethods.getText(edPassword))
    }

    private fun normalLogin(email: String, password: String) {
        ToolUtils.hideKeyboard(mActivity)
        if (ToolUtils.isNetworkConnected()) {
            mView.showProgress()
            User.getInstance()
                ?.loginWithEmailAndPassword(email = email, password = password, object :
                    StringRequestListener<String?> {
                    override fun onSuccess(message: String, data: String?) {
                        mView.hideProgress()
                        AppSharedData.setUserLogin(true)
                        NavigationUtils.goToMain(mActivity, FROM_SIGN_IN)
                    }

                    override fun onFail(message: String) {
                        mView.hideProgress()
                        mView.showErrorMessage(message)
                    }
                })
        } else {
            mView.hideProgress()
            mView.showErrorMessage(mActivity.getString(R.string.noInternetConnection))
        }
    }
}