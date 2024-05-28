package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetWindowResponse(
    @SerialName("windows")
    val windows:List<WindowResponse>,
)
