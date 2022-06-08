package com.restauran.features.main.presenter

import android.app.Activity
import com.restauran.R
import com.restauran.features.main.view.MainView
import com.restauran.network.API.model.Restaurant
import com.restauran.network.API.utils.NetworkShared
import com.restauran.network.API.utils.RequestListener
import com.restauran.utils.ConstantApp.ACTION_NOTHING
import com.restauran.utils.ToolUtils
import com.restauran.utils.dialogs.DialogUtils

class MainPresenter(var mActivity: Activity, var mView: MainView) {


    fun getRestaurantData() {
        if (ToolUtils.isNetworkConnected()) {
            mView.showProgress()
            NetworkShared.getAsp().general.getRestaurant(object :
                RequestListener<List<Restaurant>> {
                override fun onSuccess(data: List<Restaurant>) {
                    mView.hideProgress()
                    mView.setRestaurantsDataView(data)
                }

                override fun onFail(message: String?, code: Int) {
                    mView.hideProgress()
                    DialogUtils.showAlertDialog(
                        mActivity,
                        mActivity.getString(R.string.alert),
                        message,
                        mActivity.getString(R.string.ok),
                        "",
                        ACTION_NOTHING
                    )
                }
            })

        } else {
            mView.hideProgress()
            mView.showErrorMessage(mActivity.getString(R.string.noInternetConnection))
        }
    }
}