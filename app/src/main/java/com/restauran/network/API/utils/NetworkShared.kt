package com.restauran.network.API.utils

import com.restauran.network.API.feature.General

class NetworkShared {
    companion object {
        private var asp: ASP = ASP()
        fun getAsp(): ASP {
            return asp
        }
    }

    class ASP {
        private val mGeneral: General = General()
        val general: General
            get() = mGeneral
    }
}