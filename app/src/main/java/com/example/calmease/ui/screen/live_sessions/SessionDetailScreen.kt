package com.example.calmease.ui.screen.live_sessions


import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calmease.CalmEaseApplication
import com.example.calmease.JoinSessionActivity
import com.example.calmease.network.Session
import com.example.calmease.network.User
import com.example.calmease.network.fetchToken
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun SessionDetailScreen(navController: NavController, sessionJson: String) {
    val session = Gson().fromJson(sessionJson, Session::class.java)
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val userId = CalmEaseApplication.sharedPreferenceHelper.getUser()?.id
    val userEmail = CalmEaseApplication.sharedPreferenceHelper.getUser()?.email
    val userType = CalmEaseApplication.sharedPreferenceHelper.getUserType()

    val isExpert = userId?.toString() == session.expert_id

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        SessionDetailCard(session = session)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (userType == "expert") {
                Button(
                    onClick = {
                        navController.navigate("edit_session/${sessionJson}")
                    },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(50),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    Text(
                        "Edit Session",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }

            Button(
                onClick = {
                    val userId = CalmEaseApplication.sharedPreferenceHelper.getUser()?.id ?: 12345
                    val userEmail = CalmEaseApplication.sharedPreferenceHelper.getUser()?.email ?: "default@example.com"
                    val role = if (session.expert_email == "test@gmail.com") "subscriber" else "publisher"

                    CoroutineScope(Dispatchers.IO).launch {
                        try {
                            val tokenResponse = fetchToken(
                                sessionId = session.agora_channel_id?.toString() ?: "defaultChannel",
                                userId = userId,
                                userEmail = userEmail,
                                role = role
                            )

                            withContext(Dispatchers.Main) {
                                val intent = Intent(context, JoinSessionActivity::class.java).apply {
                                    putExtra("SESSION_ID", tokenResponse.session_id.toString())
                                    putExtra("TOKEN", tokenResponse.token)
                                    putExtra("USER_ID", tokenResponse.user_id)
                                    putExtra("ROLE", role)
                                }
                                context.startActivity(intent)
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Log.e("Token Fetch Error", "Error: ${e.message}")
                            }
                        }
                    }
                },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(50),
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                Text("Join Session", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
            }

        }
    }
}

@Composable
fun SessionDetailCard(session: Session) {
    Column(
            modifier = Modifier
            .fillMaxWidth()
        .background(Color.White, shape = MaterialTheme.shapes.medium)
        .padding(16.dp)

    ) {

        Text(
            text = session.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(4.dp),
            color = Color.Black
        )

        Text(
            text = session.description,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(4.dp),
            color = Color.Black
        )


        Text(
            text = formatSessionDateTime(session.session_date,session.session_time),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(4.dp),
            color = Color.Black
        )

        Text(
            text = "${session.duration} mins",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(4.dp),
            color = Color.Black
        )
        Text(
            text = "Given by: ${session.expert_email}",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(4.dp),
            color = Color.Black
        )

    }

}




