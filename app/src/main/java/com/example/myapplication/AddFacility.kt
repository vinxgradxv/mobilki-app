package com.example.myapplication

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import com.example.myapplication.models.CreateFacilityRequest
import com.example.myapplication.models.CreateFacilityResponse
import com.example.myapplication.models.LoginRequest
import com.example.myapplication.models.LoginResponse
import com.example.myapplication.network.ApiRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun AddFacility(navController: NavHostController, token: String, clientId: String, facilityId: String) {

    // State variables to store user input
    val facilityName = remember {
        mutableStateOf("")
    }
    val facilityAddress = remember {
        mutableStateOf("")
    }
    val facilitySize = remember {
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


        // Welcome message
        Text(text = "Новое помещение", fontSize = 40.sp, color = Color.Black
        )

        OutlinedTextField(value = facilityName.value, onValueChange = {
            facilityName.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.Home, contentDescription = "person")
            },
            label = {
                Text(text = "Название помещения")
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 0.dp, 0.dp)
        )

        OutlinedTextField(value = facilityAddress.value, onValueChange = {
            facilityAddress.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.LocationOn, contentDescription = "person")
            },
            label = {
                Text(text = "Адресс")
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 0.dp, 0.dp)
        )

        // Username input field
        OutlinedTextField(value = facilitySize.value, onValueChange = {
            facilitySize.value = it
        },
            leadingIcon = {
                Icon(Icons.Default.Info, contentDescription = "person")
            },
            label = {
                Text(text = "Метраж")
            },
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier.fillMaxWidth().padding(0.dp, 20.dp, 0.dp, 0.dp)
        )

        RequestContentPermission()

        ErrorText(isVisible.value)

        val apiRepo = ApiRepository()
        val scope  = rememberCoroutineScope()


        // Login button
        OutlinedButton(onClick = {
            var response: CreateFacilityResponse
            scope.launch {
                try {
                    response = apiRepo.sendCreateFacility(CreateFacilityRequest(facilityName.value, facilityAddress.value, facilitySize.value.toFloat(), clientId.toInt()), token)

                    navController.navigate(Routes.Facilities.route
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

@Composable
fun RequestContentPermission() {
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val context = LocalContext.current
    val bitmap =  remember {
        mutableStateOf<Bitmap?>(null)
    }

    val launcher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }
    Column() {
        Button(onClick = {
            launcher.launch("image/*")
        },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            modifier = Modifier.fillMaxWidth().padding(0.dp, 25.dp, 0.dp, 0.dp)) {
            Text(text = "Pick image", color = Color.Black)
        }

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver,it)

            } else {
                val source = ImageDecoder
                    .createSource(context.contentResolver,it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let {  btm ->
                Image(bitmap = btm.asImageBitmap(),
                    contentDescription =null,
                    modifier = Modifier.size(200.dp))
            }
        }

    }
}


