package com.example.calmease.ui.screen.breathing

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    val timings = exerciseTiming.split("|").map { it.trim() }
    val inhaleTime = timings.getOrNull(0) ?: "0"
    val holdInTime = timings.getOrNull(1) ?: "0"
    val exhaleTime = timings.getOrNull(2) ?: "0"
    val holdOutTime = timings.getOrNull(3) ?: "0"

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
            AsyncImage(
                model = categoryImage,
                contentDescription = "Exercise Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) .clip(RoundedCornerShape(16.dp)),
            )

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TimingCard(label = "Inhale", time = inhaleTime)
                TimingCard(label = "Hold In", time = holdInTime)
                TimingCard(label = "Exhale", time = exhaleTime)
                TimingCard(label = "Hold Out", time = holdOutTime)
            }

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
                            isLooping = true
                            prepare()
                            start()
                        }
                        isPlaying = true
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = if (isPlaying) "Stop" else "Start Exercise", color = Color.White)
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
}

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
            modifier = Modifier.fillMaxSize().background(color = Color.White)
        ) {
            Text(text = label, style = MaterialTheme.typography.bodyMedium)
            Text(text = "$time s", style = MaterialTheme.typography.titleMedium)
        }
    }
}
