package com.example.calmease.ui.screen.meditation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.calmease.ui.theme.CalmPrimaryDark
import com.example.calmease.ui.theme.CalmPrimaryLight
import com.example.calmease.ui.theme.Fredoka
import com.example.calmease.ui.theme.Poppins
import com.example.calmease.viewmodel.Meditation
import com.example.calmease.viewmodel.MeditationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MeditationDetailScreen(meditation: Meditation?, viewModel: MeditationViewModel = viewModel()) {

    val sliderProgress = remember { Animatable(0f) }
    val borderGlow = remember { Animatable(0f) }
    val showEmojiSlider = remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        launch {
            borderGlow.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 30000,
                    easing = LinearEasing
                )
            )
        }

        // Slider progress animation (charging slider)
        launch {
            sliderProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 30000, // 30 seconds
                    easing = LinearEasing
                )
            )
        }

        // Show emoji slider after border animation is completed
        launch {
            delay(6000) // Wait for 30 seconds before showing the emoji slider
            showEmojiSlider.value = true
        }
    }

    if (meditation != null) {
        Column(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 24.dp)
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Title of the meditation
            Text(
                text = meditation.title,
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

//            // Charging progress bar animation
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Slider(
//                    value = sliderProgress.value,
//                    onValueChange = {},
//                    modifier = Modifier.fillMaxWidth(),
//                    enabled = true,
//                    colors = SliderDefaults.colors(
//                        thumbColor = Color(0xFFF7A9E3),
//                        activeTrackColor = CalmPrimaryDark,
//                        inactiveTrackColor = Color.Gray
//                    )
//                )
//            }
            Spacer(modifier = Modifier.height(8.dp))

            // Purple border animation (charging effect) around the video player
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                // YouTube Player for the meditation content
                Column {
                    YoutubePlayer(
                        youtubeVideoId = meditation.content_url,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Description of the meditation
            Text(
                text = meditation.description,

                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            InformationCard(
                author = meditation.user_name,
                rating = meditation.rating.toString(),
                category = meditation.category
            )


            // Show Emoji Mood Slider after the border animation completes
            if (showEmojiSlider.value) {
                Spacer(modifier = Modifier.height(12.dp))

                EmojiMoodSlider()
            }


        }
    } else {
        // Show fallback content when meditation is not found
        Text(
            "Content not available. Please try again later!",
            style = TextStyle(fontFamily = Fredoka, fontWeight = FontWeight.W500)
        )
    }
}

@Composable
fun InformationCard(
    author: String,
    rating: String,
    category: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        // Title of the Information section
        Text(
            text = "Information",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Author, rating, and category info
        InfoCard(label = "Author", value = author)
        InfoCard(label = "Rating", value = rating)
        InfoCard(label = "Category", value = category)
    }
}

@Composable
fun InfoCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun EmojiMoodSlider() {
    val moodOptions = listOf("😊", "😐", "😞")
    var selectedMood by remember { mutableStateOf(moodOptions[1]) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "How do you feel after listening?",
            style = MaterialTheme.typography.titleMedium,

            modifier = Modifier.padding(16.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp), // Adds spacing between the emojis
            modifier = Modifier
                .padding(horizontal = 16.dp) // Adds padding on the sides
                .align(Alignment.CenterHorizontally) // Centers the row within its parent
        ) {
            moodOptions.forEach { emoji ->
                IconButton(
                    onClick = { selectedMood = emoji },
                    modifier = Modifier
                        .size(56.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(50))
                        .padding(8.dp) // Padding within the button
                        .align(Alignment.CenterVertically) // Vertically center the items
                ) {
                    Text(text = emoji, style = TextStyle(fontSize = 30.sp))
                }
            }
        }


        Text(
            text = "You selected: $selectedMood",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
    }
}
