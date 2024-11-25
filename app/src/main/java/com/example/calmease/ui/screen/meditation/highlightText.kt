package com.example.calmease.ui.screen.meditation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun highlightText(text: String, query: String): AnnotatedString {
    val annotatedString = buildAnnotatedString {
        var startIndex = 0
        // Loop through all occurrences of the query
        while (startIndex < text.length) {
            // Find the next occurrence of the query (case-insensitive)
            val index = text.indexOf(query, startIndex, ignoreCase = true)
            if (index != -1) {
                // Append text before the match
                append(text.substring(startIndex, index))
                // Highlight the match
                withStyle(style = SpanStyle(color = Color.Yellow)) {
                    append(text.substring(index, index + query.length))
                }
                // Move startIndex past the current match
                startIndex = index + query.length
            } else {
                // No more matches, just append the remaining text
                append(text.substring(startIndex))
                break
            }
        }
    }
    return annotatedString
}
