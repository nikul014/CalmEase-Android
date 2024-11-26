package com.example.calmease.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.example.calmease.network.ArticleApiService
import com.example.calmease.network.MeditationApiService
import com.example.calmease.network.Pagination
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MeditationViewModel(application: Application) : AndroidViewModel(application) {
    private val _meditations = mutableStateOf<List<Meditation>>(emptyList())
    val meditations: State<List<Meditation>> = _meditations


    private suspend fun loadMeditation(page: Int, pageSize: Int, searchTerm: String) {
        try {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://calmease-backend.onrender.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(MeditationApiService::class.java)

            val response = apiService.getMeditation(page, pageSize, searchTerm)
            _meditations.value = response.data
        } catch (e: Exception) {
            _meditations.value = emptyList()
        }
    }

    fun searchMeditation(query: String) {
        viewModelScope.launch {
            loadMeditation(page = 1, pageSize = 5, searchTerm = query)
        }
    }

}

data class MeditationResponse(
    val data: List<Meditation>,
    val pagination: Pagination
)

data class Meditation(
    val meditation_id: Int,
    val title: String,
    val category: String,
    val description: String,
    val created_at: String,
    val updated_at: String,
    val duration: String,
    val media_type: String,
    val content_url: String,
    val user_id: Int,
    val user_name: String,
    val user_email: String,
    val image_url: String,
    val rating:Float,
    val vocal:String,
    val sessiontime:String
)
