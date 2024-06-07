package com.dicoding.ourstory.data.model

data class UserModel(
    val email: String,
    val token: String,
    val userId: String,
    val isLogin: Boolean = false
)