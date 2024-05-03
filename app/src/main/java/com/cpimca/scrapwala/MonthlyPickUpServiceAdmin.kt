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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun MonthlyPickUpServiceAdmin(
    navController: NavController,
    requests: List<PickupServiceRequest>,
    isFetching: Boolean,
    onConfirm: (PickupServiceRequest) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                if (isFetching) {
                    CircularProgressIndicator()
                } else {
                    requests.forEach { request ->
                        if (!request.isConfirmed) {
                            PickupServiceRequestCard(
                                request = request,
                                onConfirm = onConfirm,
                                navController = navController
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PickupServiceRequestCard(
    navController: NavController,
    request: PickupServiceRequest,
    onConfirm: (PickupServiceRequest) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 8.dp
    ) {
        Spacer(modifier = Modifier.width(16.dp))

        // Details on the right side
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)

        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Person, // Use the appropriate icon
                    contentDescription = "Name icon",
                    modifier = Modifier.padding(end = 8.dp), // Add padding if needed
                    tint = Color(0xFF2962FF)
                )
                Text(
                    "Name: ${request.userName ?: "-"}",
                    style = TextStyle(

                    ),

                    )
            }
            Spacer(modifier = Modifier.height(4.dp))
            val context = LocalContext.current
            Row(
            ) {
                Icon(
                    imageVector = Icons.Default.Phone, // Use the appropriate icon
                    contentDescription = "Phone icon",
                    modifier = Modifier.padding(end = 8.dp), // Add padding if needed
                    tint = Color(0xFFE5204D19)
                )
                Text(
                    "PhoneNumber: ${request.phoneNumber ?: "-"}", style = TextStyle(

                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(
            ) {
                Icon(
                    imageVector = Icons.Default.Email, // Use the appropriate icon
                    contentDescription = "Email icon",
                    modifier = Modifier.padding(end = 8.dp), // Add padding if needed
                    tint = Color(0xFFFF6F00)
                )
                Text(
                    "Email: ${request.email ?: "-"}", style = TextStyle(

                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.CheckCircle, // Use the appropriate icon
                    contentDescription = "Address icon",
                    modifier = Modifier.padding(end = 8.dp), // Add padding if needed
                    tint = Color(0xFFFF9E80)
                )
                Text(
                    "Duration of Pick-Up: ${request.selectedNumber ?: "-"} Days", style = TextStyle(

                    )
                )
            }
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
            Row {
                Icon(
                    imageVector = Icons.Default.LocationOn, // Use the appropriate icon
                    contentDescription = "Address icon",
                    modifier = Modifier.padding(end = 8.dp), // Add padding if needed
                    tint = Color.Red
                )
                Text(
                    "Address:\n${request.address ?: "-"}", style = TextStyle(

                    )
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            // Confirm button
            Row(
                modifier = Modifier.padding(horizontal = 38.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        val email = request.email ?: return@Button
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$email")
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
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = {
                        val phoneNumber = request.phoneNumber ?: return@Button
                        val phoneIntent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:$phoneNumber")
                        }
                        context.startActivity(phoneIntent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5204D19)),
                ) {
                    Text(
                        "Make Call", style = TextStyle(

                        ), color = Color.White
                    )
                }
            }
            Button(
                onClick = {
                    onConfirm(request)
                    navController.navigate(Route.DashBoard) {
                        popUpTo(Route.MonthlyPickUpServiceAdmin) {
                            inclusive = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5204D19)),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            ) {
                Text(
                    "Confirm", style = TextStyle(

                    ), color = Color.White
                )
            }
        }

    }
}

@Composable
fun UserRequestPickUp(
    viewModel: MonthlyPickUpServiceViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavController,
) {


    // Define the onConfirm function
    val onConfirm: (PickupServiceRequest) -> Unit = { request ->
        // Perform Firestore operations to transfer data from the original collection to the new collection
        // For example:
        val db = Firebase.firestore
        val confirmRequestsRef = db.collection("confirm_pickup")
        confirmRequestsRef.add(request) // Add the request to the new collection

        viewModel.removeFromCurrentCollection(request)

    }

    // Observe the list of requests from the ViewModel
    val requests by viewModel.requests.observeAsState(emptyList())

    // Observe the fetching state from the ViewModel
    val isFetching by viewModel.isFetching.observeAsState(false)

    // Call the CleaningServiceAdmin composable function with the observed data and onConfirm function
    MonthlyPickUpServiceAdmin(
        requests = requests,
        isFetching = isFetching,
        onConfirm = onConfirm,
        navController = navController
    )
    // Fetch data when the composable is initially composed
    LaunchedEffect(Unit) {
        viewModel.fetchPickupRequests()
    }

}