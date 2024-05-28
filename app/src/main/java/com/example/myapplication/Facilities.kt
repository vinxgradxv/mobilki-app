package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavHostController
import com.example.myapplication.models.GetFacilityResponse
import com.example.myapplication.network.ApiRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun Facilities(navController: NavHostController, token: String, clientId: String, facilityId: String) {

    // State variables to store user input
    val resp = remember {
        mutableStateOf(GetFacilityResponse(emptyList()))
    }

    val dataStore = LocalContext.current.dataStore
    val getFacility: State<String> = dataStore.data.map { preferences ->
        preferences[FACILITY_KEY] ?: "Default Value"
    }.collectAsState(initial = "Default Value")


    getFacilities(resp, token, clientId)
    // Column to arrange UI elements vertically
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(40.dp)) {

        Image(painter = painterResource(id = R.drawable.icon_foreground),
            contentDescription = "icon",
            modifier = Modifier.size(50.dp))


        // Welcome message
        Text(text = "Помещения", fontSize = 32.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))
        FacilityCard(resp)
        Spacer(modifier = Modifier.height(20.dp))


        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Display the existing elements

            // Button to add a new element
            Button(onClick = {
                navController.navigate(Routes.AddFacility.route
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

@Composable
fun FacilityCard(response: MutableState<GetFacilityResponse>) {
    val scope  = rememberCoroutineScope()
    val dataStore = LocalContext.current.dataStore
    val getFacility: State<String> = dataStore.data.map { preferences ->
        preferences[FACILITY_KEY] ?: "Default Value"
    }.collectAsState(initial = "Default Value")
    LazyColumn(modifier = Modifier.fillMaxHeight(0.7f).selectableGroup()) {
        items(response.value.facilities) {
            facility ->
            Card(
                modifier = Modifier.fillMaxWidth().selectable(
                    selected = getFacility.value == facility.name,
                    onClick = {
                        scope.launch {
                            dataStore.edit { preferences ->
                                preferences[FACILITY_KEY] = facility.name
                            }
                        }
                    }
                ),
                colors = CardDefaults.cardColors(
                    if (getFacility.value == facility.name) Color.Blue
                    else Color.Black
                )
            ) {
                Text("Название: " + facility.name + "\n\nТемпература: " + facility.temperature
                    + "\n\nВлажность: " + facility.humidity,
                    fontSize = 35.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun getFacilities(resp: MutableState<GetFacilityResponse>, token:String, clientId:String) {
    val apiRepo = ApiRepository()
    val scope  = rememberCoroutineScope()



    LaunchedEffect(Unit) {
        scope.launch {
            resp.value = apiRepo.sendGetFacility(clientId.toInt(), token)
        }
    }
}