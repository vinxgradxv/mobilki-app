package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetHumidifierResponse(
    @SerialName("humidifiers")
    val humidifiers:List<HumidifierResponse>,
)
