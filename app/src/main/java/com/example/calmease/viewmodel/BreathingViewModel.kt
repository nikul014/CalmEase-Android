package com.example.calmease.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson

class BreathingViewModel(application: Application) : AndroidViewModel(application) {
    private val _categories = mutableStateOf<List<BreathingCategory>>(emptyList())
    val categories: State<List<BreathingCategory>> = _categories

    init {
        loadBreathingCategories()
    }

    private fun loadBreathingCategories() {
        val jsonString = loadJSONFromAsset("breathing_exercises.json")
        val gson = Gson()
        val breathingResponse = gson.fromJson(jsonString, BreathingResponse::class.java)
        _categories.value = breathingResponse.categories
    }

    private fun loadJSONFromAsset(fileName: String): String {
        val assetManager = getApplication<Application>().assets
        val inputStream = assetManager.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}

data class BreathingResponse(
    val categories: List<BreathingCategory>
)

data class BreathingCategory(
    val id: Int,
    val title: String,
    val category_image: String,
    val background_image: String,
    val exercises: List<BreathingExercise>
)

data class BreathingExercise(
    val timing: String,
    val breaths_per_min: String,
    val description: String,
    val audio_url: String
)
