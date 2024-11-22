package com.example.calmease.ui.screen.article

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.calmease.ui.screen.Meditation.highlightText
import com.example.calmease.viewmodel.Article

@Composable
fun ArticleItem(article: Article, searchQuery: String = "", navController: NavController) {
    val highlightTitle =
        searchQuery.isNotEmpty() && article.title.contains(searchQuery, ignoreCase = true)
    val highlightDescription =
        searchQuery.isNotEmpty() && article.description.contains(searchQuery, ignoreCase = true)


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        // Handle click event
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = article.image_url, // The image URL
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp), // Fixed height for image
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Title with or without search highlight
            if (highlightTitle) {
                Text(
                    text = highlightText(article.title, searchQuery),  // Highlighted title
                    style = MaterialTheme.typography.titleMedium
                )
            } else {
                Text(
                    text = article.title,  // Normal title
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description with or without search highlight
            if (highlightDescription) {
                Text(
                    text = highlightText(
                        article.description,
                        searchQuery
                    ),  // Highlighted description
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                Text(
                    text = article.description,  // Normal description
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "by " + article.author,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }

}