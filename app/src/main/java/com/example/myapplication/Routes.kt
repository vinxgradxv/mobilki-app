package com.example.myapplication

sealed class Routes (val route: String) {
    object Login : Routes("Login")
    object MainActivity : Routes("MainActivity/{token}/{clientId}/{facilityId}")
    object SignUp : Routes("SignUp")
    object ControlCenter : Routes("ControlCenter/{token}/{clientId}/{facilityId}")
    object Facilities : Routes("Facilities/{token}/{clientId}/{facilityId}")
    object AddFacility : Routes("AddFacility/{token}/{clientId}/{facilityId}")
    object Devices : Routes("Devices/{token}/{clientId}/{facilityId}")
    object Windows : Routes("Windows/{token}/{clientId}/{facilityId}")
    object AddWindow : Routes("AddWindow/{token}/{clientId}/{facilityId}")
    object Humidifiers : Routes("Humidifiers/{token}/{clientId}/{facilityId}")
    object AddHumidifier : Routes("AddHumidifier/{token}/{clientId}/{facilityId}")
    object WindowTask : Routes("WindowTask/{token}/{clientId}/{facilityId}/{windowName}")
    object HumidifierTask : Routes("HumidifierTask/{token}/{clientId}/{facilityId}/{humidifierName}")
}