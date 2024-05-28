package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateHumidifierResponse(
    @SerialName("humidifier_id")
    val windowId:Int,
)
