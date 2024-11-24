package com.example.calmease.ui.screen.meditation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.calmease.ui.theme.CalmPrimaryDark

@Composable
fun RatingSlider(rating: Float, onRatingChanged: (Float) -> Unit) {
    Column {
        Text(text = "Ratings: ${"%.1f".format(rating)}+")
        Slider(
            value = rating,
            onValueChange = { onRatingChanged(it) },
            valueRange = 1f..5f,
            steps = 7,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                activeTrackColor = CalmPrimaryDark,
                inactiveTrackColor = Color.Gray,
                disabledThumbColor = Color.LightGray,
                disabledActiveTrackColor = Color.LightGray,
                disabledInactiveTrackColor = Color.LightGray
            )
        )
    }
}