package com.example.calmease.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ContactState {
    object Idle : ContactState()
    object Loading : ContactState()
    data class Success(val message: String) : ContactState()
    data class Error(val message: String) : ContactState()
}

class ContactViewModel : ViewModel() {

    val userName = MutableStateFlow("")
    val userEmail = MutableStateFlow("")
    val message = MutableStateFlow("")
    private val _state = MutableStateFlow<ContactState>(ContactState.Idle)
    val state: StateFlow<ContactState> = _state

    fun onUserNameChange(newValue: String) {
        userName.value = newValue
    }

    fun onUserEmailChange(newValue: String) {
        userEmail.value = newValue
    }

    fun onMessageChange(newValue: String) {
        message.value = newValue
    }

    private fun validateInput(): Boolean {
        return userName.value.isNotBlank() &&
                android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail.value).matches() &&
                message.value.isNotBlank()
    }

    fun submitContactRequest() {
        if (!validateInput()) {
            _state.value = ContactState.Error("Please fill all fields with valid information")
            return
        }

        viewModelScope.launch {
            _state.value = ContactState.Loading
            kotlinx.coroutines.delay(1000)
            _state.value = ContactState.Success("Request submitted successfully")
        }
    }
}
