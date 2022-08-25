package com.example.myapplication.model

data class GetUserReponse(
    val created_at: String,
    val id: Int,
    val phoneNumber: String,
    val status: Boolean,
    val updated_at: String
)