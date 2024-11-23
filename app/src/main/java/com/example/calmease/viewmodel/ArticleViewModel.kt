package com.example.calmease.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
    private val _articles = mutableStateOf<List<Article>>(emptyList()) // Backing property
    val articles: State<List<Article>> = _articles

    init {
        loadArticles()
    }

    private fun loadArticles() {
        // Simulated list of articles
        val mockArticles = listOf(
            Article(
                Article_id = 1,
                title = "Understanding Mindfulness",
                category = "Wellness",
                description = "Learn how mindfulness can transform your life.",
                content = "Explore ways to improve your sleep quality.",
                author = "Jane Ayre",
                created_at = "2024-01-01",
                updated_at = "2024-01-05",
                duration = "5 min",
                media_type = "Text",
                content_url = "https://example.com/article1",
                image_url = "https://libraryitems.insighttimer.com/d7w3j7p4u1v0u2b6e8w2y0k7z4m1n9g8t2s1g5e0/pictures/tiny_rectangle_medium.jpeg",
                rating = 4.5f,
            ),
            Article(
                Article_id = 2,
                title = "10 Tips for Better Sleep",
                category = "Health",
                description = "Explore ways to improve your sleep quality.",
                content = "Explore ways to improve your sleep quality.",
                author = "Raven",
                created_at = "2024-01-10",
                updated_at = "2024-01-15",
                duration = "8 min",
                media_type = "Video",
                content_url = "https://example.com/article2",
                image_url = "https://libraryitems.insighttimer.com/p2t1j0w3q8p0w0m6j8s9g0f0k4h9f1f0h1z7j7l2/pictures/tiny_rectangle_medium.jpeg",
                rating = 4.8f,

            )
        )
        _articles.value = mockArticles // Assign mock data to LiveData
    }
}

data class Article(
    var Article_id: Int,
    val title: String,
    val category: String,
    val description: String,
    val content: String,
    val author: String,
    val created_at: String,
    val updated_at: String,
    val duration: String,
    val media_type: String,
    val content_url: String,
    val image_url: String,
    val rating: Float,
)