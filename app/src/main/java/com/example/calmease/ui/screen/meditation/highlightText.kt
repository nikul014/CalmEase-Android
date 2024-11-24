package com.example.calmease.ui.screen.meditation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle

@Composable
fun highlightText(text: String, query: String): AnnotatedString {
    // Creating an AnnotatedString with highlighted query matches
    val startIndex = text.indexOf(query, ignoreCase = true)
    return if (startIndex != -1) {
        val annotatedString = buildAnnotatedString {
            append(text.substring(0, startIndex))
            withStyle(style = SpanStyle(color = Color.Yellow)) { // Highlight color
                append(text.substring(startIndex, startIndex + query.length))
            }
            append(text.substring(startIndex + query.length))
        }
        annotatedString
    } else {
        AnnotatedString(text) // No match found, return the plain text
    }
}