package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavHostController
import com.example.myapplication.models.LoginRequest
import com.example.myapplication.models.LoginResponse
import com.example.myapplication.network.ApiRepository
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavHostController) {

    // State variables to store user input
    val userEmail = remember {
        mutableStateOf("")
    }
    val userPassword = remember {
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

        Image(painter = painterResource(id = R.drawable.icon_foreground),
            contentDescription = "icon")


        // Welcome message
        Text(text = "Вход", fontSize = 40.sp, color = Color.Black
        )

        Text(text = "Рады видеть вас снова!", fontSize = 18.sp, color = Color.DarkGray
        )

        // Username input field
        OutlinedTextField(value = userEmail.value, onValueChange = {
            userEmail.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.MailOutline, contentDescription = "person")
            },
            label = {
                Text(text = "Почта")
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp, 0.dp, 0.dp)
        )

        // Password input field
        OutlinedTextField(value = userPassword.value, onValueChange = {
            userPassword.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.Info, contentDescription = "password")
            },
            label = {
                Text(text = "Пароль")
            },
            shape = RoundedCornerShape(15.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 20.dp, 0.dp, 0.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        ErrorText(isVisible.value)
        Spacer(modifier = Modifier.height(200.dp))

        val apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()

        val dataStore = LocalContext.current.dataStore

        // Login button
        OutlinedButton(onClick = {
            var response:LoginResponse
            scope.launch {
                try {
                    response = apiRepo.sendLogin(LoginRequest(userEmail.value, userPassword.value))

                    dataStore.edit { preferences ->
                        preferences[TOKEN_KEY] = response.accessToken
                    }

                    dataStore.edit { preferences ->
                        preferences[CLIENT_ID_KEY] = response.userId.toString()
                    }

                    navController.navigate(Routes.Facilities.route
                        .replace(oldValue = "{token}", newValue = response.accessToken)
                        .replace(oldValue = "{clientId}", newValue = response.userId.toString())
                        .replace(oldValue = "{facilityId}", newValue = "1"))
                }
                catch (error: Throwable) {
                    isVisible.value=true
                    println("Error!")
                }
            }},
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 25.dp, 0.dp, 0.dp)) {
            Text(text = "Далее >",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        ClickableText(
            text = AnnotatedString("Новенький? Регистрируйся сейчас"),
            onClick = {
                navController.navigate(Routes.SignUp.route)
            },
            style = TextStyle(
                color = Color.Blue,
                fontFamily = FontFamily.Cursive
            )
        )

    }
}


@Composable
fun ErrorText(isVisible: Boolean){
    if(isVisible){
        Text(text = "Ошибка!!!")
    }
}
