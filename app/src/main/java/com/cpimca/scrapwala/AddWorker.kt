package com.cpimca.scrapwala

import android.Manifest
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun AddWorker(navController: NavController) {


    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var isLoading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }

    val galleryLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent(),
            onResult = { uri ->
                uri?.let {
                    imageUri = it
                    isLoading = true // Indicate that image loading is in progress
                    error = null
                }
            })

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    val firestore = Firebase.firestore
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var locationAddress by remember { mutableStateOf(TextFieldValue("")) }
    val address: String = locationAddress.text
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        imageUri?.let { }
        Image(
            modifier = Modifier
                .padding(top = 30.dp)
                .height(275.dp)
                .width(275.dp)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    galleryLauncher.launch("image/*")
                },
            painter = getPainter(imageUri),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = "Add a adharcard photo of worker from a gallery", style = TextStyle(
                color = Color(0xFF0E5204D19)
            )
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier
                .width(343.dp)
                .height(55.dp)
                .border(1.dp, Color.Black, MaterialTheme.shapes.large),
            value = name,
            shape = MaterialTheme.shapes.large,
            onValueChange = {
                name = it
            },
            placeholder = {
                Text(
                    "Name", style = TextStyle(
                        color = Color.Gray
                    )
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(),
        )

        Spacer(modifier = Modifier.height(24.dp))


        var isEmailValid by remember { mutableStateOf(true) }
        val emailErrorText by remember { mutableStateOf("Invalid email address") }
        OutlinedTextField(
            modifier = Modifier
                .width(343.dp)
                .height(55.dp)
                .border(
                    1.dp,
                    if (isEmailValid) Color.Black else Color(0xFFE5204D19),
                    MaterialTheme.shapes.large
                ),
            value = email,
            shape = MaterialTheme.shapes.large,
            onValueChange = {
                email = it
                isEmailValid = isValidEmail(it)
            },
            placeholder = {
                Text(
                    "Email", style = TextStyle(
                        color = Color.Gray
                    )
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(),
            isError = !isEmailValid,
            singleLine = true,
            visualTransformation = VisualTransformation.None
        )
        if (!isEmailValid) {
            Text(
                text = emailErrorText, color = Color(0xFFE5204D19), style = TextStyle(
                    fontSize = 12.sp, fontFamily = FontFamily.Default
                ), modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier
                .width(343.dp)
                .height(55.dp)
                .border(1.dp, Color.Black, MaterialTheme.shapes.large),
            value = phone,
            shape = MaterialTheme.shapes.large,
            onValueChange = {
                phone = it
            },
            placeholder = {
                Text(
                    "Phone number", style = TextStyle(
                        color = Color.Gray
                    )
                )
            },
            keyboardOptions = KeyboardOptions.Default.copy(),
        )
        Spacer(modifier = Modifier.height(24.dp))
        val context = LocalContext.current

        var isFetching by remember { mutableStateOf(false) }

        val requestPermissionLauncher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted: Boolean ->
                    if (isGranted) {
                        isFetching = true // Indicate that location fetching is in progress
                        requestLocationUpdates(context) { location ->
                            getAddressFromLocation(context, location) { address ->
                                Log.d("Location", "Fetched address: $address")
                                locationAddress = TextFieldValue(address)
                                isFetching = false
                            }
                        }
                    } else {
                        Toast.makeText(
                            context, "Location permission is required", Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        if (isFetching) {
            CircularProgressIndicator(
                color = Color(0xFF0E5204D19)
            )
        } else {
            OutlinedTextField(
                modifier = Modifier.width(343.dp),
                value = locationAddress,
                shape =  MaterialTheme.shapes.large,
                onValueChange = {
                    locationAddress = it
                },
                placeholder = {
                    Text(
                        text = "Address", style = TextStyle(
                            color = Color.Gray
                        )
                    )
                },
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        ClickableText(modifier = Modifier
            .align(Alignment.End)
            .padding(end = 20.dp),
            text = AnnotatedString("Get Current Location"),
            style = TextStyle(
                color = Color(0xFF0E5204D19), textDecoration = TextDecoration.Underline
            ),
            onClick = {
                if (hasLocationPermission(context)) {
                    isFetching = true
                    requestLocationUpdates(context) { location ->
                        getAddressFromLocation(context, location) { address ->
                            Log.d("Location", "Fetched address: $address")
                            locationAddress = TextFieldValue(address)
                            isFetching = false
                        }
                    }
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            })

        Spacer(modifier = Modifier.height(16.dp))



        Button(
            onClick = {
                imageUri?.let { uri ->
                    val viewModel = RegisterViewModel()
                    viewModel.createWorker(
                        name = name,
                        phone = phone,
                        email = email,
                        address = locationAddress.text,
                        imageUri = uri
                    )
                }
                navController.navigate(Route.DashBoard) {
                    popUpTo(Route.AddWorker) {
                       inclusive = true
                    }
                }
            },
            modifier = Modifier
                .width(343.dp)
                .height(52.dp)
                .border(1.dp, Color(0xFF0E5204D19), shape = MaterialTheme.shapes.large),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0E5204D19)),

            ) {
            Text(
                text = "REGISTER", style = TextStyle(
                    color = Color.White,
                    fontSize = 13.sp,
                )
            )
        }
    }
}

@Composable
fun getPainter(imageUri: Uri?): Painter {
    return if (imageUri != null) {
        rememberAsyncImagePainter(
            model = imageUri,
        )
    } else {
        // Placeholder painter resource
        painterResource(id = R.drawable.baseline_photo_size_select_actual_24)
    }
}

