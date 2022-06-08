package com.restauran.features.main.view

import com.restauran.features.bass.BaseView
import com.restauran.network.API.model.Restaurant

interface MainView : BaseView {

    fun setRestaurantsDataView(data: List<Restaurant>)

}