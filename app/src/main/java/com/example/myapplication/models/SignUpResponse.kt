package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpResponse(
    @SerialName("user_id")
    val userId:Int,
    @SerialName("access_token")
    val accessToken:String
)
