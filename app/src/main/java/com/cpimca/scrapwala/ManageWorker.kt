package com.cpimca.scrapwala

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ManageWorker(navController: NavController) {
    val viewModel: RegisterViewModel = viewModel()
    val workers = viewModel.getWorkersData().observeAsState(initial = emptyList())

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(workers.value) { worker ->
            WorkerCard(navController = navController,worker) {
            }
        }
    }
}

@Composable
fun WorkerCard(navController: NavController,worker: Worker, onDelete: () -> Unit) {
    val context = LocalContext.current
    val viewModel: RegisterViewModel = viewModel()

    // Launch phone call
    val makeCall = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Call successful
        }
    }
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
                Text("Worker Name: ${worker.name}")
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
                Text("Worker Phone Number: ${worker.phone}")
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
                Text("Worker Email: ${worker.email}")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Buttons Row
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        viewModel.deleteWorker(workerName = worker.name) { success ->
                            if (success as Boolean) {
                                onDelete() // Refresh the page
                            }
                        }
                        navController.navigate(Route.ManageWorker)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5204D19)),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Delete Worker",
                        color = Color.White)
                }

            }

            Spacer(modifier = Modifier.height(8.dp))

            // Call/Massage Buttons Row
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Call Button
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL)
                        intent.data = Uri.parse("tel:${worker.phone}")
                        makeCall.launch(intent)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5204D19)),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("Call",
                        color = Color.White)
                }
                Spacer(modifier = Modifier.width(20.dp))
                // Message Button
                val sendMessage = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        // Message sent successfully
                    }
                }
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("smsto:${worker.phone}")
                            putExtra("sms_body", "Location : \n Work : \n Time :  \n \n After complete this Work give us massage Done.") // You can pre-fill the message here
                        }
                        if (intent.resolveActivity(context.packageManager) != null) {
                            sendMessage.launch(intent)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE5204D19)),
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text("Message", color = Color.White)
                }
            }
        }
    }
}
