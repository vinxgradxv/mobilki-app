package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HumidifierResponse(
    @SerialName("name")
    val name:String,
    @SerialName("status")
    val status:String,
    @SerialName("humidity")
    val humidity:Int
)
