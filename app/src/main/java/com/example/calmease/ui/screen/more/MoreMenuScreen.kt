package com.example.calmease.ui.screen.more

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calmease.ui.theme.CalmBackground
import com.example.calmease.ui.theme.Poppins
import com.example.calmease.utils.SharedPreferenceHelper

@Composable
fun MoreMenuScreen(navController: NavController, context: Context, parentNavController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MoreCard(navController, context, parentNavController)
    }
}

@Composable
fun MoreCard(navController: NavController,context: Context, parentNavController: NavController) {
    val liveSession = listOf(
        MenuItem(
            titleText = "Live Sessions",
            navigationRoute ="session_list",
            onClickEvent = { navController.navigate("session_list") }
        ),
    )

    val settings = listOf(
        MenuItem(
            titleText = "Profile",
            navigationRoute ="profile",
            onClickEvent = { navController.navigate("profile") }
        ),  MenuItem(
            titleText = "Logout",
            navigationRoute ="logout",
            onClickEvent = {     SharedPreferenceHelper.getInstance(context).clearAll()
                parentNavController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }
        ),
    )
    val info = listOf(
        MenuItem(
            titleText = "About Us",
            navigationRoute ="about_us",
            onClickEvent = { navController.navigate("about_us") }
        ), MenuItem(
            titleText = "Terms and Conditions",
            navigationRoute ="terms_conditions",
            onClickEvent = { navController.navigate("terms_conditions") }
        ),MenuItem(
            titleText = "Privacy Policy",
            navigationRoute ="privacy_policy",
            onClickEvent = { navController.navigate("privacy_policy") }
        ),
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {

        MoreCardItem(title = "Sessions",  menuItems = liveSession)

        MoreCardItem(title = "General Settings", menuItems = settings)
        Spacer(modifier = Modifier.height(8.dp))

        MoreCardItem(title = "Information",menuItems = info)
        Spacer(modifier = Modifier.height(8.dp))


    }
}

@Composable
fun MoreCardItem(title: String,  menuItems: List<MenuItem>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            ,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,

                )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = CalmBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                menuItems.forEach { menuItem ->
                    ClickableMenuItem(menuItem = menuItem)
                }
            }

        }
    }
}
@Composable
fun ClickableMenuItem(menuItem: MenuItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = { menuItem.onClickEvent() },
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = menuItem.titleText,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}

data class MenuItem(
    val titleText: String,
    val navigationRoute: String,
    val onClickEvent: () -> Unit
)
