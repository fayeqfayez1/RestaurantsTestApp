package com.restauran.network.API.model

import com.google.gson.annotations.SerializedName

data class Restaurant(
    @SerializedName("reviews") var reviews: Int? = null,
    @SerializedName("parkinglot") var parkinglot: Boolean? = null,
    @SerializedName("location") var location: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("averagecost") var averagecost: Int? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("imageId") var imageId: String? = null,
    @SerializedName("restauranttype") var restauranttype: String? = null,
    @SerializedName("businessname") var businessname: String? = null,
    @SerializedName("address") var address: String? = null,
    @SerializedName("menu") var menu: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("id") var id: String? = null
)
