package com.cpimca.scrapwala

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestFregment(navController: NavController , onItemSelected: (Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp, bottom = 75.dp)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE5204D19))
            ) {
                Text(
                    text = "Price for each service",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp // Adjust font size as needed
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .border(
                        width = 0.5.dp,
                        color = Color(0xFFE5204D19),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Text(
                    text = "Price per KG", // Customize text as needed
                    style = TextStyle(
                        color = Color.White, // Set text color to white
                        fontSize = 16.sp // Adjust font size as needed
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .background(Color(0xFFE5204D19))
                )
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "0-10 KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "15RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "10-20 KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "17RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "20-30 KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "20RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "30-40 KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "20RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "40-50 KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "23RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .border(
                        width = 0.5.dp,
                        color = Color(0xFFE5204D19),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Text(
                    text = "Price for item per KG", // Customize text as needed
                    style = TextStyle(
                        color = Color.White, // Set text color to white
                        fontSize = 16.sp // Adjust font size as needed
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .background(Color(0xFFE5204D19))
                )
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "Aluminum", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "15RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "Plastic", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "15RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "Metal", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "25RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "Fiber", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "15RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "Copper", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "17RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "Steel", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "20RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "Paper", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "10RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .border(
                        width = 0.5.dp,
                        color = Color(0xFFE5204D19),
                        shape = RoundedCornerShape(12.dp)
                    ),
                colors = CardDefaults.cardColors(Color.White)
            ) {
                Text(
                    text = "Price for a Pick-up Service", // Customize text as needed
                    style = TextStyle(
                        color = Color.White, // Set text color to white
                        fontSize = 16.sp // Adjust font size as needed
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .background(Color(0xFFE5204D19))
                )
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "Every 15 days", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "20RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .background(Color(0xFFE5204D19))
                        .fillMaxWidth()
                )
                Row(Modifier.padding(horizontal = 16.dp , vertical = 8.dp)){
                    Text(
                        text = "Every 30 Days", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.Start,
                    )
                    Text(
                        text = "17RS/KG", // Customize text as needed
                        style = TextStyle(
                            color = Color(0xFFE5204D19), // Set text color to white
                            fontSize = 16.sp // Adjust font size as needed
                        ),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}