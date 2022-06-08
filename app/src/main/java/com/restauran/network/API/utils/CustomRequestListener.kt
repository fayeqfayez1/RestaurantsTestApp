package com.restauran.network.API.utils

interface CustomRequestListener <T> {
    fun onSuccess(totalPages: Int, data: T)

    fun onFail(message: String?, code: Int)
}