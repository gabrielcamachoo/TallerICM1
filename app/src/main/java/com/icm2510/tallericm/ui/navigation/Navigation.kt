package com.icm2510.tallericm.ui.navigation

import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.icm2510.tallericm.data.remote.KtorApiClient
import com.icm2510.tallericm.data.model.User
import com.icm2510.tallericm.ui.screens.DetailScreen
import com.icm2510.tallericm.ui.screens.MainScreen

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object Detail : Screen("detail?userId={userId}")
}

@Composable
fun NavigationStack() {
    val nav = rememberNavController()
    var users by remember { mutableStateOf<List<User>>(emptyList()) }

    // Cargar los usuarios una sola vez
    LaunchedEffect(Unit) {
        users = KtorApiClient.getAllUsers()
    }


    NavHost(navController = nav, startDestination = Screen.Main.route) {
        composable(Screen.Main.route) {
            MainScreen(users = users) { user ->
                nav.navigate("detail?userId=${user.id}")
            }
        }
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType; nullable = false }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("userId")!!
            val user = users.firstOrNull { it.id == id }
            if (user != null) DetailScreen(user = user)
        }
    }
}
