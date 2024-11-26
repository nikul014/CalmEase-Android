package com.example.calmease.ui.screen.article

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calmease.ui.components.SearchBox
import com.example.calmease.viewmodel.Article
import com.example.calmease.viewmodel.ArticleViewModel
@Composable
fun ArticleScreen(viewModel: ArticleViewModel = viewModel(), navController: NavController) {

    val articles = viewModel.articles.value
    val searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(searchQuery.value) {
        if (searchQuery.value.isNotEmpty()) {
            viewModel.searchArticles(searchQuery.value)
        } else {
            viewModel.searchArticles("")
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    SearchBox(
                        searchQuery = searchQuery.value,
                        onSearchQueryChanged = { newQuery ->
                            searchQuery.value = newQuery
                        },
                        onSearch = { query ->
                        }
                    )
                }
            }

        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 8.dp)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(articles) { article ->
                    ArticleItem(
                        article = article,
                        searchQuery = searchQuery.value,
                        navController = navController
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewArticleScreen() {
    val navController = rememberNavController()
    ArticleScreen(viewModel = viewModel(), navController = navController)
}
