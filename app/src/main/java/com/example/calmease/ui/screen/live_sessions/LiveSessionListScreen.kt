package com.example.calmease.ui.screen.live_sessions


import androidx.compose.runtime.Composable

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.calmease.viewmodel.MeditationViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.calmease.viewmodel.Meditation
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.calmease.R
import com.example.calmease.network.CreateMemoryRequest
import com.example.calmease.network.Session
import com.example.calmease.network.SessionRequest
import com.example.calmease.network.createMemory
import com.example.calmease.ui.screen.memories.GoodMemoryItem
import com.example.calmease.viewmodel.GoodMemoriesViewModel
import com.example.calmease.viewmodel.GoodMemory
import com.example.calmease.viewmodel.SessionViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.Locale

@Composable
fun LiveSessionListScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel = viewModel()
) {
    val sessions by sessionViewModel.sessions.collectAsState()
    val isLoading by sessionViewModel.isLoading.collectAsState()
    val errorMessage by sessionViewModel.errorMessage.collectAsState()

    // Automatically fetch sessions when the screen is launched
    LaunchedEffect(Unit) {
        sessionViewModel.fetchSessions(1, 5, "Morning")
    }

    // Layout for the screen
    Box(
        contentAlignment = Alignment.BottomEnd
    ) {

        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (errorMessage != null) {
                Text("Error: $errorMessage", color = Color.Red)
            } else {
                LazyColumn {
                    items(sessions) { session ->
                        LiveSessionItem(session = session, onClick = {
                            val sessionJson = Gson().toJson(session)
                            navController.navigate("session_detail/$sessionJson")
                        })
                    }
                }
            }
        }


        ExtendedFloatingActionButton(
            onClick = { navController.navigate("create_session") },
            modifier = Modifier
                .padding(24.dp),
            containerColor = MaterialTheme.colorScheme.primary
        ) {
            Icon(
                imageVector = Icons.Outlined.Add, // Smiley face icon
                contentDescription = "Create Memory",
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = "Create Session",

                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                ),

                )

        }
    }

}


@SuppressLint("NewApi")

fun formatSessionDateTime(sessionDate: String, sessionTime: String): String {

    val outputFormatter = DateTimeFormatter.ofPattern("h a - dd MMMM", Locale.ENGLISH)

    // Parse the date string into ZonedDateTime
    val parsedDateTime = ZonedDateTime.parse(sessionDate).toLocalDate().atTime(LocalTime.parse(sessionTime))
    return parsedDateTime.format(outputFormatter)
}

@Composable
fun LiveSessionItem(session: Session, onClick: () -> Unit) {
    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ) // Added horizontal padding for better spacing
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp), // Rounded corners for the card
        colors = CardDefaults.cardColors(containerColor = Color.White) // White background

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = session.title, style = MaterialTheme.typography.titleMedium)
            Text(text = session.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = formatSessionDateTime(session.session_date,session.session_time),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Session duration: ${session.duration} minutes",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Given by: ${session.expert_email}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}