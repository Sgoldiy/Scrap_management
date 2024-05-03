package com.cpimca.scrapwala

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun AccountFregment(navController: NavController, onItemSelected: (Int) -> Unit) {
    var searchText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 55.dp)
            .background(Color(0xFF0E5204D19))
    ) {
        androidx.compose.material3.Text(
            text = "search a name of worker to conformation",
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp // Adjust font size as needed
            ),
            modifier = Modifier.padding(8.dp)
        )

        // Search bar
        SearchBar(
            searchText = searchText,
            onSearchTextChanged = { searchText = it }
        )
    }

    val viewModel: RegisterViewModel = viewModel()
    val workers = viewModel.getWorkersData().observeAsState(initial = emptyList())

    val filteredWorkers = workers.value.filter {
        it.name.contains(searchText, ignoreCase = true)
    }
    LazyColumn(contentPadding = PaddingValues(16.dp), modifier = Modifier.padding(top = 150.dp)) {
        items(filteredWorkers) { worker ->
            WorkerCardUser(worker) {}
        }
    }
}


@Composable
fun WorkerCardUser(worker: Worker, onDelete: () -> Unit) {
    val context = LocalContext.current
    val viewModel: RegisterViewModel = viewModel()

    // Launch phone call
    val makeCall =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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
                    tint = Color(0xFF0E5204D19),
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Worker Name: ${worker.name}")
            }
        }
    }
}

@Composable
fun SearchBar(searchText: String, onSearchTextChanged: (String) -> Unit) {
    androidx.compose.material3.TextField(
        value = searchText,
        onValueChange = { onSearchTextChanged(it) },
        label = { Text("Search by worker name") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}
