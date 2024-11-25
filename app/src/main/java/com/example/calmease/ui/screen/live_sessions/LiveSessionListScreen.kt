package com.example.calmease.ui.screen.live_sessions


import androidx.compose.runtime.Composable

import android.annotation.SuppressLint
import android.net.Uri
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.calmease.R
import com.example.calmease.network.CreateMemoryRequest
import com.example.calmease.network.Session
import com.example.calmease.network.SessionRequest
import com.example.calmease.network.createMemory
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
@Composable
fun LiveSessionListScreen(navController: NavController, sessionViewModel: SessionViewModel = viewModel()) {
    val sessions by sessionViewModel.sessions.collectAsState()
    val isLoading by sessionViewModel.isLoading.collectAsState()
    val errorMessage by sessionViewModel.errorMessage.collectAsState()

    // Automatically fetch sessions when the screen is launched
    LaunchedEffect(Unit) {
        sessionViewModel.fetchSessions(1, 5, "Morning")
    }

    Column(modifier = Modifier.padding(16.dp)) {
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

    // Floating Action Button (FAB) for creating a new session
    FloatingActionButton(
        onClick = { navController.navigate("create_session") },
        modifier = Modifier
            .padding(16.dp),
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(Icons.Filled.Add, contentDescription = "Create Session")
    }
}


@Composable
fun LiveSessionItem(session: Session, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = session.title, style = MaterialTheme.typography.titleLarge)
            Text(text = session.description, style = MaterialTheme.typography.bodyMedium)

            Spacer(modifier = Modifier.height(8.dp))

            // Showing additional session details
            Text(text = "Session ID: ${session.session_id}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Session Date: ${session.session_date}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Session Time: ${session.session_time}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Duration: ${session.duration} minutes", style = MaterialTheme.typography.bodySmall)
            Text(text = "Expert: ${session.expert_email}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Status: ${session.status}", style = MaterialTheme.typography.bodySmall)

        }
    }
}