package com.example.myapplication.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetFacilityResponse(
    @SerialName("facilities")
    val facilities:List<FacilityResponse>,
)
