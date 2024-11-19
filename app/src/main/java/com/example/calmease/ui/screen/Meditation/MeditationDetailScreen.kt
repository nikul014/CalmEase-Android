package com.example.calmease.ui.screen.Meditation

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
import com.example.calmease.viewmodel.MeditationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun MeditationDetailScreen(meditationId: Int?, viewModel: MeditationViewModel = viewModel()) {

    val meditation = viewModel.meditations.value.find { it.meditation_id == meditationId }
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
            delay(30000) // Wait for 30 seconds before showing the emoji slider
            showEmojiSlider.value = true
        }

    }

    if (meditation != null) {
        Column(modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())) {

            // Title of the meditation
            Text(
                text = meditation.title,
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.W700,
                    fontSize = 26.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // Charging progress bar animation
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Slider(
                    value = sliderProgress.value,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    enabled = true,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFF7A9E3),
                        activeTrackColor = CalmPrimaryDark,
                        inactiveTrackColor = Color.Gray
                    )
                )
            }

            // Purple border animation (charging effect) around the video player
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .padding(8.dp)
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 8.dp,
                        color = Color(0xFF8692F7).copy(alpha = borderGlow.value),
                        shape = RoundedCornerShape(16.dp)
                    )
            ) {
                // Youtube Player for the meditation content
                Column {
                    YoutubePlayer(
                        youtubeVideoId = meditation.content_url,
                        lifecycleOwner = LocalLifecycleOwner.current
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Show Emoji Mood Slider after the border animation completes
            if (showEmojiSlider.value) {
                EmojiMoodSlider()
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Description of the meditation
            Text(
                text = meditation.description,
                style = TextStyle(
                    fontFamily = Fredoka,
                    fontWeight = FontWeight.W300,
                    fontSize = 16.sp
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )



            // Author, Rating, Category information in Cards
            InfoCard(
                label = "Author",
                value = meditation.user_name
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoCard(
                label = "Rating",
                value = meditation.rating.toString()
            )

            Spacer(modifier = Modifier.height(8.dp))

            InfoCard(
                label = "Category",
                value = meditation.category
            )


        }
    } else {
        // Show fallback content when meditation is not found
        Text("Content not available. Please try again later!", style = TextStyle(fontFamily = Fredoka, fontWeight = FontWeight.W500))
    }
}

// InfoCard Composable to display metadata (author, rating, category, etc.)
@Composable
fun InfoCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .border(2.dp, CalmPrimaryLight, RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = label,
                style = TextStyle(
                    fontFamily = Fredoka,
                    fontWeight = FontWeight.W500,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                style = TextStyle(
                    fontFamily = Fredoka,
                    fontWeight = FontWeight.W300,
                    fontSize = 16.sp
                )
            )
        }
    }
}

// Emoji Mood Slider for the user to select how they feel
@Composable
fun EmojiMoodSlider() {
    val moodOptions = listOf("😊", "😐", "😞")
    var selectedMood by remember { mutableStateOf(moodOptions[1]) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "How do you feel after listening?",
            style = TextStyle(fontSize = 18.sp, fontFamily = Poppins, fontWeight = FontWeight.W500)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
            moodOptions.forEach { emoji ->
                IconButton(
                    onClick = { selectedMood = emoji },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Gray.copy(alpha = 0.2f), shape = RoundedCornerShape(50))
                        .padding(8.dp)
                ) {
                    Text(text = emoji, style = TextStyle(fontSize = 24.sp))
                }
            }
        }

        Text(
            text = "You selected: $selectedMood",
            style = TextStyle(fontSize = 16.sp, fontFamily = Fredoka, fontWeight = FontWeight.W300)
        )
    }
}
