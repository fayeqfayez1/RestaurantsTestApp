package com.restauran.features.bass

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.restauran.utils.dialogs.LoadingDialog

open class BaseActivity : AppCompatActivity(), BaseView {
    private lateinit var loadingDialog: LoadingDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("onCreate", "onCreate")
        super.onCreate(savedInstanceState)
        initDialog()

    }

    private fun initDialog() {
        Log.e("initDialog", "initDialog")
        loadingDialog = LoadingDialog(this)
        loadingDialog.setDialogCancelable()
    }

    override fun showProgress() {
        loadingDialog.showDialog()
    }

    override fun hideProgress() {
        loadingDialog.hideDialog()
    }

    override fun showErrorMessage(massage: String?) {

    }

    override fun showErrorDialog(
        massage: String?,
        okMsg: String?,
        cancelMsg: String?,
        action: String?
    ) {
    }
}