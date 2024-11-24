package com.example.calmease.ui.screen.article

import SearchBox
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calmease.viewmodel.Article
import com.example.calmease.viewmodel.ArticleViewModel

@Composable
fun ArticleScreen(viewModel: ArticleViewModel = viewModel(), navController: NavController) {

    var expanded by remember { mutableStateOf(false) }

    // Load the meditations on the initial load or when needed
    val scrollState = rememberScrollState()
    val articles = viewModel.articles.value
    val filterBoxHeight = 50.dp
    val searchQuery = remember { mutableStateOf("") }
    var filteredArticles by remember { mutableStateOf(emptyList<Article>()) }
    var searchedArticles by remember { mutableStateOf(emptyList<Article>()) }

    LazyColumn( // Use LazyColumn for the entire screen
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {

            SearchBox(
                searchQuery = searchQuery.value,
                onSearchQueryChanged = { newQuery ->
                    searchQuery.value = newQuery
                },
                onSearch = { query ->
                    searchedArticles = articles.filter {
                        it.title.contains(query, ignoreCase = true)
                    }
                }

            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Articles",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Determine articles to display based on filters and search
        val articlesToDisplay = when {
            filteredArticles.isNotEmpty() -> filteredArticles
            searchedArticles.isNotEmpty() -> searchedArticles
            else -> articles
        }

        // Display articles
        items(articlesToDisplay) { article ->
            ArticleItem(
                article = article,
                searchQuery = searchQuery.value,
                navController = navController
            )
            Spacer(modifier = Modifier.height(8.dp)) // Add space between items
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewArticleScreen() {
    val navController = rememberNavController()
    ArticleScreen(viewModel = viewModel(), navController = navController)
}