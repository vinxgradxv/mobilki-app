package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateWindowRequest(
    val name:String,
    val facilityName:String
)
