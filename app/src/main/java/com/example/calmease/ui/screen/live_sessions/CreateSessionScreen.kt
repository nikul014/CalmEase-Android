@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.calmease.ui.screen.live_sessions


import android.app.TimePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.calmease.CalmEaseApplication
import com.example.calmease.network.Session
import com.example.calmease.network.SessionRequest
import com.example.calmease.ui.components.CustomTextField
import com.example.calmease.ui.theme.CalmBackground
import com.example.calmease.viewmodel.SessionViewModel
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


@Composable
fun CreateSessionScreen(
    navController: NavController,
    sessionViewModel: SessionViewModel = viewModel(),
    existingSessionJson: String? = null
) {

    val userId = CalmEaseApplication.sharedPreferenceHelper.getUser()?.id
    val userEmail = CalmEaseApplication.sharedPreferenceHelper.getUser()?.email
    val userType = CalmEaseApplication.sharedPreferenceHelper.getUserType()

    val isEditing = existingSessionJson != null
    val session = if (isEditing) {
        Gson().fromJson(existingSessionJson, Session::class.java)
    } else {
        Session(
            title = "",
            description = "",
            session_date = "",
            session_time = "",
            duration = 0,
            expert_id = userId?.toString() ?: "",
            expert_email = userEmail ?: ""
        )
    }

    val isExpert = userId?.toString() == session.expert_id

    var title by remember { mutableStateOf(session.title) }
    var description by remember { mutableStateOf(session.description) }
    var sessionDate by remember { mutableStateOf(session.session_date) }
    var sessionTime by remember { mutableStateOf(session.session_time) }
    var duration by remember { mutableStateOf(session.duration.toString()) }
    val isLoading by sessionViewModel.isLoading.collectAsState()
    val errorMessage by sessionViewModel.errorMessage.collectAsState()

    // Register the success callbacks
    sessionViewModel.setOnSessionCreatedCallback {
        navController.navigate("session_list")
    }

    sessionViewModel.setOnSessionUpdatedCallback {
        navController.navigate("session_list}")
    }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = if (isEditing) "Edit Session" else "Create Session",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Add session details below.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    CustomTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = "Title",
                        keyboardType = KeyboardType.Text,
                        leadingIconId = Icons.Rounded.Title
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = "Description",
                        keyboardType = KeyboardType.Text,
                        leadingIconId = Icons.Rounded.Description
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Box(
                        Modifier
                            .padding(0.dp)
                            .clickable {
                                android.app
                                    .DatePickerDialog(
                                        context,
                                        { _, year, month, dayOfMonth ->
                                            sessionDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
                                        },
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)
                                    )
                                    .show()
                            }
                    ) {

                        Column(modifier = Modifier
                            .padding(0.dp)
                            .clickable {
                                android.app
                                    .DatePickerDialog(
                                        context,
                                        { _, year, month, dayOfMonth ->
                                            sessionDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
                                        },
                                        calendar.get(Calendar.YEAR),
                                        calendar.get(Calendar.MONTH),
                                        calendar.get(Calendar.DAY_OF_MONTH)
                                    )
                                    .show()
                            }) {

                            Text(
                                text = "Session Date",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(CalmBackground, shape = RoundedCornerShape(8.dp))
                                    .clickable {
                                        android.app
                                            .DatePickerDialog(
                                                context,
                                                { _, year, month, dayOfMonth ->
                                                    sessionDate = String.format("%d-%02d-%02d", year, month + 1, dayOfMonth)
                                                },
                                                calendar.get(Calendar.YEAR),
                                                calendar.get(Calendar.MONTH),
                                                calendar.get(Calendar.DAY_OF_MONTH)
                                            )
                                            .show()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )


                                    Text(
                                        text = sessionDate,
                                        style = TextStyle(
                                            color = if (sessionDate.isEmpty()) Color.Gray else Color.Black,
                                            fontSize = 14.sp
                                        )
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))



                    Box(
                        Modifier
                            .padding(0.dp)
                            .clickable {
                                TimePickerDialog(
                                    context,
                                    { _, hourOfDay, minute ->
                                        sessionTime = String.format("%02d:%02d:%02d", hourOfDay, minute, 0)

                                    },
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE),
                                    true
                                ).show()
                            }
                    ) {

                        Column(modifier = Modifier
                            .padding(0.dp)
                            .clickable {
                                TimePickerDialog(
                                    context,
                                    { _, hourOfDay, minute ->
                                        sessionTime = String.format("%02d:%02d:%02d", hourOfDay, minute, 0)

                                    },
                                    calendar.get(Calendar.HOUR_OF_DAY),
                                    calendar.get(Calendar.MINUTE),
                                    true
                                ).show()
                            }) {

                            Text(
                                text = "Session Time",
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )


                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(CalmBackground, shape = RoundedCornerShape(8.dp))
                                    .clickable {
                                        TimePickerDialog(
                                            context,
                                            { _, hourOfDay, minute ->
                                                sessionTime = String.format("%02d:%02d:%02d", hourOfDay, minute, 0)

                                            },
                                            calendar.get(Calendar.HOUR_OF_DAY),
                                            calendar.get(Calendar.MINUTE),
                                            true
                                        ).show()
                                    }
                                    .padding(horizontal = 16.dp, vertical = 12.dp),
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.DateRange,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )


                                    Text(
                                        text = sessionTime,
                                        style = TextStyle(
                                            color = if (sessionTime.isEmpty()) Color.Gray else Color.Black,
                                            fontSize = 14.sp
                                        )
                                    )
                                }
                            }
                        }
                    }



                    Spacer(modifier = Modifier.height(16.dp))

                    CustomTextField(
                        value = duration,
                        onValueChange = { duration = it },
                        label = "Duration (minutes)",
                        keyboardType = KeyboardType.Number,
                        leadingIconId = Icons.Filled.Lock
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Submit Button
                    Button(
                        onClick = {


                            val sessionRequest = SessionRequest(
                                title = title,
                                description = description,
                                session_date = sessionDate,
                                session_time = sessionTime,
                                expert_id = userId?.toString()?:"",
                                expert_email = userEmail?.toString()?:"",
                                duration = duration.toIntOrNull() ?: 15
                            )

                            val newSession = session.copy(
                                title = title,
                                description = description,
                                session_date = sessionDate,
                                session_time = sessionTime,
                                duration = duration.toInt(),
                                expert_id = userId?.toString()?:"",
                                expert_email = userEmail?.toString()?:"",
                            )

                            if (isEditing) {
                                sessionViewModel.updateSession(newSession)
                            } else {
                                sessionViewModel.createSession(sessionRequest)
                            }
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        enabled = !isLoading
                    ) {
                        Text(
                            text = if (isLoading) "Submitting..." else if (isEditing) "Update Session" else "Create Session",
                            color = Color.White
                        )
                    }


                    if (errorMessage != null) {
                        Text(
                            text = errorMessage ?: "",
                            color = Color.Red,
                            modifier = Modifier.padding(top = 16.dp)
                        )
                    }
                }
            }
        }

        // Loading Indicator
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
