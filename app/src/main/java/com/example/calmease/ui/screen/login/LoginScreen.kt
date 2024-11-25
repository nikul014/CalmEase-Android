package com.example.calmease.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.calmease.R
import com.example.calmease.ui.components.CustomButton
import com.example.calmease.ui.components.CustomTextField
import com.example.calmease.ui.theme.CalmBackground
import com.example.calmease.viewmodel.AuthState
import com.example.calmease.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    val email by authViewModel.email.collectAsState()
    val password by authViewModel.password.collectAsState()
    val state = authViewModel.state.collectAsState().value
    val context = LocalContext.current

    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            navController.navigate("dashboard") {
                popUpTo("signup") { inclusive = true }
            }
        } else if (state is AuthState.Error) {
            Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15F)) // Background color
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp) // Add bottom padding to ensure no overlap
        ) {
            // App logo and text content at the top
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
                contentAlignment = Alignment.Center

            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo), // Replace with your drawable name
                        contentDescription = "App Logo",
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Take a step towards calmness",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Card with login form
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = MaterialTheme.shapes.large.copy(
                    topStart = CornerSize(36.dp),
                    topEnd = CornerSize(36.dp),
                    bottomStart = CornerSize(0.dp),
                    bottomEnd = CornerSize(0.dp)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 30.dp, vertical = 36.dp) // Padding around the Box
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(), // White background inside the Column
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val loginText = "Log in to your account."
                        val loginWord = "Log in"
                        val loginAnnotatedString = buildAnnotatedString {
                            append(loginText)
                            addStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.W700
                                ),
                                start = 0,
                                end = loginWord.length
                            )
                        }

                        Text(
                            text = loginAnnotatedString,
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(bottom = 16.dp),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Email Field
                        CustomTextField(
                            value = email,
                            onValueChange = authViewModel::onEmailChange,
                            label = "Email Address",
                            keyboardType = KeyboardType.Email,
                            leadingIconId = Icons.Default.Email
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Password Field
                        CustomTextField(
                            value = password,
                            onValueChange = authViewModel::onPasswordChange,
                            label = "Password",
                            keyboardType = KeyboardType.Password,
                            visualTransformation = PasswordVisualTransformation(),
                            leadingIconId = Icons.Default.Lock

                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // State Handling
                        when (state) {
                            is AuthState.Loading -> CircularProgressIndicator()
                            is AuthState.Success -> Text(
                                text = "Welcome!",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            is AuthState.Error -> Text(
                                text = state.message,
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodyMedium
                            )

                            else -> {}
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(), // Make sure the row takes full width
                            horizontalArrangement = Arrangement.End // Align children (text) to the right
                        ) {
                            Text(
                                text = "Forgot Password?",
                                color = MaterialTheme.colorScheme.primary, // Use primary color from theme
                                style = MaterialTheme.typography.bodySmall, // Underlined text
                                modifier = Modifier
                                    .clickable {
                                        // Navigate to the reset password screen or perform any action
                                        navController.navigate("resetPassword") // Adjust the navigation route
                                    }
                                    .padding(top = 4.dp) // Padding for spacing
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        // Buttons
                        CustomButton(
                            text = "Login",
                            onClick = { authViewModel.performLogin() }
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "New to CalmEase? Register",
                            color = Color.Black,
                            style = MaterialTheme.typography.bodyMedium.copy(textDecoration = TextDecoration.Underline),
                            modifier = Modifier
                                .clickable { navController.navigate("signup") }
                                .padding(top = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(36.dp))
                    }
                }
            }
        }
    }
}