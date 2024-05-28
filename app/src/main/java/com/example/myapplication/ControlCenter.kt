package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavHostController
import com.example.myapplication.models.GetFacilityResponse
import com.example.myapplication.models.GetHumidifierTaskResponse
import com.example.myapplication.models.GetWindowTaskResponse
import com.example.myapplication.network.ApiRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun ControlCenter(navController: NavHostController, token: String, clientId: String, facilityId: String) {


    val temp = remember {
        mutableStateOf("-")
    }

    val humidity = remember {
        mutableStateOf("-")
    }

    val respWindowTask = remember {
        mutableStateOf(GetWindowTaskResponse(emptyList()))
    }

    val respHumidifierTask = remember {
        mutableStateOf(GetHumidifierTaskResponse(emptyList()))
    }


    getWindowTasks(respWindowTask, token, clientId)
    getHumidifierTasks(respHumidifierTask, token, clientId)
    getFacilityParams(token, clientId, facilityId, temp, humidity)

    // State variables to store user input
    val dataStore = LocalContext.current.dataStore
    val getToken: State<String> = dataStore.data.map { preferences ->
        preferences[TOKEN_KEY] ?: "Default Value"
    }.collectAsState(initial = "Default Value")



    // Column to arrange UI elements vertically
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(40.dp)) {

        Image(painter = painterResource(id = R.drawable.icon_foreground),
            contentDescription = "icon",
            modifier = Modifier.size(50.dp))


        // Welcome message
        Text(text = "Центр управления.\nПомещение "+facilityId, fontSize = 32.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier
            .size(300.dp, 200.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(color = Color.Blue)
        ) {
            Text(text = "Температура: "+temp.value + "\n\nВлажность: " + humidity.value, fontSize = 32.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Row() {
            WindowTaskCard(respWindowTask)
            HumidifierTaskCard(respHumidifierTask)
        }
        // Define a mutable list of elements
        val elements = remember { mutableStateListOf<String>() }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Display the existing elements

            // Button to add a new element
            Button(onClick = {
                navController.navigate(Routes.Devices.route
                    .replace(oldValue = "{token}", newValue = token)
                    .replace(oldValue = "{clientId}", newValue = clientId)
                    .replace(oldValue = "{facilityId}", newValue = facilityId))
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                modifier = Modifier.fillMaxWidth().padding(0.dp, 25.dp, 0.dp, 0.dp)
            ) {
                Text(text = "+",
                    modifier = Modifier.fillMaxWidth().padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    color = Color.White
                )
            }
        }
    }
    FooterBasic(navController, token, clientId, facilityId)
}

@Composable
fun getFacilityParams(token:String, clientId:String, facilityId: String, temp: MutableState<String>, humidity: MutableState<String>) {
    val apiRepo = ApiRepository()
    val scope  = rememberCoroutineScope()



    LaunchedEffect(Unit) {
        scope.launch {
            val resp = apiRepo.sendGetFacility(clientId.toInt(), token)
            for (fac in resp.facilities) {
                if (fac.name == facilityId) {
                    temp.value = fac.temperature.toString()
                    humidity.value = fac.humidity.toString()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WindowTaskCard(response: MutableState<GetWindowTaskResponse>) {
    val scope  = rememberCoroutineScope()
    LazyColumn(modifier = Modifier.fillMaxHeight(0.7f).selectableGroup()) {
        items(response.value.tasks) {
                facility ->
            Card(
                modifier = Modifier.fillMaxWidth(0.4f),
                colors = CardDefaults.cardColors(Color.Blue)
            ) {
                Text("Окно: " + facility.windowName + "\n\nПроцент открытия: " + facility.openingPercentage
                        + "\n\nНачало: " + facility.startDate + "\n\nКонец: " + facility.endDate,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun getWindowTasks(resp: MutableState<GetWindowTaskResponse>, token:String, clientId:String) {
    val apiRepo = ApiRepository()
    val scope  = rememberCoroutineScope()



    LaunchedEffect(Unit) {
        scope.launch {
            resp.value = apiRepo.sendGetWindowTask(clientId.toInt(), token)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumidifierTaskCard(response: MutableState<GetHumidifierTaskResponse>) {
    val scope  = rememberCoroutineScope()
    LazyColumn(modifier = Modifier.fillMaxHeight(0.7f).selectableGroup()) {
        items(response.value.tasks) {
                facility ->
            Card(
                modifier = Modifier.fillMaxWidth(0.7f).padding(10.dp),
                colors = CardDefaults.cardColors(Color.Blue)
            ) {
                Text("Увлажнитель: " + facility.humidifierName + "\n\nПроцент влажности: " + facility.humidity
                        + "\n\nНачало: " + facility.startDate + "\n\nКонец: " + facility.endDate,
                    fontSize = 15.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun getHumidifierTasks(resp: MutableState<GetHumidifierTaskResponse>, token:String, clientId:String) {
    val apiRepo = ApiRepository()
    val scope  = rememberCoroutineScope()



    LaunchedEffect(Unit) {
        scope.launch {
            resp.value = apiRepo.sendGetHumidifierTask(clientId.toInt(), token)
        }
    }
}