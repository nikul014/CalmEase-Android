package com.example.calmease.ui.screen.live_sessions


import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.calmease.JoinSessionActivity
import com.example.calmease.R
import com.example.calmease.network.Session
import com.example.calmease.viewmodel.GoodMemory
import com.google.gson.Gson


@Composable
fun SessionDetailScreen(navController: NavController, sessionJson: String) {
    val session = Gson().fromJson(sessionJson, Session::class.java)
    val context = LocalContext.current

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Session Details",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Session Details Section
        SessionDetailRow("Title", session.title)
        SessionDetailRow("Description", session.description)
        SessionDetailRow("Date", session.session_date)
        SessionDetailRow("Time", session.session_time)
        SessionDetailRow("Duration", "${session.duration} mins")
        SessionDetailRow("Expert", session.expert_email)
        session.status?.let { SessionDetailRow("Status", it) }

        Spacer(modifier = Modifier.height(24.dp))

        // Buttons to Edit or Join Session
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Edit Button
            OutlinedButton(
                onClick = {
                    // Navigate to the session edit screen, pass session data
                    navController.navigate("edit_session/${sessionJson}")
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Edit Session", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            // Join Button
            Button(
                onClick = {
                    // Navigate to live session or start the session
//                    navController.navigate("join_session/${session.session_id}")
                    val intent = Intent(context, JoinSessionActivity::class.java).apply {
                        putExtra("SESSION_ID", session.agora_channel_id?.toString())
                        putExtra(
                            "TOKEN",
                            "007eJxTYFivIW3ep8y5pT8saDLjH60J4eUTH9X++VNRWDHhkeG273cUGFItTcyTTS2TklItkk2MLMwtTU2SkwxNTVLNjJNSLQzNWXud0wX4GBhUVfRZGBkYGVgYGBlEZzunM4FJZjDJAiYZGYxYGQyNjE1MAaNpIco="
                        )
                        putExtra("USER_ID", session.expert_id)
                        putExtra(
                            "ROLE", if (session.expert_email == "test@gmail.com") {
                                "subscriber"
                            } else {
                                "publisher"
                            }
                        )
                    }
                    context.startActivity(intent)

                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Join Session", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun SessionDetailRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(12.dp))
    }
}
