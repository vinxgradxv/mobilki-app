package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.MainActivity.route) {

        composable(Routes.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(Routes.MainActivity.route) {
            WelcomePage(navController = navController)
        }
        composable(Routes.SignUp.route) {
            SignUp(navController = navController)
        }
        composable(Routes.ControlCenter.route) {entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            ControlCenter(navController, token, clientId, facilityId)
        }
        composable(Routes.Facilities.route) { entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            Facilities(navController = navController, token = token, clientId = clientId, facilityId = facilityId)
        }
        composable(Routes.AddFacility.route) {entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            AddFacility(navController, token, clientId, facilityId)
        }
        composable(Routes.Devices.route) {entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            Devices(navController, token, clientId, facilityId)
        }
        composable(Routes.Windows.route) {entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            Windows(navController, token, clientId, facilityId)
        }
        composable(Routes.AddWindow.route) {entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            AddWindow(navController, token, clientId, facilityId)
        }
        composable(Routes.Humidifiers.route) {entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            Humidifiers(navController, token, clientId, facilityId)
        }
        composable(Routes.AddHumidifier.route) {entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            AddHumidifier(navController, token, clientId, facilityId)
        }
        composable(Routes.WindowTask.route) {entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            val windowName = entry.arguments?.getString("windowName").toString()
            WindowTask(navController, token, clientId, facilityId, windowName)
        }
        composable(Routes.HumidifierTask.route) {entry ->
            val clientId = entry.arguments?.getString("clientId").toString()
            val token = entry.arguments?.getString("token").toString()
            val facilityId = entry.arguments?.getString("facilityId").toString()
            val humidifierName = entry.arguments?.getString("humidifierName").toString()
            HumidifierTask(navController, token, clientId, facilityId, humidifierName)
        }
    }
}
