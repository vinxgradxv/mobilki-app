package com.example.myapplication

import android.annotation.SuppressLint
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.example.myapplication.models.GetFacilityRequest
import com.example.myapplication.models.GetFacilityResponse
import com.example.myapplication.models.GetWindowResponse
import com.example.myapplication.models.LoginRequest
import com.example.myapplication.models.LoginResponse
import com.example.myapplication.network.ApiRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun Windows(navController: NavHostController, token: String, clientId: String, facilityId: String) {

    // State variables to store user input
    val resp = remember {
        mutableStateOf(GetWindowResponse(emptyList()))
    }

    val dataStore = LocalContext.current.dataStore
    val getFacility: State<String> = dataStore.data.map { preferences ->
        preferences[FACILITY_KEY] ?: "Default Value"
    }.collectAsState(initial = "Default Value")


    getWindows(resp, token, facilityId)
    // Column to arrange UI elements vertically
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(40.dp)) {

        Image(painter = painterResource(id = R.drawable.icon_foreground),
            contentDescription = "icon",
            modifier = Modifier.size(50.dp))


        // Welcome message
        Text(text = "Окна", fontSize = 32.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))
        WindowCard(resp, navController, token, clientId, facilityId)
        Spacer(modifier = Modifier.height(20.dp))


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Display the existing elements

            // Button to add a new element
            Button(onClick = {
                navController.navigate(Routes.AddWindow.route
                    .replace(oldValue = "{token}", newValue = token)
                    .replace(oldValue = "{clientId}", newValue = clientId)
                    .replace(oldValue = "{facilityId}", newValue = getFacility.value))
            },
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 25.dp, 0.dp, 0.dp)
            ) {
                Text(text = "+",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    color = Color.White
                )
            }
        }
    }
    FooterBasic(navController, token, clientId, getFacility.value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WindowCard(response: MutableState<GetWindowResponse>, navController: NavHostController, token: String, clientId: String, facilityId: String) {
    val scope  = rememberCoroutineScope()
    val dataStore = LocalContext.current.dataStore
    LazyColumn(modifier = Modifier.fillMaxHeight(0.7f).selectableGroup()) {
        items(response.value.windows) {
            facility ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(Routes.WindowTask.route
                        .replace(oldValue = "{token}", newValue = token)
                        .replace(oldValue = "{clientId}", newValue = clientId)
                        .replace(oldValue = "{facilityId}", newValue = facilityId)
                        .replace(oldValue = "{windowName}", newValue = facility.name))
                },
                colors = CardDefaults.cardColors(Color.Blue)
            ) {
                Text("Название: " + facility.name + "\n\nСтатус: " + facility.status,
                    fontSize = 35.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun getWindows(resp: MutableState<GetWindowResponse>, token:String, facilityId: String) {
    val apiRepo = ApiRepository()
    val scope  = rememberCoroutineScope()



    LaunchedEffect(Unit) {
        scope.launch {
            resp.value = apiRepo.sendGetWindow(facilityId, token)
        }
    }
}