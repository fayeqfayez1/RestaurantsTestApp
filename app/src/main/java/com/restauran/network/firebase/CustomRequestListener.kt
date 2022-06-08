package com.restauran.network.firebase

interface CustomRequestListener<T> {
    fun onSuccess(totalPages: Int, data: T)

    fun onFail(message: String?)
}