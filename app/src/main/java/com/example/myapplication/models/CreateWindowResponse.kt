package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateWindowResponse(
    @SerialName("window_id")
    val windowId:Int,
)
