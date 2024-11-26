package com.example.calmease.ui.screen.about

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

data class TeamMember(
    val name: String,
    val role: String,
    val profileImage: String // Path to profile image
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {

    val scrollState = rememberScrollState()


    val teamMembers = listOf(
        TeamMember("Nikulkumar Kukadiya", "Mobile Lead Developer", "nk865270@dal.ca"),
        TeamMember("Dhruvi Shah", "Full Stack Developer", "dh368867@dal.ca"),
        TeamMember("Mithun Khanna Baskaran", "Product Manager", "mt813453@dal.ca"),
        TeamMember("Yukta Gurnani", "UI/UX Designer", "yk868576@dal.ca")
    )
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 36.dp)
            .verticalScroll(scrollState), // Make the column scrollable
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "CalmEase App",
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "CalmEase is a mindfulness app that seeks to meet the mental wellness problems of today’s youth. It assists individuals in enhancing mindfulness through guided meditations, breathing exercises, live sessions, and stress management among others. The app helps users deal with anxiety and stress, ultimately promoting overall mental health and a balanced life.",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "In addition to self-care techniques, CalmEase connects users to licensed therapists through in-app counseling options, making professional mental health support more accessible. The app also features mindfulness readings, including articles and blogs, to educate users and foster a supportive community. CalmEase differentiates itself from other mental health apps by combining self-help tools with personalized professional resources, creating a holistic platform for mental well-being. Through its comprehensive features, CalmEase aims to break the stigma around mental health, promote self-awareness, and help users build lasting habits of mindfulness and emotional resilience.",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Meet the Team",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Team member list
            teamMembers.forEach { member ->
                TeamMemberCard(member)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Started: September 2024",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "© 2024 CalmEase. All rights reserved.",
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "The idea and development of CalmEase are owned by the project team listed above. Any reproduction of this app for commercial use without permission is prohibited.",
                style = MaterialTheme.typography.bodySmall
            )
        }


    }
}

@Composable
fun TeamMemberCard(member: TeamMember) {
    val initials = member.name.split(" ").take(2).joinToString("") { it.take(1).uppercase() }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 8.dp
            ) // Added horizontal padding for better spacing
        ,
        shape = RoundedCornerShape(16.dp), // Rounded corners for the card
        colors = CardDefaults.cardColors(containerColor = Color.White) // White background
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) {
                Text(
                    text = initials,
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = member.name,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = member.role,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}