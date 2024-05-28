package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
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
import androidx.compose.ui.layout.ContentScale
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
import com.example.myapplication.models.SignUpRequest
import com.example.myapplication.models.SignUpResponse
import com.example.myapplication.network.ApiRepository
import kotlinx.coroutines.launch

@Composable
fun SignUp(navController: NavHostController) {

    // State variables to store user input
    val userName = remember {
        mutableStateOf("")
    }
    val userEmail = remember {
        mutableStateOf("")
    }
    val userPhone = remember {
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
        Text(text = "Регистрация", fontSize = 40.sp, color = Color.Black
        )

        Text(text = "Свежий воздух ближе чем кажется", fontSize = 18.sp, color = Color.DarkGray
        )

        OutlinedTextField(value = userName.value, onValueChange = {
            userName.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.Person, contentDescription = "person")
            },
            label = {
                Text(text = "Полное имя")
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 0.dp, 0.dp)
        )

        OutlinedTextField(value = userPhone.value, onValueChange = {
            userPhone.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.Phone, contentDescription = "person")
            },
            label = {
                Text(text = "Номер телефона")
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 0.dp, 0.dp)
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
            modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 0.dp, 0.dp)
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
            modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 0.dp, 0.dp),
            visualTransformation = PasswordVisualTransformation()
        )

        ErrorText(isVisible.value)

        Spacer(modifier = Modifier.height(40.dp))

        val apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()

        val dataStore = LocalContext.current.dataStore
        // Login button
        OutlinedButton(onClick = {
            var response: SignUpResponse
            scope.launch {
                try {
                    response = apiRepo.sendSignUp(SignUpRequest(userName.value, userPhone.value, userEmail.value, userPassword.value))

                    dataStore.edit { preferences ->
                        preferences[TOKEN_KEY] = response.accessToken
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

        Spacer(modifier = Modifier.height(10.dp))

        ClickableText(
            text = AnnotatedString("У вас уже есть аккаунт? Войдите"),
            onClick = {
                navController.navigate(Routes.Login.route)
            },
            style = TextStyle(
                color = Color.Blue,
                fontFamily = FontFamily.Cursive
            )
        )

    }
}

