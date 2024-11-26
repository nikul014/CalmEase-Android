package com.example.calmease.ui.screen.meditation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.calmease.ui.components.SearchBox
import com.example.calmease.viewmodel.MeditationViewModel


@Composable
fun MeditationScreen(viewModel: MeditationViewModel = viewModel(), navController: NavController) {

    val meditations = viewModel.meditations.value
    val searchQuery = remember { mutableStateOf("") }

    LaunchedEffect(searchQuery.value) {
        if (searchQuery.value.isNotEmpty()) {
            viewModel.searchMeditation(searchQuery.value) // Trigger search on text change
        } else {
            viewModel.searchMeditation("") // Empty search will reload all articles
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
                modifier = Modifier
                    .fillMaxSize()
            ) {
                items(meditations) { meditation ->
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

