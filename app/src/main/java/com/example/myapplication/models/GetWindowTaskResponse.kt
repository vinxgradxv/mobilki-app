package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetWindowTaskResponse(
    @SerialName("tasks")
    val tasks:List<WindowTaskResponse>,
)
