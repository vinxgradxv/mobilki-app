package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.myapplication.models.CreateHumidifierTaskRequest
import com.example.myapplication.models.CreateHumidifierTaskResponse
import com.example.myapplication.models.CreateWindowTaskRequest
import com.example.myapplication.models.CreateWindowTaskResponse
import com.example.myapplication.network.ApiRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumidifierTask(navController: NavHostController, token: String, clientId: String, facilityId: String, humidifierName:String) {

    // State variables to store user input
    val humidity = remember {
        mutableStateOf("")
    }

    val isVisible = remember {
        mutableStateOf(false)
    }

    // Column to arrange UI elements vertically
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        val stateStart = rememberTimePickerState()


        // Welcome message
        Text(text = "Настроить включение увлажнителя", fontSize = 40.sp, color = Color.Black
        )

        OutlinedTextField(value = humidity.value, onValueChange = {
            humidity.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.Build, contentDescription = "person")
            },
            label = {
                Text(text = "Процент влажности")
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 0.dp, 0.dp)
        )


        TimeInput(
            state = stateStart,
            modifier = Modifier.padding(vertical = 16.dp)
        )


        val stateEnd = rememberTimePickerState()

        TimeInput(
            state = stateEnd,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        ErrorText(isVisible.value)

        val apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()


        // Login button
        OutlinedButton(onClick = {
            var response: CreateHumidifierTaskResponse
            scope.launch {
                try {
                    var startHour = stateStart.hour.toString()
                    if (startHour.length == 1) {
                        startHour = "0$startHour"
                    }

                    var startMinute = stateStart.minute.toString()
                    if (startMinute.length == 1) {
                        startMinute = "0$startMinute"
                    }

                    var endHour = stateEnd.hour.toString()
                    if (endHour.length == 1) {
                        endHour = "0$endHour"
                    }

                    var endMinute = stateEnd.minute.toString()
                    if (endMinute.length == 1) {
                        endMinute = "0$endMinute"
                    }


                    val startTime = "2024-05-16T" + startHour + ":" + startMinute + ":00"
                    val endTime = "2024-05-16T" + endHour + ":" + endMinute + ":00"
                    response = apiRepo.sendCreateHumidifierTask(CreateHumidifierTaskRequest(humidity.value.toInt(), startTime, endTime, humidifierName), token)

                    navController.navigate(Routes.Humidifiers.route
                        .replace(oldValue = "{token}", newValue = token)
                        .replace(oldValue = "{clientId}", newValue = clientId)
                        .replace(oldValue = "{facilityId}", newValue = facilityId))
                }
                catch (error: Throwable) {
                    isVisible.value=true
                    println("Error!")
                }
            }
                                 },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier.fillMaxWidth().padding(0.dp, 25.dp, 0.dp, 0.dp)) {
            Text(text = "Далее >",
                modifier = Modifier.fillMaxWidth().padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White
            )
        }
    }
}

