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
fun ArticleDetailScreen(articleId: Int, viewModel: ArticleViewModel = viewModel()) {

    val article = viewModel.articles.value.find { it.Article_id == articleId }
    val scrollState = rememberScrollState()

    if (article != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(vertical = 16.dp, horizontal = 24.dp)
        ) {
            // Title
            Text(
                text = article.title,
                style = TextStyle(
                    fontFamily = Poppins,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )



            // Summary Section
            Text(
                text = "Summary",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = article.description,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )


            // Article Content Section
            Text(
                text = article.content,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))


            // Information Card for Category
            InformationCard(
                rating = article.rating.toString(),
                category = article.category
            )
            Spacer(modifier = Modifier.height(8.dp))

            // Author Details Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = MaterialTheme.shapes.medium)
                    .padding(16.dp)

            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.PersonOutline,
                        contentDescription = "Author Icon",
                        modifier = Modifier.size(36.dp).padding(4.dp)
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    AuthorDetails(
                        author = article.author,
                        duration = article.duration,
                        createdAt = article.created_at
                    )
                }
            }
        }
    }
}

@Composable
fun AuthorDetails(author: String, duration: String, createdAt: String) {
    Column {
        Text(
            text = author,
            style = MaterialTheme.typography.titleSmall.copy(fontFamily = Poppins),
            modifier = Modifier.padding(bottom = 1.dp)
        )
        Text(
            text = "$duration min read • $createdAt",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )
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
        // Title of the Information section
        Text(
            text = "Information",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Author, rating, and category info
        InfoCard(label = "Rating", value = rating)
        InfoCard(label = "Category", value = category)
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

@Preview(showBackground = true)
@Composable
fun PreviewArticleDetailScreen() {
    val article = Article(
        1,
        "10 tips for modern communication",
        "Health",
        "Wi-Fi Hacking is much easier than most people think. Here’s how you can protect your router.",
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec ac neque nisi. Integer sed tortor nec risus feugiat.",
        "John Doe",
        "10 Dec",
        "11/12",
        "10",
        "article",
        "url",
        "url",
        4.5f
    )
    ArticleDetailScreen(articleId = article.Article_id)
}
