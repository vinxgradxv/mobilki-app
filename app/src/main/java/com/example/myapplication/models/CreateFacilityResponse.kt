package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateFacilityResponse(
    @SerialName("facility_id")
    val facilityId:Int,
)
