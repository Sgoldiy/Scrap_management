package com.cpimca.scrapwala

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryService(navController: NavController) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(140.dp)
                        .width(130.dp),
                    colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                    elevation = CardDefaults.cardElevation(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .height(133.dp)
                            .width(123.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                navController.navigate(Route.CategoryServiceUser)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,

                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.dustbin),
                            contentDescription = "android image",
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "Category sell",
                            style = TextStyle(
                                color = Color(0xFF0E5204D19)
                            )
                        )
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(140.dp)
                        .width(130.dp),
                    colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                    elevation = CardDefaults.cardElevation(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .height(133.dp)
                            .width(123.dp)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .clickable {
                                navController.navigate(Route.CategoryServiceConfirmUser)
                            },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,

                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.requestconfirm),
                            contentDescription = "android image",
                            modifier = Modifier.size(100.dp)
                        )
                        Text(
                            text = "Confirm Request",
                            style = TextStyle(
                                color = Color(0xFF0E5204D19)
                            )
                        )
                    }
                }
            }
            Card(
                modifier = Modifier
                    .padding(top = 8.dp, start = 42.dp)
                    .height(140.dp)
                    .width(130.dp),
                colors = CardDefaults.cardColors(Color(0xFF0E5204D19)),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .height(133.dp)
                        .width(123.dp)
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .clickable {
                            navController.navigate(Route.MonthlyCategoryServiceComplain)
                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,

                    ) {
                    Image(
                        painter = painterResource(id = R.drawable.intro1),
                        contentDescription = "android image",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = "Complain",
                        style = TextStyle(
                            color = Color(0xFF0E5204D19)
                        )
                    )
                }
            }
        }
    }
}