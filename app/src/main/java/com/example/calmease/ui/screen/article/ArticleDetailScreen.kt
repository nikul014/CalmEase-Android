package com.example.calmease.ui.screen.article

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calmease.ui.theme.Poppins
import com.example.calmease.viewmodel.Article
import com.example.calmease.viewmodel.ArticleViewModel

@Composable
fun ArticleDetailScreen(article: Article, viewModel: ArticleViewModel = viewModel()) {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(vertical = 16.dp, horizontal = 24.dp)
    ) {
        Text(
            text = article.title,
            style = TextStyle(
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )



        Text(
            text = "Summary",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = article.content,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )


        Spacer(modifier = Modifier.height(8.dp))


        InformationCard(
            rating = article.tags.toString(),
            category = article.user_email
        )
        Spacer(modifier = Modifier.height(8.dp))


    }
}


@Composable
fun InformationCard(
    rating: String,
    category: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Text(
            text = "Information",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        InfoCard(label = "Tags", value = rating)
        InfoCard(label = "Author", value = category)
    }
}

@Composable
fun InfoCard(label: String, value: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = MaterialTheme.shapes.small
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

