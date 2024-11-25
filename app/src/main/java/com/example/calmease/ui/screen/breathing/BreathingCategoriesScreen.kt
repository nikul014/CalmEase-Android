package com.example.calmease.ui.screen.breathing

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.calmease.ui.screen.meditation.highlightText
import com.example.calmease.viewmodel.BreathingViewModel

@Composable
fun BreathingCategoriesScreen(
    viewModel: BreathingViewModel,
    navController: NavController
) {
    val categories = viewModel.categories.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(categories) { category ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp) // Added horizontal padding for better spacing
                    .clickable {
                        navController.navigate("breathing_exercises/${category.id}")
                    },
                shape = RoundedCornerShape(16.dp), // Rounded corners for the card
                colors = CardDefaults.cardColors(containerColor = Color.White) // White background
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    AsyncImage(
                        model = category.category_image,
                        contentDescription = category.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp) .clip(RoundedCornerShape(16.dp)), // Apply rounded corners
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Title with search highlight if necessary
                    Text(
                        text = category.title,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.Black
                    )
                }
            }
        }
    }
}
