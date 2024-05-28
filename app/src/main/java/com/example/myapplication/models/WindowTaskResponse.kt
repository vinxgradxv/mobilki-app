package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WindowTaskResponse(
    @SerialName("window_name")
    val windowName:String,
    @SerialName("opening_percentage")
    val openingPercentage:Int,
    @SerialName("start_date")
    val startDate:String,
    @SerialName("end_date")
    val endDate:String,
    )
