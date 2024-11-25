package com.example.calmease.ui.screen.dashboard

import android.content.Intent
import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Mood
import androidx.compose.material.icons.outlined.MoreHoriz
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onPlaced
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
import com.example.calmease.ui.screen.about.AboutScreen
import com.example.calmease.ui.screen.about.PrivacyPolicyScreen
import com.example.calmease.ui.screen.about.TermsConditionsScreen
import com.example.calmease.ui.screen.article.ArticleDetailScreen
import com.example.calmease.ui.screen.article.ArticleScreen
import com.example.calmease.ui.screen.breathing.BreathingCategoriesScreen
import com.example.calmease.ui.screen.breathing.BreathingDetailScreen
import com.example.calmease.ui.screen.breathing.BreathingExercisesScreen
import com.example.calmease.ui.screen.contact.ContactScreen
import com.example.calmease.ui.screen.live_sessions.CreateSessionScreen
import com.example.calmease.ui.screen.live_sessions.LiveSessionListScreen
import com.example.calmease.ui.screen.live_sessions.SessionDetailScreen
import com.example.calmease.ui.screen.memories.CreateMemoryScreen
import com.example.calmease.ui.screen.memories.GoodMemoriesScreen
import com.example.calmease.ui.screen.memories.MemoryDetailsScreen
import com.example.calmease.ui.screen.more.MoreMenuScreen
import com.example.calmease.ui.theme.CalmBackground
import com.example.calmease.ui.theme.CalmDarkBackground
import com.example.calmease.ui.theme.CalmEaseTheme
import com.example.calmease.viewmodel.BreathingViewModel
import com.example.calmease.ui.theme.CalmPrimaryDark
import com.example.calmease.viewmodel.SessionViewModel

@Composable
fun DashboardScreen(parentNavController: NavController) {
    val navController = rememberNavController()

    val bottomNavItems = listOf("Meditation", "Breathing", "Articles", "Profile", "More")
    val selectedIndex by remember { mutableIntStateOf(0) }


    Scaffold(
        containerColor = CalmBackground,
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).background(color = CalmBackground)) {
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

                    composable("create_memory") {
                        CreateMemoryScreen(navController = navController)
                    }

                    composable("memory_detail/{memoryJson}") { backStackEntry ->
                        val memoryJson = backStackEntry.arguments?.getString("memoryJson") ?: ""
                        MemoryDetailsScreen(memoryJson = memoryJson, navController = navController)
                    }

                    composable("memories") {
                        GoodMemoriesScreen(navController = navController)
                    }

                    composable("session_list") {
                        LiveSessionListScreen(navController)
                    }
                    composable("session_detail/{sessionJson}") { backStackEntry ->
                        val sessionJson = backStackEntry.arguments?.getString("sessionJson") ?: ""
                        SessionDetailScreen(navController, sessionJson)
                    }
                    composable("create_session") {
                        CreateSessionScreen(navController)
                    }

                    composable("edit_session/{sessionJson}") { backStackEntry ->
                        val sessionJson = backStackEntry.arguments?.getString("sessionJson")
                        sessionJson?.let {
                            CreateSessionScreen(navController, existingSessionJson = it)
                        }
                    }

                    composable("articles") {
                        ArticleScreen(
                            viewModel = viewModel(),
                            navController = navController
                        )
                    }
                    composable("article_detail/{articleId}") { backStackEntry ->
                        val articleIdString = backStackEntry.arguments?.getString("articleId")
                        val articleId = articleIdString?.toIntOrNull()
                        Log.d("ArticleDetail", "Article ID: $articleId")
                        if (articleId != null) {
                            ArticleDetailScreen(articleId = articleId)
                        }
                    }

                    composable("profile") { ProfileScreen() }
                    composable("more") {
                        val context = LocalContext.current
                        MoreMenuScreen(navController = navController, context, parentNavController)
                    }
                    composable("contact") { ContactScreen(navController) }

                    composable("about_us") {
                        AboutScreen(navController = navController)
                    }
                    composable("terms_conditions") {
                        TermsConditionsScreen()
                    }
                    composable("privacy_policy") {
                        PrivacyPolicyScreen()
                    }
                }
            }
        }

}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val currentRoute = navController.currentBackStackEntryAsState()?.value?.destination?.route

    // Wrapping NavigationBar in a Box to add background and rounded corners
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                clip = false // Set to true if you want to clip the content within the shape
            )
            .background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(4.dp) // Optional padding for better spacing
    ) {
        NavigationBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent // Ensure the bar itself has a white background
        ) {
            NavigationBarItem(
                selected = currentRoute == "meditation",
                onClick = { navController.navigate("meditation") },
                label = { Text("Meditation") },
                icon = { Icon(imageVector = Icons.Outlined.SelfImprovement, contentDescription = null) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = CalmPrimaryDark,
                    indicatorColor = CalmPrimaryDark,
                    unselectedIconColor = CalmDarkBackground,
                    unselectedTextColor = CalmDarkBackground
                )
            )
            NavigationBarItem(
                selected = currentRoute == "breathing",
                onClick = { navController.navigate("breathing") },
                label = { Text("Breathing") },
                icon = { Icon(imageVector = Icons.Outlined.Favorite, contentDescription = null) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = CalmPrimaryDark,
                    indicatorColor = CalmPrimaryDark,
                    unselectedIconColor = CalmDarkBackground,
                    unselectedTextColor = CalmDarkBackground
                )
            )
            NavigationBarItem(
                selected = currentRoute == "articles",
                onClick = { navController.navigate("articles") },
                label = { Text("Articles") },
                icon = { Icon(imageVector = Icons.Outlined.Article, contentDescription = null) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = CalmPrimaryDark,
                    indicatorColor = CalmPrimaryDark,
                    unselectedIconColor = CalmDarkBackground,
                    unselectedTextColor = CalmDarkBackground
                )
            )
            NavigationBarItem(
                selected = currentRoute == "memories",
                onClick = { navController.navigate("memories") },
                label = { Text("Memories") },
                icon = { Icon(imageVector = Icons.Outlined.Mood, contentDescription = null) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = CalmPrimaryDark,
                    indicatorColor = CalmPrimaryDark,
                    unselectedIconColor = CalmDarkBackground,
                    unselectedTextColor = CalmDarkBackground
                )
            )
            NavigationBarItem(
                selected = currentRoute == "more",
                onClick = { navController.navigate("more") },
                label = { Text("More") },
                icon = { Icon(imageVector = Icons.Outlined.MoreHoriz, contentDescription = null) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = CalmPrimaryDark,
                    indicatorColor = CalmPrimaryDark,
                    unselectedIconColor = CalmDarkBackground,
                    unselectedTextColor = CalmDarkBackground
                )
            )
        }
    }
}

@Composable
fun ProfileScreen() {
    Text(text = "Profile Screen")
}