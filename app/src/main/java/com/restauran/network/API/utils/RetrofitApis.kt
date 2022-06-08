package com.restauran.network.API.utils

import com.restauran.network.API.model.MenuResponse
import com.restauran.network.API.model.RestaurantResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface RetrofitApis {
    @GET("restaurant")
    fun getRestaurant(): Observable<RestaurantResponse?>

    @GET("menu")
    fun getMenu(): Observable<MenuResponse?>
}