package com.example.calmease.ui.screen.breathing

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
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
                    .padding(vertical = 8.dp)
                    .clickable {
                        navController.navigate("breathing_exercises/${category.id}")
                    }
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    AsyncImage(
                        model = category.category_image,
                        contentDescription = category.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = category.title)
                }
            }
        }
    }
}
