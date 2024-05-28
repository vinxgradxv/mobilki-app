package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FacilityResponse(
    @SerialName("name")
    val name:String,
    @SerialName("temperature")
    val temperature:Int,
    @SerialName("humidity")
    val humidity:Int
)
