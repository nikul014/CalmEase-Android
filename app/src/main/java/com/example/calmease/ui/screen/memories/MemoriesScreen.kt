package com.example.calmease.ui.screen.memories

import android.graphics.Paint.Align
import androidx.compose.runtime.Composable

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
import com.example.calmease.R
import com.example.calmease.ui.components.SearchBox
import com.example.calmease.ui.screen.meditation.highlightText
import com.example.calmease.viewmodel.GoodMemoriesViewModel
import com.example.calmease.viewmodel.GoodMemory
import com.google.gson.Gson

@Composable
fun GoodMemoriesScreen(
    viewModel: GoodMemoriesViewModel = viewModel(),
    navController: NavController
) {
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
            it.title.contains(query, ignoreCase = true) || it.description.contains(
                query,
                ignoreCase = true
            )
        }
    }

    // Layout for the screen
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

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
        // Floating Action Button for creating new memory
        ExtendedFloatingActionButton(
            onClick = { navController.navigate("create_memory") },
            modifier = Modifier
                .padding(24.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Outlined.Mood, // Smiley face icon
                contentDescription = "Create Memory",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Create Memory",

                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),

                )

        }
    }
}

@Composable
fun GoodMemoryItem(memory: GoodMemory, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp) // Added horizontal padding for better spacing
            .clickable {
                val memoryJson = Gson().toJson(memory) // Serialize the memory object to JSON
                navController.navigate("memory_detail/${Uri.encode(memoryJson)}") // Pass the JSON string as an argument
            },
        shape = RoundedCornerShape(16.dp), // Rounded corners for the card
        colors = CardDefaults.cardColors(containerColor = Color.White) // White background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Image with fixed height and proper content scaling
            AsyncImage(
                model = "https://i.pinimg.com/736x/52/a3/93/52a393b4b0671690bb8c05cb31536cb2.jpg",
                contentDescription = memory.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)        .clip(RoundedCornerShape(16.dp)), // Apply rounded corners

                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp)) // Add space between image and text

            // Title with search highlight if necessary
            Text(
                text = memory.title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.fillMaxWidth(),
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp)) // Space between title and description

            // Description with search highlight if necessary
            Text(
                memory.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp)) // Space between description and duration

            // Duration with a more visible style
            Text(
                text = memory.memory_date_time?:"",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

