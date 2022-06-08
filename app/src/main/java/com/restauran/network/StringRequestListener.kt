package com.restauran.network

interface StringRequestListener<T> {
    fun onSuccess(message: String, data: T)

    fun onFail(message: String)
}