package com.cpimca.scrapwala

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun CategoryServiceUser(navController: NavController) {
    val viewModel: CategoryServiceViewModel = viewModel()

    var locationAddressForCleaningService by remember { mutableStateOf(TextFieldValue("")) }
    var isFetching by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var selectedItem by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("9:00 AM") }
    var showDropdownMenu1 by remember { mutableStateOf(false) }
    var showDropdownMenu by remember { mutableStateOf(false) }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // First outlined text field with dropdown
        Box {
            OutlinedTextField(
                value = "Selected Item: $selectedItem",
                onValueChange = {},
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .width(343.dp)
                    .height(52.dp),
                trailingIcon = {
                    IconButton(onClick = {
                        // Toggle dropdown menu visibility
                        showDropdownMenu1 = !showDropdownMenu1
                    }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                },
                readOnly = true // Make the text field read-only
            )

            // Dropdown menu
            DropdownMenu(
                expanded = showDropdownMenu1,
                onDismissRequest = { showDropdownMenu1 = false },
                modifier = Modifier
                    .width(343.dp)
            ) {
                DropdownMenuItem(onClick = {
                    selectedItem = "Aluminum"
                    showDropdownMenu1 = false // Close the menu after selection
                }) {
                    Text(text = "Aluminum")
                }
                DropdownMenuItem(onClick = {
                    selectedItem = "Copper"
                    showDropdownMenu1 = false // Close the menu after selection
                }) {
                    Text(text = "Copper")
                }
                DropdownMenuItem(onClick = {
                    selectedItem = "Copper"
                    showDropdownMenu1 = false // Close the menu after selection
                }) {
                    Text(text = "Plastic")
                }
                DropdownMenuItem(onClick = {
                    selectedItem = "Copper"
                    showDropdownMenu1 = false // Close the menu after selection
                }) {
                    Text(text = "Fiber")
                }
                DropdownMenuItem(onClick = {
                    selectedItem = "Copper"
                    showDropdownMenu1 = false // Close the menu after selection
                }) {
                    Text(text = "Metal")
                }
                DropdownMenuItem(onClick = {
                    selectedItem = "Steel"
                    showDropdownMenu1 = false // Close the menu after selection
                }) {
                    Text(text = "Steel")
                }
                DropdownMenuItem(onClick = {
                    selectedItem = "Paper"
                    showDropdownMenu1 = false // Close the menu after selection
                }) {
                    Text(text = "Paper")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        // Second outlined text field with time picker
        Box {
            OutlinedTextField(
                value = "Select Pickup Time: $selectedTime",
                onValueChange = {},
                shape = MaterialTheme.shapes.large,
                modifier = Modifier
                    .width(343.dp)
                    .height(52.dp),
                readOnly = true, // Make the text field read-only
                trailingIcon = {
                    IconButton(onClick = {
                        // Toggle dropdown menu visibility
                        showDropdownMenu = !showDropdownMenu
                    }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown")
                    }
                }
            )
            if (showDropdownMenu) {
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = { showDropdownMenu = false },
                    modifier = Modifier.width(343.dp)
                ) {
                    val timeOptions = listOf("9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM")
                    timeOptions.forEach { time ->
                        DropdownMenuItem(onClick = {
                            selectedTime = time // Set the selected time directly
                            showDropdownMenu = false // Close the menu after selection
                        }) {
                            Text(text = time)
                        }
                    }
                }
            }

        }
        val requestPermissionLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission(),
                onResult = { isGranted: Boolean ->
                    if (isGranted) {
                        isFetching = true // Indicate that location fetching is in progress
                        requestLocationUpdates(context) { location ->
                            getAddressFromLocation(context, location) { address ->
                                Log.d("Location", "Fetched address: $address")
                                locationAddressForCleaningService = TextFieldValue(address)
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
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                modifier = Modifier
                    .width(343.dp),
                shape = MaterialTheme.shapes.large,
                value = locationAddressForCleaningService,
                onValueChange = {
                    locationAddressForCleaningService = it
                },
                placeholder = {
                    Text(
                        text = "Address of area",
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
                .align(Alignment.End)
                .padding(end = 20.dp),
            text = AnnotatedString("Get Current Location"),
            style = TextStyle(
                color = Color(0xFF0E5204D19),
                textDecoration = TextDecoration.Underline
            ),
            // one click
            onClick = {
                if (hasLocationPermission(context)) {
                    isFetching = true
                    requestLocationUpdates(context) { location ->
                        getAddressFromLocation(context, location) { address ->
                            Log.d("Location", "Fetched address: $address")
                            locationAddressForCleaningService = TextFieldValue(address)
                            isFetching = false
                        }
                    }
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                viewModel.storeRequest(
                    locationAddressForCleaningService.text,
                    selectedItem,
                    selectedTime
                )
                navController.navigate(Route.CategoryService) {
                    popUpTo(Route.CategoryServiceUser) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .width(343.dp)
                .height(52.dp)
                .border(1.dp, Color(0xFFE5204D19), shape = MaterialTheme.shapes.large),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5204D19)),
        ) {
            Text(
                text = "REQUEST", style = TextStyle(
                    color = Color.White,
                    fontSize = 13.sp,
                )
            )
        }
    }
}