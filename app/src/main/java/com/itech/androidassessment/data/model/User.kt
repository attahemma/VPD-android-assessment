package com.itech.androidassessment.data.model


data class User (
    val id: Long?,
    val name: String,
    val username: String,
    val email: String,
    val phone: String,
    val website: String,
    val thumbnail: String?
)