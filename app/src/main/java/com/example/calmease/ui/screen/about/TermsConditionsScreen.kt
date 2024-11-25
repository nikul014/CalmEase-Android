package com.example.calmease.ui.screen.about


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsConditionsScreen() {

    val scrollState = rememberScrollState()


    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp, horizontal = 36.dp)
                .verticalScroll(scrollState), // Make the column scrollable
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Terms and Conditions",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Wrap the Terms and Conditions content in a clickable Card with rounded corners
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp,) // Added horizontal padding for better spacing
                   ,
                shape = RoundedCornerShape(16.dp), // Rounded corners for the card
                colors = CardDefaults.cardColors(containerColor = Color.White) // White background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "1. Introduction\n" +
                                "These Terms and Conditions govern the use of the CalmEase app. By accessing or using the app, you agree to comply with these terms.\n\n" +
                                "2. Usage License\n" +
                                "We grant you a limited, non-exclusive, non-transferable license to use the app for personal, non-commercial purposes.\n\n" +
                                "3. Intellectual Property\n" +
                                "All content and materials available on the CalmEase app, including text, images, graphics, and logos, are owned by CalmEase or its licensors.\n\n" +
                                "4. User Conduct\n" +
                                "You agree not to misuse the app, including but not limited to interfering with the app’s functionality or using it for unlawful activities.\n\n" +
                                "5. Privacy\n" +
                                "Your use of CalmEase is also governed by our Privacy Policy, which can be accessed separately.\n\n" +
                                "6. Limitation of Liability\n" +
                                "We are not liable for any damages resulting from your use of the app, including any loss of data or mental health issues.\n\n" +
                                "7. Changes to Terms\n" +
                                "We may update these Terms and Conditions from time to time. Any changes will be reflected in this section.\n\n" +
                                "8. Contact\n" +
                                "If you have any questions about these Terms, please contact us at support@calmease.com.",
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
