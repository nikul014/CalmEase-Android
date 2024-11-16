package com.example.calmease.ui.screen.forgot_password


import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calmease.ui.components.CustomButton
import com.example.calmease.ui.components.CustomTextField
import com.example.calmease.viewmodel.AuthViewModel

@Composable
fun ForgotPasswordScreen(authViewModel: AuthViewModel = viewModel()) {
    val email = authViewModel.email.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Forgot Password", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = email,
            onValueChange = authViewModel::onEmailChange,
            label = "Email"
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Reset Password",
            onClick = { authViewModel.performForgotPassword() }
        )
    }
}
