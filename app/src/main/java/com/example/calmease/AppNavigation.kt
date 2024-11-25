package com.example.calmease

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calmease.enums.SharedPrefKeys
import com.example.calmease.ui.screen.breathing.BreathingCategoriesScreen
import com.example.calmease.ui.screen.contact.ContactScreen
import com.example.calmease.ui.screen.dashboard.DashboardScreen
import com.example.calmease.ui.screen.forgot_password.ForgotPasswordScreen
import com.example.calmease.ui.screen.login.LoginScreen
import com.example.calmease.ui.screen.signup.SignupScreen
import com.example.calmease.viewmodel.BreathingViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    val isLoggedIn =
        CalmEaseApplication.sharedPreferenceHelper.isLoggedIn()

    Log.e("TAGSA","Login:"+isLoggedIn)
     val startDestination = if (isLoggedIn) "dashboard" else "login"

    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(onSplashComplete = {
                navController.navigate(startDestination) {
                    popUpTo("splash") { inclusive = true }
                }
            })
        }
        composable("login") { LoginScreen(navController) }
        composable("signup") { SignupScreen(navController) }
        composable("resetPassword") { ForgotPasswordScreen(navController) }
        composable("dashboard") { DashboardScreen(navController) }

    }
}
