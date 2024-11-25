package com.example.calmease.ui.screen.memories


import androidx.compose.runtime.Composable

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.app.DatePickerDialog
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import com.example.calmease.R
import com.example.calmease.network.CreateMemoryRequest
import com.example.calmease.network.createMemory
import com.example.calmease.ui.components.CustomTextField
import com.example.calmease.viewmodel.GoodMemoriesViewModel
import com.example.calmease.viewmodel.GoodMemory
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale


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

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp) // Add bottom padding to ensure no overlap
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Create a Memory",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Add a new moment to remember.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 8.dp,
                        horizontal = 16.dp
                    ) // Added horizontal padding for better spacing
                ,
                shape = RoundedCornerShape(16.dp), // Rounded corners for the card
                colors = CardDefaults.cardColors(containerColor = Color.White) // White background
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 30.dp, vertical = 36.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {


                        CustomTextField(
                            value = title,
                            onValueChange = { title = it },
                            label = "Memory Title",
                            keyboardType = KeyboardType.Text,
                            leadingIconId = Icons.Rounded.Title
                        )


                        Spacer(modifier = Modifier.height(16.dp))

                        CustomTextField(
                            value = description,
                            onValueChange = { description = it },
                            label = "Memory Description",
                            keyboardType = KeyboardType.Text,
                            leadingIconId = Icons.Rounded.Description
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        // Image Upload
                        Button(onClick = { /* Add image picker logic */ }) {
                            Text("Upload Image", color = Color.White)
                        }

                        Spacer(modifier = Modifier.height(16.dp))


                        Button(
                            onClick = {
                                isSubmitting = true

                                val calendar = Calendar.getInstance()
                                val formatter =
                                    SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

                                val memoryRequest = CreateMemoryRequest(
                                    title = title,
                                    description = description,
                                    memory_date_time = formatter.format(calendar.time),
                                    image_url = "https://thumbs.dreamstime.com/b/memory-loss-man-25730340.jpg",
                                    user_id = 1
                                )

                                CoroutineScope(Dispatchers.IO).launch {
                                    val result = createMemory(memoryRequest)
                                    withContext(Dispatchers.Main) {
                                        isSubmitting = false
                                        if (result.isSuccess) {
                                            // Navigate back or show success message
                                            navController.popBackStack()
                                        } else {
                                            errorMessage =
                                                result.errorMessage ?: "An error occurred"
                                        }
                                    }
                                }
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            enabled = !isSubmitting
                        ) {
                            Text(
                                text = if (isSubmitting) "Submitting..." else "Submit",
                                color = Color.White
                            )
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
        }
    }
}