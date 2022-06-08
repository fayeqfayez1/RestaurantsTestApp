package com.restauran.network.API.model

import com.google.gson.annotations.SerializedName

data class RestaurantResponse(
    @SerializedName("Total   Restaurants") var فtotalRestaurants: Int? = null,
    @SerializedName("Result") var result: ArrayList<Restaurant> = arrayListOf()
)
