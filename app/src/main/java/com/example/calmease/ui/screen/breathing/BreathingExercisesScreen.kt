package com.example.calmease.ui.screen.breathing

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BreathingExercisesScreen(
    categoryId: Int,
    navController: NavController,
    viewModel: com.example.calmease.viewmodel.BreathingViewModel
) {
    // Fetch the category based on the ID
    val category = viewModel.categories.value.find { it.id == categoryId }

    if (category != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(category.exercises) { exercise ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp) // Added horizontal padding for better spacing
                        .clickable {
                            // Encode the parameters for navigation
                            val encodedTiming = java.net.URLEncoder.encode(exercise.timing, "UTF-8")
                            val encodedCategoryImage = java.net.URLEncoder.encode(category.category_image, "UTF-8")
                            val encodedBackgroundImage = java.net.URLEncoder.encode(category.background_image, "UTF-8")
                            val encodedAudioUrl = java.net.URLEncoder.encode(exercise.audio_url, "UTF-8")

                            // Navigate to the detail screen
                            navController.navigate(
                                "breathing_detail/$encodedCategoryImage/$encodedBackgroundImage/$encodedTiming/$encodedAudioUrl"
                            )
                        },
                    shape = RoundedCornerShape(16.dp), // Rounded corners for the card
                    colors = CardDefaults.cardColors(containerColor = Color.White) // White background
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = exercise.timing,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = exercise.description,
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "${exercise.breaths_per_min} breaths/min",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    } else {
        Text(
            text = "No category found",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}

