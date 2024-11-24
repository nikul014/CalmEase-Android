package com.example.calmease.ui.screen.dashboard

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.calmease.R
import com.example.calmease.ui.screen.meditation.MeditationDetailScreen
import com.example.calmease.ui.screen.meditation.MeditationScreen
import com.example.calmease.ui.screen.sessions.VideoActivity
import com.example.calmease.ui.theme.CalmPrimaryDark

@Composable
fun DashboardScreen() {
    val navController = rememberNavController()

    val bottomNavItems = listOf("Meditation", "Breathing", "Articles", "Profile", "More")
    val currentScreen = remember { mutableStateOf(bottomNavItems[0]) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = "meditation"
            ) {
                composable("meditation") { MeditationScreen(viewModel = viewModel(),navController = navController) }
                composable("meditation_detail/{meditation_id}") { backStackEntry ->
                    val meditationIdString = backStackEntry.arguments?.getString("meditation_id")
                    val meditationId = meditationIdString?.toIntOrNull()
                    Log.d("MeditationDetail", "Meditation ID: $meditationId")
                    MeditationDetailScreen(meditationId = meditationId)
                }
                composable("breathing") { BreathingScreen() }
                composable("memories") { val context = LocalContext.current
                    LaunchedEffect(Unit) {
                        val intent = Intent(context, VideoActivity::class.java).apply {
                            putExtra("ChannelName", "2")
                            putExtra("UserRole", "publisher")
                        }
                        ContextCompat.startActivity(context, intent, null)
                    } }
                composable("articles") { ArticleScreen() }
                composable("more") { MoreScreen() }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = currentRoute == "meditation", // Set to `true` when the item is active
            onClick = { navController.navigate("meditation") },
            label = { Text("Meditation") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_meditate_name),
                    contentDescription = "Meditation Icon"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = CalmPrimaryDark,
                indicatorColor = CalmPrimaryDark,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )


        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("breathing") },
            label = { Text("Breathing") },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("memories") },
            label = { Text("Good Memories") },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("articles") },
            label = { Text("Articles") },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("more") },
            label = { Text("More") },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
        )
    }
}




@Composable
fun BreathingScreen() {
    Text(text = "Breathing Screen")
}

@Composable
fun ArticleScreen() {
    Text(text = "Article Screen")
}

@Composable
fun ProfileScreen() {
    Text(text = "Profile Screen")
}
@Composable
fun MemoriesScreen(channelName: String, userRole: String) {
    val context = LocalContext.current

    Button(onClick = {
        val intent = Intent(context, VideoActivity::class.java).apply {
            putExtra("ChannelName", channelName)
            putExtra("UserRole", userRole)
        }
        ContextCompat.startActivity(context, intent, null)
    }) {
        Text("Start Video Activity")
    }
}


@Composable
fun MoreScreen() {
    Text(text = "More Screen")
}
