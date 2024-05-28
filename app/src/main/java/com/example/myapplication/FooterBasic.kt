package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun FooterBasic(navController: NavHostController, token: String, clientId: String, facilityId: String){
    Box(modifier = Modifier.fillMaxSize()
        //.padding(horizontal = 40.dp, vertical = 10.dp)
        .border(1.dp, Color.Black),
    ){
        Column(
            modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter).background(Color.White),
        ){
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Image(painter = painterResource(id = R.drawable.manage_foreground),
                    contentDescription = "icon",
                    modifier = Modifier.size(50.dp).clickable {
                        navController.navigate(Routes.ControlCenter.route
                            .replace(oldValue = "{token}", newValue = token)
                            .replace(oldValue = "{clientId}", newValue = clientId)
                            .replace(oldValue = "{facilityId}", newValue = facilityId)) })

                Image(painter = painterResource(id = R.drawable.places_foreground),
                    contentDescription = "icon",
                    modifier = Modifier.size(50.dp).clickable {
                        navController.navigate(Routes.Facilities.route
                            .replace(oldValue = "{token}", newValue = token)
                            .replace(oldValue = "{clientId}", newValue = clientId)
                            .replace(oldValue = "{facilityId}", newValue = facilityId))
                    })

                Image(painter = painterResource(id = R.drawable.devices_foreground),
                    contentDescription = "icon",
                    modifier = Modifier.size(50.dp).clickable {
                        navController.navigate(Routes.Devices.route
                            .replace(oldValue = "{token}", newValue = token)
                            .replace(oldValue = "{clientId}", newValue = clientId)
                            .replace(oldValue = "{facilityId}", newValue = facilityId)) })
            }
        }
    }

}
