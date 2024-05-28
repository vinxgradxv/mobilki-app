package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HumidifierTaskResponse(
    @SerialName("humidifier_name")
    val humidifierName:String,
    @SerialName("humidity")
    val humidity:Int,
    @SerialName("start_date")
    val startDate:String,
    @SerialName("end_date")
    val endDate:String,
    )
