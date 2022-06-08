package com.restauran.features.bass

interface BaseView {
    fun showProgress()

    fun hideProgress()

    fun showErrorMessage(massage: String?)

    fun showErrorDialog(massage: String?, okMsg: String?, cancelMsg: String?, action: String?)
}