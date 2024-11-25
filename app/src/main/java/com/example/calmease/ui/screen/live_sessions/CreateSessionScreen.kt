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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.input.KeyboardType
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
fun CreateSessionScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel = viewModel(),
    existingSessionJson: String? = null // This is passed when editing an existing session
) {
    val isEditing = existingSessionJson != null
    val session = if (isEditing) {
        // Parse the existing session if editing
        Gson().fromJson(existingSessionJson, Session::class.java)
    } else {
        // Default empty session for creation
        Session(
            title = "",
            description = "",
            session_date = "",
            session_time = "",
            duration = 0,
            expert_id = "expert-123",
            expert_email = "nikul@example.com"
        )
    }

    var title by remember { mutableStateOf(session.title) }
    var description by remember { mutableStateOf(session.description) }
    var sessionDate by remember { mutableStateOf(session.session_date) }
    var sessionTime by remember { mutableStateOf(session.session_time) }
    var duration by remember { mutableStateOf(session.duration.toString()) }
    val isLoading by sessionViewModel.isLoading.collectAsState()
    val errorMessage by sessionViewModel.errorMessage.collectAsState()

    fun handleSaveSession() {
        val sessionRequest = SessionRequest(
            title = title,
            description = description,
            session_date = sessionDate,
            session_time = sessionTime,
            expert_id = "expert-123",
            expert_email = "nikul@example.com",
            duration = duration.toIntOrNull() ?: 15
        )

        val newSession = session.copy(
            title = title,
            description = description,
            session_date = sessionDate,
            session_time = sessionTime,
            duration = duration.toInt(),
            expert_id = "expert-123",
            expert_email = "nikul@example.com",
        )

        if (isEditing) {
            // Update the session
            sessionViewModel.updateSession(newSession)
        } else {
            // Create a new session
            sessionViewModel.createSession(sessionRequest)
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = if (isEditing) "Edit Session" else "Create Session",
            style = MaterialTheme.typography.headlineMedium
        )

        // Input fields for the session details
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sessionDate,
            onValueChange = { sessionDate = it },
            label = { Text("Session Date") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = sessionTime,
            onValueChange = { sessionTime = it },
            label = { Text("Session Time") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            label = { Text("Duration (minutes)") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Button for save/create or update
        Button(
            onClick = { handleSaveSession() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text(text = if (isEditing) "Update Session" else "Create Session")
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        if (errorMessage != null) {
            Text("Error: $errorMessage", color = Color.Red)
        }
    }
}
