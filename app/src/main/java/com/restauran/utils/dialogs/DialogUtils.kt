package com.restauran.utils.dialogs

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.restauran.R
import com.restauran.utils.ConstantApp.ACTION_CLOSE

object DialogUtils {
    fun showAlertDialog(
        mActivity: Activity,
        title: String?,
        message: String?,
        okMsg: String?,
        cancelMsg: String?,
        action: String?
    ) {
        val dialog: CustomFloatDialog? =
            CustomFloatDialog.newInstance(title, message, okMsg, cancelMsg)
        dialog?.setListener(object : CustomFloatDialog.OnClickListener {
            override fun onOkClick() {
                dialog.dismiss()
                if (action == ACTION_CLOSE) {
                    mActivity.setResult(Activity.RESULT_OK)
                    mActivity.finish()
                }
            }

            override fun onCancelClick() {
                dialog.dismiss()
            }
        })
        dialog?.let { dialog ->
            (mActivity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .add(dialog, "DialogMessage").commitAllowingStateLoss()
        }
    }

     fun showAlertDialogWithListener(
        mActivity: Activity,
        title: String,
        message: String,
        okMsg: String,
        cancelMsg: String,
        listener: OnClickListener?

    ) {
        val dialog: CustomDialog =
            CustomDialog.newInstance(title, message, okMsg, cancelMsg)
        dialog.setListener(object : CustomDialog.OnClickListener {
            override fun onCancelClick() {
                listener?.onCancelClick()
                dialog.dismiss()
            }

            override fun onOkClick() {
                listener?.onOkClick()
                dialog.dismiss()
            }
        })
         (mActivity as AppCompatActivity).supportFragmentManager.beginTransaction()
             .add(dialog, "DialogMessage").commitAllowingStateLoss()
    }

    fun showMustLoginDialog(mActivity: Activity) {
        showAlertDialogWithListener(
            mActivity,
            mActivity.getString(R.string.alert),
            mActivity.getString(R.string.must_login),
            mActivity.getString(R.string.login),
            mActivity.getString(R.string.cancel),
            object : OnClickListener {
                override fun onCancelClick() {

                }

                override fun onOkClick() {
                }
            })
    }

    interface OnClickListener {
        fun onOkClick()
        fun onCancelClick()
    }

}