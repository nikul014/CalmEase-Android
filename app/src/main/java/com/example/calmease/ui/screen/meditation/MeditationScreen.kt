package com.example.calmease.ui.screen.meditation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.calmease.viewmodel.MeditationViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.calmease.viewmodel.Meditation
import androidx.navigation.NavController
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.example.calmease.ui.components.SearchBox


@Composable
fun MeditationScreen(viewModel: MeditationViewModel = viewModel(), navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    // Load the meditations on the initial load or when needed
    val scrollState = rememberScrollState()
    val meditations = viewModel.meditations.value
    val searchQuery = remember { mutableStateOf("") }
    var filteredMeditations by remember { mutableStateOf(emptyList<Meditation>()) }
    var searchedMeditations by remember { mutableStateOf(emptyList<Meditation>()) }
    val onSearch: (String) -> Unit = { query ->
        filteredMeditations = meditations.filter {
            it.title.contains(query, ignoreCase = true) // Match based on title
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
                // SearchBox with search logic on button click
                Box(modifier = Modifier.weight(weight = 1f)) {
                    SearchBox(
                        searchQuery = searchQuery.value,
                        onSearchQueryChanged = { newQuery ->
                            searchQuery.value = newQuery
                        },
                        onSearch = { query ->
                            searchedMeditations = meditations.filter {
                                it.title.contains(query, ignoreCase = true)
                            }
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
            val meditationsToDisplay = when {
                filteredMeditations.isNotEmpty() -> filteredMeditations
                searchedMeditations.isNotEmpty() -> searchedMeditations
                else -> meditations
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(meditationsToDisplay) { meditation ->
                    MeditationItem(
                        meditation = meditation,
                        searchQuery = searchQuery.value,
                        navController = navController
                    )
                }
            }
        }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewMeditateScreen() {
    val navController = rememberNavController()
    MeditationScreen(viewModel = viewModel(), navController = navController)
}

