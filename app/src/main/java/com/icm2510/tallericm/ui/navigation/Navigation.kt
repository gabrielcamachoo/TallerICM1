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


/**
 * Clase sellada que define las rutas de navegación de la app.
 * - Main: lista de usuarios
 * - Detail: detalle de un usuario específico
 */
sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object Detail : Screen("detail?userId={userId}")
}


/**
 * Composable principal que gestiona el stack de navegación.
 */
@Composable
fun NavigationStack() {
    val nav = rememberNavController()
    var users by remember { mutableStateOf<List<User>>(emptyList()) }

    // Cargar los usuarios una sola vez, se ejecuta una sola vez para cargar usuarios desde la API
    LaunchedEffect(Unit) {
        users = KtorApiClient.getAllUsers()
    }

    // Definición del NavHost con sus destinos
    NavHost(navController = nav, startDestination = Screen.Main.route) {
        // Pantalla principal con la lista de usuarios
        composable(Screen.Main.route) {
            MainScreen(users = users) { user ->
                // Navegar al detalle, pasando el ID como argumento en la ruta
                nav.navigate("detail?userId=${user.id}")
            }
        }

        // Pantalla de detalle de usuario
        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("userId") { type = NavType.IntType; nullable = false }
            )
        ) { backStackEntry ->
            // Recuperar el parámetro userId de los argumentos
            val id = backStackEntry.arguments?.getInt("userId")!!
            val user = users.firstOrNull { it.id == id }
            if (user != null) DetailScreen(user = user)
        }
    }
}
