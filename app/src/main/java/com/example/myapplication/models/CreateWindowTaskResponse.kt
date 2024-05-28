package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateWindowTaskResponse(
    @SerialName("window_task_id")
    val windowTaskId:Int,
)
