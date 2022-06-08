package com.restauran.network.API.model

import com.google.gson.annotations.SerializedName

data class Menu(
    @SerializedName("images") var images: ArrayList<String> = arrayListOf(),
    @SerializedName("_id") var Id: String? = null,
    @SerializedName("menuname") var menuname: String? = null,
    @SerializedName("description") var description: String? = null,
)
