package com.example.myapplication.network

import com.example.myapplication.models.CreateFacilityRequest
import com.example.myapplication.models.CreateFacilityResponse
import com.example.myapplication.models.CreateHumidifierRequest
import com.example.myapplication.models.CreateHumidifierResponse
import com.example.myapplication.models.CreateHumidifierTaskRequest
import com.example.myapplication.models.CreateHumidifierTaskResponse
import com.example.myapplication.models.CreateWindowRequest
import com.example.myapplication.models.CreateWindowResponse
import com.example.myapplication.models.CreateWindowTaskRequest
import com.example.myapplication.models.CreateWindowTaskResponse
import com.example.myapplication.models.GetFacilityRequest
import com.example.myapplication.models.GetFacilityResponse
import com.example.myapplication.models.GetHumidifierResponse
import com.example.myapplication.models.GetHumidifierTaskResponse
import com.example.myapplication.models.GetWindowResponse
import com.example.myapplication.models.GetWindowTaskResponse
import com.example.myapplication.models.LoginRequest
import com.example.myapplication.models.LoginResponse
import com.example.myapplication.models.SignUpRequest
import com.example.myapplication.models.SignUpResponse
import com.example.myapplication.network.ApiClient.client
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class ApiRepository {

    suspend fun sendLogin(request: LoginRequest): LoginResponse = client.post(ApiRoutes.LOGIN_URL){
        setBody(request)
    }.body()

    suspend fun sendSignUp(request: SignUpRequest): SignUpResponse = client.post(ApiRoutes.SIGN_UP_URL){
        setBody(request)
    }.body()

    suspend fun sendGetFacility(clientId:Int, token: String): GetFacilityResponse = client.get(ApiRoutes.CREATE_FACILITY_URL + "/" + clientId){
        header("Authorization", "Bearer $token")
    }.body()


    suspend fun sendCreateFacility(request: CreateFacilityRequest, token: String): CreateFacilityResponse = client.post(ApiRoutes.CREATE_FACILITY_URL){
        setBody(request)
        header("Authorization", "Bearer $token")
    }.body()

    suspend fun sendGetWindow(facilityId:String, token: String): GetWindowResponse = client.get(ApiRoutes.WINDOW_URL + "/" + facilityId){
        header("Authorization", "Bearer $token")
    }.body()

    suspend fun sendCreateWindow(request: CreateWindowRequest, token: String): CreateWindowResponse = client.post(ApiRoutes.WINDOW_URL){
        setBody(request)
        header("Authorization", "Bearer $token")
    }.body()

    suspend fun sendGetHumidifier(facilityId:String, token: String): GetHumidifierResponse = client.get(ApiRoutes.HUMIDIFIER_URL + "/" + facilityId){
        header("Authorization", "Bearer $token")
    }.body()

    suspend fun sendCreateHumidifier(request: CreateHumidifierRequest, token: String): CreateHumidifierResponse = client.post(ApiRoutes.HUMIDIFIER_URL){
        setBody(request)
        header("Authorization", "Bearer $token")
    }.body()

    suspend fun sendCreateWindowTask(request: CreateWindowTaskRequest, token: String): CreateWindowTaskResponse = client.post(ApiRoutes.WINDOW_URL + "/task"){
        setBody(request)
        header("Authorization", "Bearer $token")
    }.body()

    suspend fun sendGetWindowTask(clientId:Int, token: String): GetWindowTaskResponse = client.get(ApiRoutes.WINDOW_URL + "/task/" + clientId){
        header("Authorization", "Bearer $token")
    }.body()

    suspend fun sendCreateHumidifierTask(request: CreateHumidifierTaskRequest, token: String): CreateHumidifierTaskResponse = client.post(ApiRoutes.HUMIDIFIER_URL + "/task"){
        setBody(request)
        header("Authorization", "Bearer $token")
    }.body()

    suspend fun sendGetHumidifierTask(clientId:Int, token: String): GetHumidifierTaskResponse = client.get(ApiRoutes.HUMIDIFIER_URL + "/task/" + clientId){
        header("Authorization", "Bearer $token")
    }.body()
}