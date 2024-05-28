package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class GetFacilityRequest(
    val clientId:Int
)
