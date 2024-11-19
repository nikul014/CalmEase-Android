package com.example.calmease.ui.screen.Meditation

import SearchBox
import androidx.compose.foundation.content.MediaType.Companion.Text
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
import com.example.calmease.ui.theme.Poppins
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.calmease.ui.screen.Meditation.FilterSection
import com.example.calmease.ui.screen.Meditation.MeditationItem



@Composable
fun MeditationScreen(viewModel: MeditationViewModel = viewModel(), navController: NavController) {
    var expanded by remember { mutableStateOf(false) }

    // Load the meditations on the initial load or when needed
    val scrollState = rememberScrollState()
    val meditations = viewModel.meditations.value
    val filterBoxHeight = if (expanded) 520.dp else 50.dp
    val searchQuery = remember { mutableStateOf("") }
    var filteredMeditations by remember { mutableStateOf(emptyList<Meditation>()) }
    var searchedMeditations by remember { mutableStateOf(emptyList<Meditation>()) }
    val onSearch: (String) -> Unit = { query ->
        filteredMeditations = meditations.filter {
            it.title.contains(query, ignoreCase = true) // Match based on title
        }

    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .heightIn(min = filterBoxHeight)
            .verticalScroll(scrollState)
    ) {

        // SearchBox with search logic on button click
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
        Spacer(modifier = Modifier.height(16.dp))


        FilterBoxAndSearch(
            modifier = Modifier
                .fillMaxWidth(),
            meditations = meditations,
            searchQuery = searchQuery.value,
            onSearchQueryChanged = { newQuery ->
                searchQuery.value = newQuery
            },
            onFilteredMeditationsChange = { newFilteredMeditations ->
                filteredMeditations = newFilteredMeditations
            },
            expanded = expanded,
            onExpandToggle = { expanded = !expanded },
            filterBoxHeight = filterBoxHeight
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 140.dp)
    ) {
        val meditationsToDisplay = when {
            filteredMeditations.isNotEmpty() -> filteredMeditations
            searchedMeditations.isNotEmpty() -> searchedMeditations
            else -> meditations
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = filterBoxHeight)
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


@Preview(showBackground = true)
@Composable
fun PreviewMeditateScreen() {
    val navController = rememberNavController()
    MeditationScreen(viewModel = viewModel(),navController = navController)
}

