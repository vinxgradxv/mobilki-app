package com.example.myapplication.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CreateHumidifierTaskRequest(
    val humidity:Int,
    val startTime:String,
    val endTime:String,
    val humidifierName:String
)
