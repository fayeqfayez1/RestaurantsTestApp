package com.restauran.network

interface NewRequestListener<T> {
    fun onSuccess(data: T)
    fun onFail(message: String, code: Int)
}