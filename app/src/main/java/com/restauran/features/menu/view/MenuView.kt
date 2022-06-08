package com.restauran.features.menu.view

import com.restauran.features.bass.BaseView
import com.restauran.network.API.model.Menu

interface MenuView : BaseView {

   fun setMenusDataView  (data:ArrayList<Menu>)
}