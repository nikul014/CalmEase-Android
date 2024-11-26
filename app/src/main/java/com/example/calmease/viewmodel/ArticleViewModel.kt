package com.example.calmease.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.calmease.network.ApiService
import com.example.calmease.network.ArticleApiService
import com.example.calmease.network.Pagination
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val _articles = mutableStateOf<List<Article>>(emptyList()) // Backing property
    val articles: State<List<Article>> = _articles

    // Function to load articles from the API
    private suspend fun loadArticles(page: Int, pageSize: Int, searchTerm: String) {
        try {
            // Logging interceptor
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

            // OkHttpClient
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

            // Retrofit setup
            val retrofit = Retrofit.Builder()
                .baseUrl("https://calmease-backend.onrender.com/") // Update with the actual base URL
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val apiService = retrofit.create(ArticleApiService::class.java)

            val response = apiService.getArticles(page, pageSize, searchTerm)
            _articles.value = response.data // Set the articles data from API response
        } catch (e: Exception) {
            // Handle error, maybe set an error message or log it
            _articles.value = emptyList()
        }
    }

    // Function to trigger the loadArticles API call when the search term is updated
    fun searchArticles(query: String) {
        viewModelScope.launch {
            loadArticles(page = 1, pageSize = 5, searchTerm = query)
        }
    }
}


data class ArticleResponse(
    val data: List<Article>,
    val pagination: Pagination
)

data class Article(
    val article_id: Int,
    val title: String,
    val content: String,
    val created_at: String,
    val updated_at: String,
    val user_id: Int,
    val user_name: String,
    val user_email: String,
    val image: String,
    val tags: String
)


//
//data class Article(
//    val category: String,
//    val description: String,
//    val author: String,
//    val duration: String,
//    val media_type: String,
//    val content_url: String,
//    val rating: Float,
//)