package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateHumidifierTaskResponse(
    @SerialName("humidifier_task_id")
    val humidifierTaskId:Int,
)
