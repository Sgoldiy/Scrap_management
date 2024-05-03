package com.cpimca.scrapwala

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun UserComplainAdmin(navController: NavController) {
    val viewModel: complainClassViewModel = viewModel()

    val complains by viewModel.complains.observeAsState(emptyList())
    val isFetching by viewModel.isFetching.observeAsState(false)

    LaunchedEffect(Unit) {
        viewModel.fetchcomplains()
    }

    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
            .background(Color(0xFF0141E61))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .clickable {
                        navController.popBackStack(Route.UserComplainAdmin, inclusive = true)
                    }
            )
            Text(
                text = "All complains Regarding improvement",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(top = 60.dp)) {
        items(complains) { complain ->
            ComplainCard(complain)
        }
    }
}


@Composable
fun ComplainCard(complain: Complain) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp
    ) {
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Name icon",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color(0xFF2962FF)
                )
                Text(
                    "complain: ${complain.complain}",
                    style = TextStyle()
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Email icon",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color(0xFFFF6F00)
                )
                Text(
                    "Email: ${complain.userEmail}",
                    style = TextStyle()
                )
            }
        }
    }
}