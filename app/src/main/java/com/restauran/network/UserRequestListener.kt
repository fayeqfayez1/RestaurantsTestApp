package com.restauran.network

interface UserRequestListener<T> {
    fun onSuccess(action: Int, data: T)
    fun onFail(message: String?)
}