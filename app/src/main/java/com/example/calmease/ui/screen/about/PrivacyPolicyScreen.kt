package com.example.calmease.ui.screen.about

import androidx.compose.runtime.Composable

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberImagePainter
import com.example.calmease.R
import com.example.calmease.viewmodel.GoodMemoriesViewModel
import com.google.gson.Gson

@Composable
fun PrivacyPolicyScreen() {

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 36.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Privacy Policy",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "1. Introduction\n" +
                                "This Privacy Policy outlines how CalmEase collects, uses, and protects your personal information. By using the app, you agree to the terms outlined in this policy.\n\n" +
                                "2. Information Collection\n" +
                                "We collect personal information such as your name, email address, and usage data to provide and improve our services.\n\n" +
                                "3. Use of Information\n" +
                                "We use your personal information to provide personalized services, improve user experience, and communicate with you about app updates and offers.\n\n" +
                                "4. Data Security\n" +
                                "We implement security measures to protect your personal information from unauthorized access, alteration, or destruction.\n\n" +
                                "5. Sharing of Information\n" +
                                "We do not share your personal information with third parties unless required by law or with your consent.\n\n" +
                                "6. Cookies\n" +
                                "We use cookies to enhance the user experience and analyze app usage. You can manage your cookie preferences through your device settings.\n\n" +
                                "7. Data Retention\n" +
                                "We retain your personal information for as long as necessary to fulfill the purposes outlined in this policy.\n\n" +
                                "8. User Rights\n" +
                                "You have the right to access, correct, or delete your personal information. You can also opt-out of receiving marketing communications.\n\n" +
                                "9. Changes to Privacy Policy\n" +
                                "We may update this Privacy Policy from time to time. Any changes will be posted on this page, and the date of the last update will be provided.\n\n" +
                                "10. Contact\n" +
                                "If you have any questions or concerns about this Privacy Policy, please contact us at support@calmease.com.",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "© 2024 CalmEase. All rights reserved.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = "The use of this app is subject to the terms outlined above. By using this app, you acknowledge and agree to comply with these terms.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 4.dp)
            )
        }
    }
}
