package com.example.calmease.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import com.google.gson.Gson
import androidx.compose.runtime.State



class MeditationViewModel(application: Application) : AndroidViewModel(application) {
    private val _meditations = mutableStateOf<List<Meditation>>(emptyList())
    val meditations: State<List<Meditation>> = _meditations

    init {
        loadMeditations()
    }

    private fun loadMeditations() {
        // Load the JSON data from assets
        val jsonString = loadJSONFromAsset("meditations.json")

        // Parse the JSON string into the MeditationResponse object
        val gson = Gson()
        val meditationResponse = gson.fromJson(jsonString, MeditationResponse::class.java)

        // Get the list of meditations and update the state
        _meditations.value = meditationResponse.data
    }

    private fun loadJSONFromAsset(fileName: String): String {
        // Access the assets folder and read the JSON file
        val assetManager = getApplication<Application>().assets
        val inputStream = assetManager.open(fileName)
        return inputStream.bufferedReader().use { it.readText() }
    }
}

data class MeditationResponse(
    val data: List<Meditation>
)
data class Meditation(
    val meditation_id: Int,
    val title: String,
    val category: String,
    val description: String,
    val created_at: String,
    val updated_at: String,
    val duration: String,
    val media_type: String,
    val content_url: String,
    val user_id: Int,
    val user_name: String,
    val user_email: String,
    val image_url: String,
    val rating:Float,
    val vocal:String,
    val sessiontime:String
)
