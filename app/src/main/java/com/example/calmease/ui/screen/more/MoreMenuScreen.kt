package com.example.calmease.ui.screen.more

import androidx.compose.runtime.Composable

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import com.example.calmease.R
import com.example.calmease.network.CreateMemoryRequest
import com.example.calmease.network.Session
import com.example.calmease.network.SessionRequest
import com.example.calmease.network.createMemory
import com.example.calmease.ui.theme.Poppins
import com.example.calmease.utils.SharedPreferenceHelper
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
fun MoreMenuScreen(navController: NavController, context: Context, parentNavController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "More", style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(16.dp))

        ArticleCard()
        // Menu items
        MenuItem("Live Sessions", navController, "session_list", context, parentNavController)
        MenuItem("About Us", navController, "about_us", context, parentNavController)
        MenuItem("Terms and Conditions", navController, "terms_conditions", context, parentNavController)
        MenuItem("Privacy Policy", navController, "privacy_policy", context, parentNavController)
        MenuItem("Profile", navController, "profile", context, parentNavController)
        MenuItem("Logout", navController, "logout", context, parentNavController) // Added context for logout

    }
}

@Composable
fun ArticleCard() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Card 1
        ArticleCardItem(title = "Card Title 1", item1 = "Item 1", item2 = "Item 2")
        Spacer(modifier = Modifier.height(16.dp))

        // Card 2
        ArticleCardItem(title = "Card Title 2", item1 = "Item 1", item2 = "Item 2")
        Spacer(modifier = Modifier.height(16.dp))

        // Card 3
        ArticleCardItem(title = "Card Title 3", item1 = "Item 1", item2 = "Item 2")
    }
}

@Composable
fun ArticleCardItem(title: String, item1: String, item2: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        shape = RoundedCornerShape(8.dp), // optional to give rounded corners
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Title text
            Text(
                text = title,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = Poppins
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Horizontal line
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Item 1 and Item 2 with clickable text
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // equal padding for items
            ) {
                ClickableTextItem(text = item1)
                ClickableTextItem(text = item2)
            }
        }
    }
}

@Composable
fun ClickableTextItem(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .clickable {
                // Handle click action here
            }
    )
}



@Composable
fun MenuItem(label: String, navController: NavController, route: String, context: Context, parentNavController: NavController) {
    if (label == "Logout") {
        Button(
            onClick = {
                SharedPreferenceHelper.getInstance(context).clearAll()
                // Navigate to the login screen
                parentNavController.navigate("login") {
                    // Clear the back stack so that the user can't go back to the menu after logout
                    popUpTo("login") { inclusive = true }
                }

            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = label, style = MaterialTheme.typography.titleLarge)
        }
    } else {
        Button(
            onClick = { navController.navigate(route) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = label, style = MaterialTheme.typography.titleLarge)
        }
    }
}