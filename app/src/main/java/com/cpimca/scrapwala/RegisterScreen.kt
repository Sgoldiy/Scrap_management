package com.cpimca.scrapwala

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun RegisterScreen(navController: NavHostController) {

    val scrollState = rememberScrollState()
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var ConfirmPassword by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isPasswordVisible2 by remember { mutableStateOf(false) }
    var locationAddress by remember { mutableStateOf(TextFieldValue("")) }
    val address: String = locationAddress.text


    val context = LocalContext.current

    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    Box(
        modifier = Modifier
            .height(180.dp)
            .fillMaxWidth()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter
    ) {

        Icon(
            painter = painterResource(id = R.drawable.baseline_arrow_back_24),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 20.dp, end = 20.dp)
                .size(20.dp)
                .align(Alignment.TopEnd)
                .clickable {
                    navController.navigate(Route.LoginScreen) {
                        popUpTo(Route.RegisterScreen) {
                            inclusive = true
                        }
                    }
                },
        )

        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }
            .build()

        Image(
            painter = rememberAsyncImagePainter(R.drawable.scrapwala, imageLoader),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(top = 20.dp)
        )
    }
    Column(
        modifier = Modifier
            .padding(top = 150.dp)
            .padding(horizontal = 30.dp)
            .verticalScroll(scrollState),

        ) {

        Text(
            text = "Create to your Account", style = TextStyle(
                color = Color.DarkGray,
                fontSize = 13.sp,
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

        var isPasswordValid by remember { mutableStateOf(true) }
        OutlinedTextField(
            modifier = Modifier
                .width(343.dp)
                .height(55.dp)
                .border(
                    1.dp,
                    if (!isPasswordValid) Color(0xFFE5204D19) else Color.Black,
                    MaterialTheme.shapes.large
                ),
            value = password,
            shape = MaterialTheme.shapes.large,
            onValueChange = {
                password = it
                isPasswordValid = it.length >= 8
            },
            placeholder = {
                Text(
                    "Password", style = TextStyle(
                        color = Color.Gray
                    )
                )
            },
            visualTransformation = if (isPasswordVisible) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation(mask = '*')
            },
            keyboardOptions = KeyboardOptions.Default.copy(),
            trailingIcon = {
                IconButton(
                    onClick = { isPasswordVisible = !isPasswordVisible },
                ) {
                    if (isPasswordVisible) {
                        Image(
                            painter = painterResource(id = R.drawable.visibility),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp), // Adjust size as needed
                            colorFilter = ColorFilter.tint(color = Color(0xE5204D19))
                        )

                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.visibilitygone),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp), // Adjust size as needed
                            colorFilter = ColorFilter.tint(color = Color(0xE5204D19))
                        )
                    }
                }
            }
        )
        if (!isPasswordValid) {
            Text(
                text = "Password must be at least 8 characters long",
                color = Color(0xFFE5204D19),
                style = TextStyle(
                    fontSize = 12.sp, fontFamily = FontFamily.Default
                ),
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            modifier = Modifier
                .width(343.dp)
                .height(55.dp)
                .border(1.dp, Color.Black, MaterialTheme.shapes.large),
            value = ConfirmPassword,
            shape = MaterialTheme.shapes.large,
            onValueChange = {
                ConfirmPassword = it
            },
            placeholder = {
                Text(
                    "Confirm Password", style = TextStyle(
                        color = Color.Gray
                    )
                )
            },
            visualTransformation = if (isPasswordVisible2) {
                VisualTransformation.None
            } else {
                PasswordVisualTransformation(mask = '*')
            },
            keyboardOptions = KeyboardOptions.Default.copy(),
            trailingIcon = {
                IconButton(
                    onClick = { isPasswordVisible2 = !isPasswordVisible2 },
                ) {
                    if (isPasswordVisible2) {
                        Image(
                            painter = painterResource(id = R.drawable.visibility),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp), // Adjust size as needed
                            colorFilter = ColorFilter.tint(color = Color(0xE5204D19))
                        )

                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.visibilitygone),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp), // Adjust size as needed
                            colorFilter = ColorFilter.tint(color = Color(0xE5204D19))
                        )
                    }

                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val context = LocalContext.current

        var isFetching by remember { mutableStateOf(false) }

        val requestPermissionLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
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
                            context,
                            "Location permission is required",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                }
            )
        if (isFetching) {
            CircularProgressIndicator(
                color = Color(0xFF0E5204D19)
            )
        } else {
            OutlinedTextField(
                modifier = Modifier
                    .width(343.dp),
                value = locationAddress,
                shape = MaterialTheme.shapes.large,
                onValueChange = {
                    locationAddress = it
                },
                placeholder = {
                    Text(
                        text = "Address",
                        style = TextStyle(
                            color = Color.Gray
                        )
                    )
                },
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        ClickableText(
            modifier = Modifier
                .align(Alignment.End),
            text = AnnotatedString("Get Current Location"),
            style = TextStyle(
                color = Color(0xFF0E5204D19),
                textDecoration = TextDecoration.Underline
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
                    requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))



        Button(
            onClick = {
                if (name.isEmpty() || phone.isEmpty() || ConfirmPassword.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        context, "Please fill in all fields", Toast.LENGTH_SHORT
                    ).show()
                } else if (!isValidEmail(email)) {
                    Toast.makeText(
                        context, "Invalid email address", Toast.LENGTH_SHORT
                    ).show()
                } else if (password.length < 8) {
                    Toast.makeText(
                        context,
                        "Password must be at least 8 characters long",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != ConfirmPassword) {
                    Toast.makeText(
                        context,
                        "Password did not match",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val firebaseAnalytics = FirebaseAnalytics.getInstance(context)
                    val bundle = Bundle()
                    bundle.putString(FirebaseAnalytics.Param.METHOD, "email")
                    firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle)
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // User registration successful, now send email verification
                                val user = FirebaseAuth.getInstance().currentUser
                                user?.let {
                                    user.sendEmailVerification()
                                        .addOnCompleteListener { verificationTask ->
                                            if (verificationTask.isSuccessful) {
                                                // Email verification sent successfully
                                                Toast.makeText(
                                                    context,
                                                    "Email verification sent successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                // Redirect user to a screen indicating that a verification email has been sent
                                            } else {
                                                // Failed to send email verification
                                                Toast.makeText(
                                                    context,
                                                    "Email verification sent successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                // Show error message to the user
                                            }
                                        }
                                }
                                // Save user data in Firestore
                                val userData = UserData(name, email, phone, password, address)

                                FirebaseFirestore.getInstance().collection("users")
                                    .document(user!!.uid)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        navController.navigate("LoginScreen") {
                                            popUpTo(Route.RegisterScreen) {
                                                inclusive = true
                                            }
                                        }

                                        val params = Bundle().apply {
                                            putString("registration_status", "success")
                                        }
                                        firebaseAnalytics.logEvent("user_registration", params)
                                    }
                                    .addOnFailureListener { e ->
                                        // Error saving user data
                                        Log.e("RegisterScreen", "Error adding document", e)
                                    }
                            } else {
                                // User registration failed
                                Log.e("RegisterScreen", "User registration failed", task.exception)

                                val params = Bundle().apply {
                                    putString("registration_status", "failure")
                                }
                                firebaseAnalytics.logEvent("user_registration", params)
                                // Show error message to the user
                            }
                        }
                }
            },
            modifier = Modifier
                .width(343.dp)
                .height(52.dp)
                .border(1.dp, Color(0xFF0E5204D19), MaterialTheme.shapes.large),
            shape = MaterialTheme.shapes.large,
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


// for current location


fun hasLocationPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED
}

fun requestLocationUpdates(context: Context, callback: (Location) -> Unit) {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager

    if (locationManager != null &&
        context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    ) {
        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            0L,
            0f,
            object : LocationListener {
                override fun onLocationChanged(location: Location) {
                    Log.d("Location", "Received location: $location")
                    callback(location)
                    locationManager.removeUpdates(this)
                }

                override fun onProviderDisabled(provider: String) {}
                override fun onProviderEnabled(provider: String) {}
                override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
            })
    } else {
        Log.e("Location", "Permission not granted")
    }
}

fun getAddressFromLocation(
    context: Context,
    location: Location,
    callback: (String) -> Unit,
) {
    val geocoder = Geocoder(context, Locale.getDefault())
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val addresses = geocoder.getFromLocation(location.latitude, location.longitude, 1)
            val address = addresses!!.firstOrNull()?.getAddressLine(0) ?: "Address not found"
            callback(address)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}






