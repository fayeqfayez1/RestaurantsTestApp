package com.restauran.network.API.feature

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.restauran.R
import com.restauran.network.API.model.Menu
import com.restauran.network.API.model.Restaurant
import com.restauran.network.API.utils.RequestListener
import com.restauran.network.API.utils.RetrofitModel
import com.restauran.utils.RestaurantsApp
import com.restauran.utils.ToolUtils

class General {
    private val TAG_SUCCESS = "Response_SUCCESS"
    private val TAG_ERROR = "Response_ERROR"
    private val TAG_GENERAL = "General"
    private val retrofitModelMultiPart: RetrofitModel = RetrofitModel()
    private val gson = Gson()

    @SuppressLint("CheckResult")
    fun getRestaurant(mRequestListener: RequestListener<List<Restaurant>>) {
        retrofitModelMultiPart.getRestaurant().subscribe({ appResponse ->
            Log.e(TAG_SUCCESS, gson.toJson(appResponse))
            if (appResponse?.result?.isNotEmpty() == true)
                mRequestListener.onSuccess(appResponse.result)
            else
                mRequestListener.onFail(
                    RestaurantsApp.getInstance()?.getString(R.string.failed_to_get_restaurant_data)
                )

        }, { throwable ->
            Log.e(TAG_ERROR, "${throwable.message}")
            mRequestListener.onFail(
                ToolUtils.getThrowableErrorMsg(throwable),
                ToolUtils.getErrorCode(throwable)
            )
        })
    }

    @SuppressLint("CheckResult")
    fun getMenu(mRequestListener: RequestListener<ArrayList<Menu>>) {
        retrofitModelMultiPart.getMenu().subscribe({ appResponse ->
            Log.e(TAG_SUCCESS, gson.toJson(appResponse))
            if (appResponse?.result?.isNotEmpty() == true)
                mRequestListener.onSuccess(appResponse.result)
            else
                mRequestListener.onFail(
                    RestaurantsApp.getInstance()?.getString(R.string.failed_to_get_restaurant_data)
                )
        }, { throwable ->
            Log.e(TAG_ERROR, "${throwable.message}")
            mRequestListener.onFail(
                ToolUtils.getThrowableErrorMsg(throwable),
                ToolUtils.getErrorCode(throwable)
            )
        })
    }
}