package com.example.calmease.ui.screen.memories


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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.calmease.R
import com.example.calmease.network.CreateMemoryRequest
import com.example.calmease.network.createMemory
import com.example.calmease.viewmodel.GoodMemoriesViewModel
import com.example.calmease.viewmodel.GoodMemory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun CreateMemoryScreen(navController: NavController) {
    // State variables for each input field
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var selectedTime by remember { mutableStateOf(LocalTime.now()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    // State for showing loading and success/error messages
    var isSubmitting by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    // Date and Time format
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    Scaffold(
        topBar = {
             Text(text = "Create Memory")
        },
        bottomBar = {
            BottomAppBar {
                Button(onClick = {
                    // Navigate back to the previous screen
                    navController.popBackStack()
                }) {
                    Text("Cancel")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Title Input
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Memory Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Date Picker
            Text(text = "Date")
            DatePicker(
                selectedDate = selectedDate,
                onDateSelected = { selectedDate = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Time Picker
            Text(text = "Time")
            TimePicker(
                selectedTime = selectedTime,
                onTimeSelected = { selectedTime = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Description Input
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Memory Description") },
                modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Image Upload
            Button(onClick = { /* Add image picker logic */ }) {
                Text("Upload Image")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    isSubmitting = true
                    val memoryRequest = CreateMemoryRequest(
                        title = "Morning Mindfulness Meditation",
                        description =  "A calming mindfulness meditation to start your day.",
                        memory_date_time =  "20 minutes",
                        image_url = "http://example.com/audio/morning_mindfulness.mp3",
                        user_id = 1
                    )
                    /*
                    {
"title": "Morning Mindfulness Meditation",
  "category": "Mindfulness",
  "description": "A calming mindfulness meditation to start your day.",
  "duration": "20 minutes",
  "media_type": "audio",
  "content_url": "http://example.com/audio/morning_mindfulness.mp3",
  "user_id": 1,
  "user_name": "John Doe",
  "user_email": "johndoe@example.com"
}

                     */

                    CoroutineScope(Dispatchers.IO).launch {
                        val result = createMemory(memoryRequest)
                        withContext(Dispatchers.Main) {
                            isSubmitting = false
                            if (result.isSuccess) {
                                // Navigate back or show success message
                                navController.popBackStack()
                            } else {
                                errorMessage = result.errorMessage ?: "An error occurred"
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isSubmitting
            ) {
                Text(text = if (isSubmitting) "Submitting..." else "Submit")
            }


            // Error Message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}


@SuppressLint("NewApi")
@Composable
fun DatePicker(selectedDate: LocalDate, onDateSelected: (LocalDate) -> Unit) {
    val datePickerDialog = remember { mutableStateOf(false) }

    Text(
        text = selectedDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        modifier = Modifier.clickable { datePickerDialog.value = true }
    )

    if (datePickerDialog.value) {
        DatePickerDialog(onDateSelected = { date ->
            datePickerDialog.value = false
            onDateSelected(date)
        })
    }
}

@SuppressLint("NewApi")
@Composable
fun DatePickerDialog(onDateSelected: (LocalDate) -> Unit) {
    val date = remember { mutableStateOf(LocalDate.now()) }
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Select Date") },
        text = {
            Column {
                // Implement your custom date picker dialog here
            }
        },
        confirmButton = {
            Button(onClick = {
                onDateSelected(date.value)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = { }) {
                Text("Cancel")
            }
        }
    )
}


@SuppressLint("NewApi")
@Composable
fun TimePicker(selectedTime: LocalTime, onTimeSelected: (LocalTime) -> Unit) {
    val timePickerDialog = remember { mutableStateOf(false) }

    Text(
        text = selectedTime.format(DateTimeFormatter.ofPattern("HH:mm")),
        modifier = Modifier.clickable { timePickerDialog.value = true }
    )

    if (timePickerDialog.value) {
        TimePickerDialog(onTimeSelected = { time ->
            timePickerDialog.value = false
            onTimeSelected(time)
        })
    }
}

@SuppressLint("NewApi")
@Composable
fun TimePickerDialog(onTimeSelected: (LocalTime) -> Unit) {
    val time = remember { mutableStateOf(LocalTime.now()) }
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Select Time") },
        text = {
            Column {
                // Implement your custom time picker dialog here
            }
        },
        confirmButton = {
            Button(onClick = {
                onTimeSelected(time.value)
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            Button(onClick = { }) {
                Text("Cancel")
            }
        }
    )
}
