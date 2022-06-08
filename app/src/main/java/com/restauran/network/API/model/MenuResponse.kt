package com.restauran.network.API.model

import com.google.gson.annotations.SerializedName

data class MenuResponse(
    @SerializedName("Total   Menu") var totalMenu: Int? = null,
    @SerializedName("Result") var result: ArrayList<Menu> = arrayListOf()
)
