package com.example.calmease

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calmease.enums.SharedPrefKeys
import com.example.calmease.ui.screen.about.AboutScreen
import com.example.calmease.ui.screen.breathing.BreathingScreen
import com.example.calmease.ui.screen.contact.ContactScreen
import com.example.calmease.ui.screen.dashboard.DashboardScreen
import com.example.calmease.ui.screen.login.LoginScreen
import com.example.calmease.ui.screen.signup.SignupScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val isLoggedIn =
        CalmEaseApplication.sharedPreferenceHelper.get(SharedPrefKeys.TOKEN, "").isNotEmpty()

   // val startDestination = if (isLoggedIn) "dashboard" else "login"

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("dashboard") { DashboardScreen() }
        composable("about") { AboutScreen(navController) }
        composable("contact") { ContactScreen(navController) }
        composable("breathing") { BreathingScreen() }
    }
}
