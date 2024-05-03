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

@Composable
fun MonthlyScrapServiceComplain(navController: NavController) {
    var complainText by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .padding(top = 90.dp)
            .fillMaxWidth()
            .height(300.dp)
    ) {
        OutlinedTextField(
            value = complainText,
            onValueChange = { complainText = it },
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
                navController.popBackStack(Route.MonthlyScrapServiceComplain, inclusive = true)
                storeFeedbackData(complainText, navController)
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