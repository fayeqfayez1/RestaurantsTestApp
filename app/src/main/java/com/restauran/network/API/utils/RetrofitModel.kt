package com.restauran.network.API.utils

import android.util.Log
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.restauran.network.API.model.MenuResponse
import com.restauran.network.API.model.RestaurantResponse
import com.restauran.network.API.utils.ConstantRetrofit.BASE_URL
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitModel {
    private lateinit var api: RetrofitApis
    private var subscribeOn: Scheduler? = null
    private var observeOn: Scheduler? = null

    init {
        var interceptorToHeaderData = Interceptor { chain: Interceptor.Chain ->
            val builder: Request.Builder = chain.request().newBuilder()
            chain.proceed(builder.build())
        }
        initInterceptor(interceptorToHeaderData)
    }

    private fun initInterceptor(interceptorToHeaderData: Interceptor) {
        Log.e("initInterceptor", "Interceptor")
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptorToHeaderData)
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(3, TimeUnit.MINUTES)
            .readTimeout(3, TimeUnit.MINUTES)
            .writeTimeout(3, TimeUnit.MINUTES)
            .build()
        this.api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(httpClient)
            .build()
            .create(RetrofitApis::class.java)
        subscribeOn = Schedulers.io()
        observeOn = AndroidSchedulers.mainThread()
    }

    fun getRestaurant(): Observable<RestaurantResponse?> =
        api.getRestaurant().subscribeOn(subscribeOn).observeOn(observeOn)

    fun getMenu(): Observable<MenuResponse?> =
        api.getMenu().subscribeOn(subscribeOn).observeOn(observeOn)
}