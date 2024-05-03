package com.cpimca.scrapwala

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

@SuppressLint("InvalidColorHexValue")
@Composable
fun LoginScreen(navController: NavHostController) {
    val context = LocalContext.current


    fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    Box(
        modifier = Modifier
            .height(250.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter

    ) {
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
                .size(175.dp)
                .padding(top = 20.dp)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 250.dp)
            .padding(horizontal = 30.dp)
    ) {
        var email by remember { mutableStateOf("") }
        var isEmailValid by remember { mutableStateOf(true) }
        val emailErrorText by remember { mutableStateOf("Invalid email address") }


        Text(
            text = "Login to your Account", style = TextStyle(
                color = Color.DarkGray,
                fontSize = 13.sp,
            )
        )
        Spacer(modifier = Modifier.height(20.dp))

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
        var password by remember { mutableStateOf("") }
        var isPasswordValid by remember { mutableStateOf(true) }
        var isPasswordVisible by remember { mutableStateOf(false) }

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
        Spacer(modifier = Modifier.height(13.dp))
        ClickableText(
            modifier = Modifier.align(Alignment.End),
            text = AnnotatedString("Forgot password?"),
            onClick = {
                if (email.isEmpty()) {
                    Toast.makeText(
                        context,
                        "Fill password first for receive a link of password change",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    context,
                                    "Password reset email sent successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Password reset email sent successfully
                                // Inform the user that a password reset email has been sent to their email address
                            } else {
                                Toast.makeText(
                                    context,
                                    "Password reset email sending failed",
                                    Toast.LENGTH_SHORT
                                ).show()
                                // Password reset email sending failed
                                val exception = task.exception
                                if (exception is FirebaseAuthInvalidUserException) {
                                    Toast.makeText(
                                        context, " User does not exist", Toast.LENGTH_SHORT
                                    ).show()
                                    // User does not exist
                                    // Show an error message to the user indicating that the provided email is not registered
                                } else {
                                    Toast.makeText(
                                        context, "Other errors", Toast.LENGTH_SHORT
                                    ).show()
                                    // Other errors
                                    // Show a generic error message to the user
                                }
                            }
                        }
                }
            },
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray,
            )
        )
        val authViewModel = viewModel<AuthViewModel>()
        authViewModel.initSharedPreferences(LocalContext.current)
        Spacer(modifier = Modifier.height(70.dp))
        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        context, "Please fill in all fields", Toast.LENGTH_SHORT
                    ).show()
                } else if (!isValidEmail(email)) {
                    Toast.makeText(
                        context, "Invalid email address", Toast.LENGTH_SHORT
                    ).show()
                } else if (password.length < 8) {
                    Toast.makeText(
                        context, "Password must be at least 8 characters long", Toast.LENGTH_SHORT
                    ).show()
                } else if (email == "admin123@gmail.com" && password == "12345678") {

                    // admin login successful
                    authViewModel.saveAdminStatus(true)
                    navController.navigate("DashBoard") {
                        popUpTo(Route.LoginScreen) {
                            inclusive = true
                        }
                    }
                    Toast.makeText(
                        context, "Admin Login Successful", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                // User login successful
                                val user = FirebaseAuth.getInstance().currentUser


                                if (user != null && user.isEmailVerified) {
                                    authViewModel.saveLoginStatus(true)
                                    navController.navigate("HomeScreen") {
                                        popUpTo(Route.LoginScreen) {
                                            inclusive = true
                                        }
                                    }
                                    Toast.makeText(
                                        context, "Login Successful", Toast.LENGTH_SHORT
                                    ).show()
                                    // User is logged in and email is verified
                                    // Proceed to navigate to the next screen or perform any required action
                                } else {
                                    navController.navigate("HomeScreen") {
                                        popUpTo(Route.LoginScreen) {
                                            inclusive = true
                                        }
                                    }
                                    Toast.makeText(
                                        context,
                                        "Show a message to the user indicating that they need to verify their email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // User is logged in but email is not verified
                                    // Show a message to the user indicating that they need to verify their email
                                }
                            } else {
                                // User login failed
                                val exception = task.exception
                                if (exception is FirebaseAuthInvalidUserException) {
                                    Toast.makeText(
                                        context, "User does not exist", Toast.LENGTH_SHORT
                                    ).show()
                                    // User does not exist
                                    // Show an error message to the user indicating that the provided email is not registered
                                } else if (exception is FirebaseAuthInvalidCredentialsException) {
                                    Toast.makeText(
                                        context,
                                        "Invalid credentials (e.g., wrong password)",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    // Invalid credentials (e.g., wrong password)
                                    // Show an error message to the user indicating that the provided credentials are incorrect
                                } else {
                                    Toast.makeText(
                                        context, "Other authentication errors", Toast.LENGTH_SHORT
                                    ).show()
                                    // Other authentication errors
                                    // Show a generic error message to the user
                                }
                            }
                        }
                }
            },
            modifier = Modifier
                .width(343.dp)
                .height(52.dp)
                .border(1.dp, Color(0xFFE5204D19), shape = MaterialTheme.shapes.large),
            shape = MaterialTheme.shapes.large,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5204D19)),

            ) {
            Text(
                text = "LOG IN", style = TextStyle(
                    color = Color.White,
                    fontSize = 13.sp,
                )
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
        val annotatedText = buildAnnotatedString {
            append("Didn't have a account  ")
            pushStyle(
                SpanStyle(
                    color = Color(0xFF0E5204D19), textDecoration = TextDecoration.Underline
                )
            )
            append("Register")
            pop()
        }
        ClickableText(
            text = annotatedText,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                navController.navigate("RegisterScreen")
                {
                    popUpTo(Route.LoginScreen) {
                        inclusive = true
                    }
                }
            },
            style = TextStyle(fontSize = 16.sp, color = Color.Gray)
        )
    }
}

