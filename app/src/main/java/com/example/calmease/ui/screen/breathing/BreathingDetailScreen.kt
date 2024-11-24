package com.example.calmease.ui.screen.breathing

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage

@Composable
fun BreathingDetailScreen(
    categoryImage: String,
    backgroundImage: String,
    exerciseTiming: String,
    audioUrl: String,
    navController: NavController
) {
    // State to manage the audio player and play status
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    // Parse the timing (Inhale | Hold In | Exhale | Hold Out)
    val timings = exerciseTiming.split("|").map { it.trim() }
    val inhaleTime = timings.getOrNull(0) ?: "0"
    val holdInTime = timings.getOrNull(1) ?: "0"
    val exhaleTime = timings.getOrNull(2) ?: "0"
    val holdOutTime = timings.getOrNull(3) ?: "0"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Display the background image
        AsyncImage(
            model = backgroundImage,
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Display the category image at the top
            AsyncImage(
                model = categoryImage,
                contentDescription = "Exercise Image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp)
            )

            // Timing breakdown display at the bottom
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TimingCard(label = "Inhale", time = inhaleTime)
                TimingCard(label = "Hold In", time = holdInTime)
                TimingCard(label = "Exhale", time = exhaleTime)
                TimingCard(label = "Hold Out", time = holdOutTime)
            }

            // Start/Stop Button at the bottom
            Button(
                onClick = {
                    if (isPlaying) {
                        mediaPlayer?.stop()
                        mediaPlayer?.release()
                        mediaPlayer = null
                        isPlaying = false
                    } else {
                        mediaPlayer = MediaPlayer().apply {
                            setDataSource(audioUrl)
                            isLooping = true // Loop the audio
                            prepare()
                            start()
                        }
                        isPlaying = true
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = if (isPlaying) "Stop" else "Start Exercise")
            }
        }
    }

    // Clean up the MediaPlayer when the composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}

// Helper Composable for Timing Display
@Composable
fun TimingCard(label: String, time: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .size(80.dp),
        colors = CardDefaults.cardColors(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Text(text = "$time s", style = MaterialTheme.typography.titleMedium)
        }
    }
}
