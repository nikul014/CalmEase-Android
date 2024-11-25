package com.example.calmease.ui.screen.live_sessions


import android.annotation.SuppressLint
import android.content.Intent
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
import com.google.gson.Gson
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
        // Session details in cards
        SessionDetailCard(session = session)

        Spacer(modifier = Modifier.height(16.dp))

        // Buttons to Edit or Join Session
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


                    val userId = CalmEaseApplication.sharedPreferenceHelper.getUser()?.id;

                    val intent = Intent(context, JoinSessionActivity::class.java).apply {
                        putExtra("SESSION_ID", session.agora_channel_id?.toString())
                        putExtra("TOKEN", "007eJxTYFivIW3ep8y5pT8saDLjH60J4eUTH9X++VNRWDHhkeG273cUGFItTcyTTS2TklItkk2MLMwtTU2SkwxNTVLNjJNSLQzNWXud0wX4GBhUVfRZGBkYGVgYGBlEZzunM4FJZjDJAiYZGYxYGQyNjE1MAaNpIco=")
                        putExtra("USER_ID", userId ?:12345)
                        putExtra("ROLE", if (session.expert_email == "test@gmail.com") "subscriber" else "publisher")
                    }
                    context.startActivity(intent)
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




