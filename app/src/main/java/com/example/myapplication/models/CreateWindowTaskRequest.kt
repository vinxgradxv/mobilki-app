package com.example.myapplication.models

import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class CreateWindowTaskRequest(
    val openingPerc:Int,
    val startTime:String,
    val endTime:String,
    val windowName:String
)
