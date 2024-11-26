package com.example.calmease.network


import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

data class User(
    val id: Int,
    val email: String,
    val userType: String,
    val createdAt: String,
    val updatedAt: String
)


data class LoginRequest(val email: String, val password: String)

data class SignupRequest(
    val fullName: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
    val signupType: String = "user"
)
data class ForgotPasswordRequest(val email: String)
data class AuthResponse(val success: Boolean, val message: String)

data class SignupResponse(
    val message: String
)

data class ErrorResponse(
    val error: String? = null
)


// LoginResponse model to represent the login API response
data class LoginResponse(
    val message: String,
    val token: String,
    val user: User
)
interface AuthService {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/api/auth/register")
    suspend fun signup(@Body request: SignupRequest): Response<SignupResponse>

    @POST("api/auth/forgot-password")
    suspend fun forgotPassword(@Body request: ForgotPasswordRequest): Response<AuthResponse>

    @POST("api/auth/reset-password")
    suspend fun resetPassword(@Body request: ResetPasswordRequest): Response<AuthResponse>

}

data class ResetPasswordRequest(
    val email: String
)
