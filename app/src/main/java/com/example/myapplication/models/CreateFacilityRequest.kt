package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class CreateFacilityRequest(
    val name:String,
    val address:String,
    val size:Float,
    val clientId:Int
)
