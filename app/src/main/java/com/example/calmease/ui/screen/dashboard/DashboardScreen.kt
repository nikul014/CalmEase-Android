package com.example.calmease.ui.screen.dashboard

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.calmease.R
import com.example.calmease.ui.screen.meditation.MeditationDetailScreen
import com.example.calmease.ui.screen.meditation.MeditationScreen
import com.example.calmease.ui.screen.sessions.VideoActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calmease.ui.screen.Meditation.MeditationDetailScreen
import com.example.calmease.ui.screen.Meditation.MeditationScreen
import com.example.calmease.ui.screen.article.ArticleDetailScreen
import com.example.calmease.ui.screen.article.ArticleScreen
import com.example.calmease.ui.screen.breathing.BreathingCategoriesScreen
import com.example.calmease.ui.screen.breathing.BreathingDetailScreen
import com.example.calmease.ui.screen.breathing.BreathingExercisesScreen
import com.example.calmease.viewmodel.BreathingViewModel
import com.example.calmease.ui.theme.CalmPrimaryDark

@Composable
fun DashboardScreen() {
    val navController = rememberNavController()

    val bottomNavItems = listOf("Meditation", "Breathing", "Articles", "Profile", "More")
    val currentScreen = remember { mutableStateOf(bottomNavItems[0]) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            NavHost(
                navController = navController,
                startDestination = "meditation"
            ) {
                composable("meditation") {
                    MeditationScreen(viewModel = viewModel(), navController = navController)
                }
                composable("meditation_detail/{meditation_id}") { backStackEntry ->
                    val meditationIdString = backStackEntry.arguments?.getString("meditation_id")
                    val meditationId = meditationIdString?.toIntOrNull()
                    Log.d("MeditationDetail", "Meditation ID: $meditationId")
                    MeditationDetailScreen(meditationId = meditationId)
                }
                composable("breathing") {
                    val viewModel: BreathingViewModel = viewModel()
                    BreathingCategoriesScreen(viewModel = viewModel, navController = navController)
                }
                composable("breathing_exercises/{categoryId}") { backStackEntry ->
                    val categoryId = backStackEntry.arguments?.getString("categoryId")?.toIntOrNull()
                    if (categoryId != null) {
                        val viewModel: BreathingViewModel = viewModel()
                        BreathingExercisesScreen(categoryId = categoryId, navController = navController, viewModel = viewModel)
                    }
                }
                composable(
                    route = "breathing_detail/{categoryImage}/{backgroundImage}/{exerciseTiming}/{audioUrl}",
                    arguments = listOf(
                        navArgument("categoryImage") { type = NavType.StringType },
                        navArgument("backgroundImage") { type = NavType.StringType },
                        navArgument("exerciseTiming") { type = NavType.StringType },
                        navArgument("audioUrl") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val categoryImage = backStackEntry.arguments?.getString("categoryImage") ?: ""
                    val backgroundImage = backStackEntry.arguments?.getString("backgroundImage") ?: ""
                    val exerciseTiming = backStackEntry.arguments?.getString("exerciseTiming") ?: ""
                    val audioUrl = backStackEntry.arguments?.getString("audioUrl") ?: ""

                    BreathingDetailScreen(
                        categoryImage = categoryImage,
                        backgroundImage = backgroundImage,
                        exerciseTiming = exerciseTiming,
                        audioUrl = audioUrl,
                        navController = navController
                    )
                }

                composable("memories") { val context = LocalContext.current
                    LaunchedEffect(Unit) {
                        val intent = Intent(context, VideoActivity::class.java).apply {
                            putExtra("ChannelName", "2")
                            putExtra("UserRole", "publisher")
                        }
                        ContextCompat.startActivity(context, intent, null)
                    } }
                composable("articles") { ArticleScreen(viewModel = viewModel(),navController = navController) }
                composable("article_detail/{articleId}") { backStackEntry ->
                    val articleIdString = backStackEntry.arguments?.getString("articleId")
                    val articleId = articleIdString?.toIntOrNull()
                    Log.d("ArticleDetail", "Article ID: $articleId")
                    if (articleId != null) {
                        ArticleDetailScreen(articleId = articleId)
                    }
                }
                composable("profile") { ProfileScreen() }
                composable("more") { MoreScreen() }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route
    NavigationBar(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBarItem(
            selected = currentRoute == "meditation",
            onClick = { navController.navigate("meditation") },
            label = { Text("Meditation") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_meditate_name),
                    contentDescription = "Meditation Icon"
                )
            },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = CalmPrimaryDark,
                indicatorColor = CalmPrimaryDark,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = currentRoute == "breathing",
            onClick = { navController.navigate("breathing") },
            label = { Text("Breathing") },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = CalmPrimaryDark,
                indicatorColor = CalmPrimaryDark,
            )
        )

        NavigationBarItem(
            selected = currentRoute == "articles",
            onClick = { navController.navigate("articles") },
            label = { Text("Articles") },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = CalmPrimaryDark,
                indicatorColor = CalmPrimaryDark,
            )
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("memories") },
            label = { Text("Good Memories") },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("more") },
            label = { Text("More") },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) }
        )
    }
}

@Composable
fun ProfileScreen() {
    Text(text = "Profile Screen")
}
@Composable
fun MemoriesScreen(channelName: String, userRole: String) {
    val context = LocalContext.current

    Button(onClick = {
        val intent = Intent(context, VideoActivity::class.java).apply {
            putExtra("ChannelName", channelName)
            putExtra("UserRole", userRole)
        }
        ContextCompat.startActivity(context, intent, null)
    }) {
        Text("Start Video Activity")
    }
}


@Composable
fun MoreScreen() {
    Text(text = "More Screen")
}
