package com.example.calmease.ui.screen.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*

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
                composable("meditation") { MeditationScreen() }
                composable("breathing") { BreathingScreen() }
                composable("articles") { ArticleScreen() }
                composable("profile") { ProfileScreen() }
                composable("more") { MoreScreen() }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("meditation") },
            label = { Text("Meditation") },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("breathing") },
            label = { Text("Breathing") },
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
            onClick = { navController.navigate("profile") },
            label = { Text("Profile") },
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
fun MeditationScreen() {
    Text(text = "Meditation Screen")
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
fun MoreScreen() {
    Text(text = "More Screen")
}
