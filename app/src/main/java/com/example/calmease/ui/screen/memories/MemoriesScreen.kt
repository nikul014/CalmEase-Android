package com.example.calmease.ui.screen.memories

import androidx.compose.runtime.Composable

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.calmease.viewmodel.MeditationViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.calmease.viewmodel.Meditation
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberImagePainter
import com.example.calmease.R
import com.example.calmease.ui.components.SearchBox
import com.example.calmease.viewmodel.GoodMemoriesViewModel
import com.example.calmease.viewmodel.GoodMemory
import com.google.gson.Gson

@Composable
fun GoodMemoriesScreen(viewModel: GoodMemoriesViewModel = viewModel(), navController: NavController) {
    // Observe the list of memories from the view model
    val memories by viewModel.memories.collectAsState()

    // Define search query state
    val searchQuery = remember { mutableStateOf("") }

    // Trigger search or API call
    LaunchedEffect(searchQuery.value) {
        viewModel.fetchMemories(searchTerm = searchQuery.value)
    }

    // Define filtering logic for search
    var filteredMemories by remember { mutableStateOf(emptyList<GoodMemory>()) }

    val onSearch: (String) -> Unit = { query ->
        filteredMemories = memories.filter {
            it.title.contains(query, ignoreCase = true) || it.description.contains(query, ignoreCase = true)
        }
    }

    // Layout for the screen
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Button to create new memory
        Button(
            onClick = { navController.navigate("create_memory") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
        ) {
            Text(text = "Create Memory")
        }

        // Search Box
        SearchBox(
            searchQuery = searchQuery.value,
            onSearchQueryChanged = { newQuery ->
                searchQuery.value = newQuery
                onSearch(newQuery)
            },
            onSearch =  {  }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Determine which memories to display
        val memoriesToDisplay = filteredMemories.ifEmpty { memories }

        // Display Memories
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(memoriesToDisplay) { memory ->
                GoodMemoryItem(memory = memory, navController = navController)
            }
        }
    }
}

@Composable
fun GoodMemoryItem(memory: GoodMemory, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        // Display Thumbnail or Placeholder
        Image(
            painter = rememberImagePainter("https://i.pinimg.com/736x/52/a3/93/52a393b4b0671690bb8c05cb31536cb2.jpg"),
            contentDescription = "Memory Thumbnail",
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp)
        )

        Column(modifier = Modifier.weight(1f)) {
            Text(text = memory.title, style = MaterialTheme.typography.titleMedium)
            Text(text = memory.memoryDateTime ?: "No date set", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)
            Text(text = memory.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }

        Button(
            onClick = {
                val memoryJson = Gson().toJson(memory) // Serialize the memory object to JSON
                navController.navigate("memory_detail/${Uri.encode(memoryJson)}") // Pass the JSON string as an argument

//                navController.navigate("memory_detail/${memory}")
            },
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(text = "View")
        }
    }
}

