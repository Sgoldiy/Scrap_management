package com.cpimca.scrapwala

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun MonthlyPickUpServiceComplain(navController: NavController) {
    var feedbackText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(top = 90.dp)
            .fillMaxWidth()
            .height(300.dp)
    ) {
        OutlinedTextField(
            value = feedbackText,
            onValueChange = { feedbackText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text(text = "Enter your complain...") },
            singleLine = false
        )

        // Submit Button
        Button(
            onClick = {
                // Store feedback data in Firestore
                navController.popBackStack(Route.MonthlyPickUpServiceComplain, inclusive = true)
                storeFeedbackData(feedbackText, navController)
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .height(45.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0E5204D19))
        ) {
            Text(
                text = "Submit",
                color = Color.White
            )
        }
    }
}


// Function to store feedback data in Firestore
fun storeFeedbackData(feedback: String, navController: NavController) {
    val currentUser = FirebaseAuth.getInstance().currentUser

    // Get a reference to the Firestore database
    val db = FirebaseFirestore.getInstance()
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val currentTime = dateFormat.format(calendar.time)

    // Create a new document with a generated ID
    val feedbackData = hashMapOf(
        "complain" to feedback,
        "timestamp" to currentTime, // Current timestamp
        "userId" to currentUser?.uid, // Current user ID
        "userEmail" to currentUser?.email // Current user email
    )

    // Add a new document with a generated ID
    db.collection("complain")
        .add(feedbackData)
        .addOnSuccessListener { documentReference ->
            // Document added successfully
            println("Feedback added with ID: ${documentReference.id}")

            // Navigate back after submission
            navController.popBackStack(Route.MonthlyPickUpServiceComplain, inclusive = true)
        }
        .addOnFailureListener { e ->
            // Error handling
            println("Error adding feedback: $e")
        }
}