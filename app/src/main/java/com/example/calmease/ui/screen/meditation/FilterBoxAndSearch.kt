package com.example.calmease.ui.screen.meditation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.calmease.ui.theme.CalmPrimaryDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.calmease.viewmodel.Meditation

@Composable
fun FilterBoxAndSearch(
    modifier: Modifier,
    meditations: List<Meditation>,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit,
    onFilteredMeditationsChange: (List<Meditation>) -> Unit,
    expanded: Boolean,
    onExpandToggle: () -> Unit,
) {
    var selectedVocal by remember { mutableStateOf("Vocals") }
    val vocalOptions = listOf("Vocals", "Female only", "Male only")

    var selectedSessionTime by remember { mutableStateOf("Session time") }
    val sessionTimeOptions = listOf("Session time", "Long", "Short", "Extended")

    var selectedSortBy by remember { mutableStateOf("Sort by") }
    val sortByOptions = listOf("Sort by", "Popular", "Newest")

    var rating by remember { mutableStateOf(1.0f) }
    var applyFilters by remember { mutableStateOf(false) }

    // Filtering logic that combines both search and filter criteria
    val filteredMeditations = remember(
        searchQuery, selectedVocal, selectedSessionTime, selectedSortBy, rating, applyFilters
    ) {
        // Step 1: Apply search filter
        val searchFiltered = if (searchQuery.isNotEmpty()) {
            meditations.filter { it.title.contains(searchQuery, ignoreCase = true) }
        } else {
            meditations
        }

        // Step 2: Apply filter criteria (vocal, session time, etc.)
        val filtered = searchFiltered.filter {
            (selectedVocal == "Vocals" || it.vocal == selectedVocal) &&
                    (selectedSessionTime == "Session time" || it.sessiontime == selectedSessionTime) &&
                    it.rating >= rating
        }

        // Step 3: Apply sort by criteria
        when (selectedSortBy) {
            "Popular" -> filtered.sortedByDescending { it.rating }
            "Newest" -> filtered.sortedByDescending { it.created_at }
            else -> filtered // No sorting
        }
    }

    // Update filtered meditations in parent composable when the filters are applied
    onFilteredMeditationsChange(filteredMeditations)

    if(expanded) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .animateContentSize() // Smooth transition when expanding or collapsing
        ) {
            Button(
                onClick = onExpandToggle,
                colors = ButtonDefaults.buttonColors(
                    containerColor = CalmPrimaryDark, // Purple background
                    contentColor = Color.White         // Text color
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (expanded) "Collapse Filter" else "Expand Filter")
            }

            // The filter section
            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)

                ) {
                    // Vocal Filter
                    FilterSection(
                        title = "Vocal Filter",
                        content = {
                            DropdownFilter(
                                selectedOption = selectedVocal,
                                options = vocalOptions,
                                onOptionSelected = { selectedVocal = it }
                            )
                        }
                    )

                    // Ratings Filter
                    FilterSection(
                        title = "Ratings Filter",
                        content = {
                            RatingSlider(rating) { newRating -> rating = newRating }
                        }
                    )

                    // Session Time Filter
                    FilterSection(
                        title = "Session Time Filter",
                        content = {
                            DropdownFilter(
                                selectedOption = selectedSessionTime,
                                options = sessionTimeOptions,
                                onOptionSelected = { selectedSessionTime = it }
                            )
                        }
                    )

                    // Sort By Filter
                    FilterSection(
                        title = "Sort By Filter",
                        content = {
                            DropdownFilter(
                                selectedOption = selectedSortBy,
                                options = sortByOptions,
                                onOptionSelected = { selectedSortBy = it }
                            )
                        }
                    )

                    // Apply Button to trigger the filtering
                    Button(
                        onClick = {
                            applyFilters = true // Apply the filters when the button is clicked
                        }, colors = ButtonDefaults.buttonColors(
                            containerColor = CalmPrimaryDark, // Purple background
                            contentColor = Color.White         // Text color
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 32.dp)
                    ) {
                        Text("Apply Filter")
                    }
                }
            }
        }
    }
}