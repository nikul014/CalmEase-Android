package com.example.calmease.ui.screen.signup

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.calmease.ui.components.CustomButton
import com.example.calmease.ui.components.CustomTextField
import com.example.calmease.viewmodel.AuthState
import com.example.calmease.viewmodel.AuthViewModel

@Composable
fun SignupScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val email = authViewModel.email.collectAsState().value
    val password = authViewModel.password.collectAsState().value
    val confirmPassword = authViewModel.confirmPassword.collectAsState().value
    val fullName = authViewModel.fullName.collectAsState().value
    val state = authViewModel.state.collectAsState().value


    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            navController.navigate("dashboard") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign Up", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = fullName,
            onValueChange = authViewModel::onFullNameChange,
            label = "Full Name"
        )

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

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = confirmPassword,
            onValueChange = authViewModel::onConfirmPasswordChange,
            label = "Confirm Password"
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Show the loader when in the loading state
        CustomButton(
            text = if (state is AuthState.Loading) "Signing Up..." else "Sign Up",
            onClick = { authViewModel.performSignup() },
            isLoading = state is AuthState.Loading // Pass loading state to button
        )

        // Display success or error message after signup attempt
        Spacer(modifier = Modifier.height(16.dp))
        if (state is AuthState.Success) {
            Text(text = state.message, color = MaterialTheme.colorScheme.primary)
        }
        if (state is AuthState.Error) {
            Text(text = state.message, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("login") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back to Login")
        }
    }
}
