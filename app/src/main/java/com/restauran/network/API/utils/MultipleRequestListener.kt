package com.restauran.network.API.utils

interface MultipleRequestListener<T, F> {
    fun onSuccess(data: T, data1: F)

    fun onFail(message: String, code: Int?)
}