package com.example.calmease.viewmodel

import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmease.CalmEaseApplication
import com.example.calmease.network.AuthService
import com.example.calmease.network.LoginRequest
import com.example.calmease.network.SignupRequest
import com.example.calmease.network.ForgotPasswordRequest
import com.example.calmease.network.MemoryApiService
import com.example.calmease.network.NetworkModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


data class GoodMemory(
    val memoryId: Int,
    val title: String,
    val memory_date_time: String?,
    val description: String,
    val imageUrl: String,
    val userId: Int
)

class GoodMemoriesViewModel : ViewModel() {
    private val _memories = MutableStateFlow<List<GoodMemory>>(emptyList())
    val memories: StateFlow<List<GoodMemory>> = _memories

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://calmease-backend.onrender.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(MemoryApiService::class.java)

    fun fetchMemories(searchTerm: String = "", page: Int = 1, pageSize: Int = 10) {
        viewModelScope.launch {
            try {
                val response = apiService.getMemories(searchTerm, page, pageSize)
                _memories.value = response.data
            } catch (e: Exception) {
                // Handle the error, e.g., log it, show a toast, etc.
            }
        }
    }
}