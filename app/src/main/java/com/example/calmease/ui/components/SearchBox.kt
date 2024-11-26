package com.example.calmease.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun SearchBox(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChanged,
            placeholder = {
                Text(text = "Search...")
            },
            colors =

            TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .weight(1f)
                .background(Color.Transparent),
            singleLine = true
        )

        Icon(
            imageVector = Icons.Rounded.Search,
            contentDescription = "Search",
            tint = Color.Black,
            modifier = Modifier
                .padding(start = 8.dp, end = 2.dp)
                .clickable { onSearch(searchQuery) } // Make the icon clickable
        )

    }
}
