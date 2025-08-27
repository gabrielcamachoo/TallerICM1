package com.icm2510.tallericm.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.icm2510.tallericm.data.model.User
import com.icm2510.tallericm.ui.components.UserListItem



/**
 * Pantalla principal que muestra una lista de usuarios.
 *
 * @param users Lista de usuarios a mostrar.
 * @param onUserClick Acción a ejecutar cuando se selecciona un usuario.
 */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    users: List<User>,
    onUserClick: (User) -> Unit
) {
    // LazyColumn permite renderizar listas de manera eficiente
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        // Cabecera fija en la parte superior que muestra el total de usuarios
        stickyHeader {
            Surface(
                color = MaterialTheme.colorScheme.primary,  // Fondo con color del tema
                contentColor = MaterialTheme.colorScheme.onPrimary // Color del texto
            ) {
                Text(
                    text = "Total usuarios: ${users.size}", // Muestra la cantidad de usuarios
                    style = MaterialTheme.typography.titleLarge, // Estilo de texto grande
                    modifier = Modifier
                        .padding(horizontal = 100.dp, vertical = 25.dp) // Margen interno
                )
            }
        }

        // Renderiza cada usuario con su propio UserListItem
        items(users, key = { it.id }) { u ->
            UserListItem(user = u) { onUserClick(u) } // Al hacer click se ejecuta onUserClick
        }
        // Agrega un espacio al final de la lista
        item { Spacer(Modifier.height(12.dp)) }
    }
}
