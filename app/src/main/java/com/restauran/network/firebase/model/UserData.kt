package com.restauran.network.firebase.model

data class UserData(
     val email: String,
     val name: String,
     var address: String? = "",
     var age: Long = 0,
     var bio: String = "",
     val coverImage: String? = null,
     val createdAt: Long? = null,
     val gender: String? = null,
     val isActive: Long = 0,
)
