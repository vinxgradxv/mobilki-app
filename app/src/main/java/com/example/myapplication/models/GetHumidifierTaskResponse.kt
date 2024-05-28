package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetHumidifierTaskResponse(
    @SerialName("tasks")
    val tasks:List<HumidifierTaskResponse>,
)
