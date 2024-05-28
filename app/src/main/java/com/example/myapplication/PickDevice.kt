package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PickDevice(navController: NavHostController) {

    // Column to arrange UI elements vertically
    Column(modifier = Modifier
        .fillMaxHeight()
        .padding(40.dp)) {

        Image(painter = painterResource(id = R.drawable.icon_foreground),
            contentDescription = "icon",
            modifier = Modifier.size(50.dp))


        // Welcome message
        Text(text = "Выберите устройство", fontSize = 32.sp, color = Color.Black)

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier
            .size(300.dp, 200.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(color = Color.Blue)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Define a mutable list of elements
        val elements = remember { mutableStateListOf<String>() }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Display the existing elements
            elements.forEach { element ->
                Text(text = element)
            }

            // Button to add a new element
            Button(onClick = {
                val newElement = "New Element"
                navController.navigate(Routes.ControlCenter.route)
                elements.add(newElement)
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
}
