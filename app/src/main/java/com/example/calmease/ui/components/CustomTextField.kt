package com.example.calmease.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.calmease.ui.theme.CalmBackground


@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIconId: ImageVector?, // Optional parameter for leading icon
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,

    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null
) {

    Column(modifier = Modifier.padding(0.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = label, style = TextStyle(color = Color.Gray)) },
            modifier = modifier
                .fillMaxWidth()
                .background(CalmBackground, shape = RoundedCornerShape(8.dp))
                .clickable(enabled = onClick != null) { onClick?.invoke() },

            readOnly = readOnly,
            leadingIcon = {
                if (leadingIconId != null) {
                    Icon(
                        imageVector =leadingIconId,
                        contentDescription = null,
                        modifier = Modifier.padding(start = 8.dp),
                        tint= MaterialTheme.colorScheme.primary
                    )
                }
            },
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = CalmBackground,
                focusedContainerColor =CalmBackground,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            textStyle = TextStyle(color = Color.Black),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
            visualTransformation = visualTransformation,
            singleLine = true
        )
    }
}
