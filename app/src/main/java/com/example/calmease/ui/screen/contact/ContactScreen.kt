package com.example.calmease.ui.screen.contact

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.calmease.viewmodel.ContactState
import com.example.calmease.ui.components.CustomButton
import com.example.calmease.ui.components.CustomTextField
import com.example.calmease.viewmodel.ContactViewModel

@Composable
fun ContactScreen(navController: NavController, contactViewModel: ContactViewModel = viewModel()) {
    val userName = contactViewModel.userName.collectAsState().value
    val userEmail = contactViewModel.userEmail.collectAsState().value
    val message = contactViewModel.message.collectAsState().value
    val state = contactViewModel.state.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Contact Us",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            value = userName,
            onValueChange = contactViewModel::onUserNameChange,
            label = "Name",
            leadingIconId = Icons.Default.Person

        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = userEmail,
            onValueChange = contactViewModel::onUserEmailChange,
            label = "Email",
            leadingIconId = Icons.Default.Email

        )

        Spacer(modifier = Modifier.height(8.dp))

        CustomTextField(
            value = message,
            onValueChange = contactViewModel::onMessageChange,
            label = "Message",
            leadingIconId = Icons.Default.Info
,
                    modifier = Modifier.height(150.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (state) {
            is ContactState.Loading -> CircularProgressIndicator()
            is ContactState.Success -> Text(text = state.message, color = MaterialTheme.colorScheme.primary)
            is ContactState.Error -> Text(text = state.message, color = MaterialTheme.colorScheme.error)
            else -> {}
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomButton(
            text = "Submit",
            onClick = { contactViewModel.submitContactRequest() }
        )
    }
}