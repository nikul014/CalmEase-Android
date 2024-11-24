package com.example.calmease.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.calmease.ui.components.CustomButton
import com.example.calmease.ui.components.CustomTextField
import com.example.calmease.viewmodel.AuthViewModel
import com.example.calmease.viewmodel.AuthState

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val email = authViewModel.email.collectAsState().value
    val password = authViewModel.password.collectAsState().value
    val state = authViewModel.state.collectAsState().value
    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            navController.navigate("dashboard") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Login", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = email,
            onValueChange = authViewModel::onEmailChange,
            label = "Email"
        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = password,
            onValueChange = authViewModel::onPasswordChange,
            label = "Password"
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is AuthState.Loading -> CircularProgressIndicator()
            is AuthState.Success -> Text(text = state.message, color = MaterialTheme.colorScheme.primary)
            is AuthState.Error -> Text(text = state.message, color = MaterialTheme.colorScheme.error)
            else -> {}
        }


        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Login",
            onClick = { authViewModel.performLogin() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("signup") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Go to Signup")
        }
    }
}
