package com.example.calmease.ui.screen.article

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calmease.viewmodel.Article
import com.example.calmease.viewmodel.ArticleViewModel

@Composable
fun ArticleDetailScreen(articleId: Int, viewModel: ArticleViewModel = viewModel()) {

    val article = viewModel.articles.value.find { it.Article_id == articleId }

    if (article!= null){
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.titleLarge

                    )

                    Spacer(modifier = Modifier.height(8.dp))


                    //Author details box
                    Box(){
                        Row {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Meditation Icon",
                                Modifier.size(42.dp)
                            )

                            AuthorDetails(article.author, article.duration, article.created_at)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Summary

                    Text(
                        text = "Summary",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = article.description,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.bodyMedium
                    )


                }

            }

        }
    }
}

@Composable
fun AuthorDetails(author: String, duration: String, created_at: String){
Column {
    Text(
        text = author,
        style = MaterialTheme.typography.titleMedium
    )

    val durationDetail = duration + "min read . " + created_at
    Text(
        text = durationDetail,
        style = MaterialTheme.typography.bodySmall
    )
}

}


//@Preview(showBackground = true)
//@Composable
//fun PreviewAArticleScreen() {
//    val article = Article(
//        1,
//        "10 tips for modern communication",
//        "Health",
//        "Wi-Fi Hacking is much easier than most people think and the way to achieve it is some common techniques that most hackers use. With a few simple steps, the average user can protect their home router from the five most common WiFi hacking methods",
//        "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.",
//        "Matia shaw",
//        "10 Dec",
//        "11/12",
//        "10",
//        "article",
//        "url",
//        "url",
//        5f
//    )
//    ArticleDetailScreen(article)
//}