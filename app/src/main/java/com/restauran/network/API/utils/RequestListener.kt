package com.restauran.network.API.utils


interface RequestListener<T> {

    fun onSuccess(data: T)

    fun onFail(message: String?, code: Int=0)
}