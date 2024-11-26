package com.example.calmease.ui.screen.profile


import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.calmease.R
import com.example.calmease.network.User
import com.example.calmease.ui.theme.CalmBackground
import com.example.calmease.ui.theme.Typography
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ProfileScreen(user: User) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CalmBackground)
            .padding(16.dp)
    ) {

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 24.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier
                            .size(120.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                ProfileDetailRow( value = user.email)
                ProfileDetailRow( value = formatDateString(user.createdAt))
                ProfileDetailRow(value = formatDateUpdateString(user.updatedAt))
            }
        }
    }
}

@SuppressLint("NewApi")
fun formatDateString(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
    val parsedDate = ZonedDateTime.parse(dateString, inputFormatter)
    return "Joined at ${parsedDate.format(outputFormatter)}"
}

@SuppressLint("NewApi")
fun formatDateUpdateString(dateString: String): String {
    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.getDefault())
    val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.getDefault())
    val parsedDate = ZonedDateTime.parse(dateString, inputFormatter)
    return "Updated at ${parsedDate.format(outputFormatter)}"
}


@Composable
fun ProfileDetailRow( value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = value.capitalize(),
            style = Typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
