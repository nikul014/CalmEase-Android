package com.example.calmease.ui.components
//
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//
//@Composable
//fun CustomTextField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    label: String,
//    modifier: Modifier = Modifier
//) {
//    OutlinedTextField(
//        value = value,
//        onValueChange = onValueChange,
//        label = { Text(text = label) },
//        modifier = modifier.fillMaxWidth()
//    )
//}
//
//
//package com.example.calmease.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            modifier = Modifier.padding(bottom = 8.dp) // Space between label and TextField
        )

        // TextField with rounded corners, white background, leading icon, and vertical line
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
