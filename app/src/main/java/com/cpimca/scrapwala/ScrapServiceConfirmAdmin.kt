package com.cpimca.scrapwala

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ScrapServiceConfirmAdmin(navController: NavController) {
    val viewModel: ConfirmScrapServiceViewModel = viewModel() // Initialize the view model

    // Fetch data when the composable is initially composed
    LaunchedEffect(Unit) {
        viewModel.fetchConfirmScrapRequests()
    }


    // Observe the list of confirm requests from the view model
    val ConfirmScrapPickupRequest by viewModel.ConfirmScrapRequests.observeAsState(emptyList())

    val isFetching by viewModel.isFetching.observeAsState(false) // Observe fetching state

    if (isFetching) {
        // Show progress bar while fetching data
        CircularProgressIndicator()
    } else {
        // Show confirm requests once data is fetched
        ConfirmScrapPickupRequestList(ConfirmScrapPickupRequest = ConfirmScrapPickupRequest)
    }
}

@Composable
fun ConfirmScrapPickupRequestList(ConfirmScrapPickupRequest: List<ConfirmScrapRequest>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(ConfirmScrapPickupRequest) { request ->
            ConfirmScrapPickupRequestCard(request = request)
        }
    }
}

@Composable
fun ConfirmScrapPickupRequestCard(request: ConfirmScrapRequest) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Name icon",
                    tint = Color(0xFF2962FF),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Name: ${request.userName}")
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Phone Number
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Phone icon",
                    tint = Color(0xFFE5204D19),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Phone Number: ${request.phoneNumber}")
            }
            Spacer(modifier = Modifier.height(8.dp))

            // Email
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email icon",
                    tint = Color(0xFFFF6F00),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Email: ${request.email}")
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row {
                Icon(
                    imageVector = Icons.Default.CheckCircle, // Use the appropriate icon
                    contentDescription = "Address icon",
                    modifier = Modifier.padding(end = 8.dp), // Add padding if needed
                    tint = Color(0xFFFF9E80)
                )
                Text(
                    "KG Available: ${request.iteminKG ?: "-"}", style = TextStyle(
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.DateRange, // Use the appropriate icon
                    contentDescription = "Address icon",
                    modifier = Modifier.padding(end = 8.dp), // Add padding if needed
                    tint = Color(0xFFFFAB00)
                )
                Text(
                    "Time of Pick-up: ${request.selectedTime ?: "-"}", style = TextStyle(

                    )
                )
            }
            // Address
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Address icon",
                    tint = Color.Red,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Address:\n${request.address}")
            }
            val context = LocalContext.current
            Row(
                modifier = Modifier.padding(horizontal = 38.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val email = request.email
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$email")
                            putExtra(Intent.EXTRA_SUBJECT, "Monthly Pick-up service")
                            putExtra(Intent.EXTRA_TEXT, "we have confirm your service and we will collect scrap at duration you mention")
                        }
                        context.startActivity(emailIntent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5204D19)),
                ) {
                    Text(
                        "Send Email", style = TextStyle(

                        ), color = Color.White
                    )
                }
            }
        }
    }
}