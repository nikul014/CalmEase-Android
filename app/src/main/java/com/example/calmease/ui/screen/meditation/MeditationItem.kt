package com.example.calmease.ui.screen.meditation

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.calmease.viewmodel.Meditation
import com.google.gson.Gson
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@Composable
fun MeditationItem(meditation: Meditation, searchQuery: String = "", navController: NavController) {
    // Determine if title or description contains the search query
    val highlightTitle = searchQuery.isNotEmpty() && meditation.title.contains(searchQuery, ignoreCase = true)
    val highlightDescription = searchQuery.isNotEmpty() && meditation.description.contains(searchQuery, ignoreCase = true)

    // Card with a white background and shadow
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp) // Added horizontal padding for better spacing
            .clickable {


                val meditationJson = Gson().toJson(meditation)  // Serialize the article to a JSON string
                val encodedMeditationJson = URLEncoder.encode(meditationJson, StandardCharsets.UTF_8.toString()) // URL-encode the JSON string
                navController.navigate("meditation_detail/$encodedMeditationJson")

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
                model = meditation.image_url,
                contentDescription = meditation.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)        .clip(RoundedCornerShape(16.dp)), // Apply rounded corners

                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp)) // Add space between image and text

            // Title with search highlight if necessary
            Text(
                text = if (highlightTitle) highlightText(meditation.title, searchQuery).text else meditation.title,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.fillMaxWidth(),
                color = if (highlightTitle) MaterialTheme.colorScheme.primary else Color.Black
            )

            Spacer(modifier = Modifier.height(4.dp)) // Space between title and description

            // Description with search highlight if necessary
            Text(
                if (highlightDescription) highlightText(meditation.description, searchQuery).text else meditation.description,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp)) // Space between description and duration

            // Duration with a more visible style
            Text(
                text = meditation.duration,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
