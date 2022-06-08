package com.restauran.features.menu.prsenter

import android.app.Activity
import com.restauran.R
import com.restauran.features.menu.view.MenuView
import com.restauran.network.API.model.Menu
import com.restauran.network.API.utils.NetworkShared
import com.restauran.network.API.utils.RequestListener
import com.restauran.utils.ConstantApp
import com.restauran.utils.ToolUtils
import com.restauran.utils.dialogs.DialogUtils

class MenuPresenter(var mActivity: Activity, var mView: MenuView) {
    fun getMenuData() {
        if (ToolUtils.isNetworkConnected()) {
            mView.showProgress()
            NetworkShared.getAsp().general.getMenu(object :
                RequestListener<ArrayList<Menu>> {
                override fun onSuccess(data: ArrayList<Menu>) {
                    mView.hideProgress()
                    mView.setMenusDataView(data)
                }

                override fun onFail(message: String?, code: Int) {
                    mView.hideProgress()
                    DialogUtils.showAlertDialog(
                        mActivity,
                        mActivity.getString(R.string.alert),
                        message,
                        mActivity.getString(R.string.ok),
                        "",
                        ConstantApp.ACTION_NOTHING
                    )
                }
            })

        } else {
            mView.hideProgress()
            mView.showErrorMessage(mActivity.getString(R.string.noInternetConnection))
        }
    }
}