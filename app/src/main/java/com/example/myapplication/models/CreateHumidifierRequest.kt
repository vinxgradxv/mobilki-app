package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateHumidifierRequest(
    val name:String,
    val facilityName:String
)
