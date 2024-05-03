package com.cpimca.scrapwala

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
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MonthlyPickUpServiceConfirmUser(navController: NavController) {
    val viewModel: ConfirmPickupServiceUserViewModel = viewModel() // Initialize the view model

    // Fetch data when the composable is initially composed
    LaunchedEffect(Unit) {
        viewModel.fetchConfirmPickupRequestsForCurrentUser()
    }

    // Get the logged-in user's email from your authentication system
    val loggedInUserEmail = FirebaseAuth.getInstance().currentUser?.email

    // Observe the list of confirm requests from the view model
    val ConfirmPickUpRequests by viewModel.ConfirmPickupRequests.observeAsState(emptyList())

    val isFetching by viewModel.isFetching.observeAsState(false) // Observe fetching state

    if (isFetching) {
        // Show progress bar while fetching data
        CircularProgressIndicator(
            color = Color.Black,
        )
    } else {
        // Filter requests by logged-in user's email
        val userRequests = ConfirmPickUpRequests.filter { it.email == loggedInUserEmail }

        if (userRequests.isEmpty()) {
            // Show message if no requests found for the logged-in user
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No requests found for the logged-in user.")
            }
        } else {
            // Show confirm requests for the logged-in user
            ConfirmPickUpRequestsListUser(ConfirmPickUpRequests = userRequests)
        }
    }
}

@Composable
fun ConfirmPickUpRequestsListUser(ConfirmPickUpRequests: List<ConfirmPickupRequest>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 60.dp , top = 80.dp)
    ) {
        items(ConfirmPickUpRequests) { request ->
            ConfirmPickUpRequestCardUser(request = request)
        }
    }
}

@Composable
fun ConfirmPickUpRequestCardUser(request: ConfirmPickupRequest) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 16.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Thank-you for your pick-up service")
            // Name
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Name icon",
                    tint = Color(0xFF2962FF),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Your Name: ${request.userName}")
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
                Text("Your Phone Number: ${request.phoneNumber}")
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
                Text("Your Email: ${request.email}")
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
                    "Duration of Pick-up: ${request.selectedNumber ?: "-"} Days", style = TextStyle(

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
                Text("Your Address:\n${request.address}")
            }
            Text("We will arrive to pick your scrap soon\nscrap OUT money IN!")
        }
    }
}