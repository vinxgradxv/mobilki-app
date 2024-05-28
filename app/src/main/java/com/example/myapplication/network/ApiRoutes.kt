package com.example.myapplication.network

object ApiRoutes {
    private const val BASE_URL:String = "http://192.168.0.4:8088"
    var LOGIN_URL = "$BASE_URL/auth/authenticate"
    var SIGN_UP_URL = "$BASE_URL/auth/clientRegister"
    var CREATE_FACILITY_URL = "$BASE_URL/facility"
    var WINDOW_URL = "$BASE_URL/window"
    var HUMIDIFIER_URL = "$BASE_URL/humidifier"
}