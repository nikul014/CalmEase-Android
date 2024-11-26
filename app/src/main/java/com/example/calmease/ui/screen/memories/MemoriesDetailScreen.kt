package com.example.calmease.ui.screen.memories

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.example.calmease.R
import com.example.calmease.ui.theme.CalmBackground
import com.example.calmease.viewmodel.GoodMemory
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryDetailsScreen(
    memoryJson: String,
    navController: NavController
) {
    val memory = Gson().fromJson(memoryJson, GoodMemory::class.java)

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(CalmBackground),
            horizontalAlignment = Alignment.Start
        ) {



            // Image or Placeholder with Rounded Corners
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White // White background for the card
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(bottom = 16.dp)
            ) {
                AsyncImage(
                    model = memory.imageUrl
                        ?: "https://i.pinimg.com/736x/52/a3/93/52a393b4b0671690bb8c05cb31536cb2.jpg",
                    contentDescription = memory.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(16.dp)), // Apply rounded corners

                    contentScale = ContentScale.Crop
                )
            }


            // Title Section
            Text(
                text = memory.title,
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            // Description Section
            Text(
                text = memory.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )


            Text(
                text = memory.memory_date_time ?: "No Date Provided",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(bottom = 16.dp)
            )

        }
    }
}
