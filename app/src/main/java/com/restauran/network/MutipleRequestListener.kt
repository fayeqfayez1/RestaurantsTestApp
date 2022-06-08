package com.restauran.network

interface MutipleRequestListener<T,F> {
    fun onSuccess(data: T?, data1: F?)

    fun onFail(message: String?)
}