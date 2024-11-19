import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit // Added a callback for search action
) {
    TextField(
        value = searchQuery,
        onValueChange = { newQuery ->
            onSearchQueryChanged(newQuery) // Update query as the user types
        },
        label = { Text("Search...") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp), // Added padding to separate the field from others
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search Icon"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFF6200EE),
            unfocusedIndicatorColor = Color.Gray,
            focusedLabelColor = Color(0xFF6200EE),
            unfocusedLabelColor = Color.Gray
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(searchQuery) // Trigger the search action when the user presses the search button
            }
        )
    )
}
