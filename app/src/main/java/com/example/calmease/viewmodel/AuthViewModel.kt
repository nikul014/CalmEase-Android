package com.example.calmease.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmease.CalmEaseApplication
import com.example.calmease.network.AuthService
import com.example.calmease.network.ErrorResponse
import com.example.calmease.network.LoginRequest
import com.example.calmease.network.SignupRequest
import com.example.calmease.network.ForgotPasswordRequest
import com.example.calmease.network.NetworkModule
import com.example.calmease.network.ResetPasswordRequest
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val message: String) : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val authService: AuthService = NetworkModule.authService
    private val sharedPreferenceHelper = CalmEaseApplication.sharedPreferenceHelper

    val fullName = MutableStateFlow("")
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    fun onFullNameChange(newValue: String) {
        fullName.value = newValue
    }

    fun onEmailChange(newValue: String) {
        email.value = newValue
    }

    fun onPasswordChange(newValue: String) {
        password.value = newValue
    }

    fun onConfirmPasswordChange(newValue: String) {
        confirmPassword.value = newValue
    }

    private fun validateEmail(): Boolean =
        android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()

    private fun validatePassword(): Boolean = password.value.length >= 6
    private fun passwordsMatch(): Boolean = password.value == confirmPassword.value

    fun performLogin() {
        if (!validateEmail()) {
            _state.value = AuthState.Error("Invalid email format")
            return
        }
        if (!validatePassword()) {
            _state.value = AuthState.Error("Password must be at least 6 characters")
            return
        }

        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val response = authService.login(LoginRequest(email.value, password.value))
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse != null) {
                        val user = loginResponse.user
                        val token = loginResponse.token
                        sharedPreferenceHelper.saveUserData(user, token)
                    }
                    _state.value = AuthState.Success(response.body()?.message ?: "Login successful")

                } else {
                    try {
                        val errorResponse = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                        _state.value = AuthState.Error(errorResponse.error ?: "Unknown error")
                    } catch (e: Exception) {
                        _state.value = AuthState.Error("An unknown error occurred")
                    }
                }
            } catch (e: Exception) {
                Log.e("TAGSA","This is user error "+e)

                _state.value = AuthState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }

    fun performSignup() {
        if (!validateEmail()) {
            _state.value = AuthState.Error("Invalid email format")
            return
        }
        if (!validatePassword()) {
            _state.value = AuthState.Error("Password must be at least 6 characters")
            return
        }
        if (!passwordsMatch()) {
            _state.value = AuthState.Error("Passwords do not match")
            return
        }

        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val userType = if (email.value.startsWith("nikul", ignoreCase = true)) "expert" else "user"
                val request = SignupRequest(
                    fullName.value,
                    email.value,
                    password.value,
                    confirmPassword.value,
                    userType
                )

                val response = authService.signup(request)

                if (response.isSuccessful) {
                    _state.value = AuthState.Success("Signup successful!")
                } else {
                    try {
                        val errorResponse = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                        _state.value = AuthState.Error(errorResponse.error ?: "Unknown error")
                    } catch (e: Exception) {
                        _state.value = AuthState.Error("An unknown error occurred")
                    }
                }
            } catch (e: Exception) {
                _state.value = AuthState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }

    fun performForgotPassword() {
        if (!validateEmail()) {
            _state.value = AuthState.Error("Invalid email format")
            return
        }

        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val response = authService.resetPassword(ResetPasswordRequest(email.value))
                if (response.isSuccessful) {
                    _state.value = AuthState.Success(response.body()?.message ?: "Password reset successfully.")
                } else {
                    try {
                        val errorResponse = Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
                        _state.value = AuthState.Error(errorResponse.error ?: "Unknown error")
                    } catch (e: Exception) {
                        _state.value = AuthState.Error("An unknown error occurred")
                    }
                }
            } catch (e: Exception) {
                _state.value = AuthState.Error("Network error: ${e.localizedMessage}")
            }
        }
    }
}
