package com.restauran.network

interface RequestListener<T> {
    fun onSuccess(data: T)

    fun onFail(message: String)
}