package com.example.calmease.ui.screen.memories


import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Description
import androidx.compose.material.icons.rounded.Title
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.calmease.network.CreateMemoryRequest
import com.example.calmease.network.createMemory
import com.example.calmease.ui.components.CustomTextField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("NewApi")
@Composable
fun CreateMemoryScreen(navController: NavController) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

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
                .padding(24.dp)
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
                    )
                ,
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
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

                        Button(onClick = { }) {
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