package com.restauran.utils

interface OnRequestListener {
    fun onSuccess()

    fun onFail(msg: String?)
}