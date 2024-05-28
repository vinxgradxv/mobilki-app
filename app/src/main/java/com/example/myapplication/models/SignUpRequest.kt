package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val name:String,
    val phone:String,
    val email:String,
    val password:String
)
