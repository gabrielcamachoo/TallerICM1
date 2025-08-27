package com.icm2510.tallericm.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.icm2510.tallericm.data.model.User

/**
 * Componente que representa un ítem de la lista de usuarios.
 *
 * Muestra:
 *  - Foto del usuario (imagen cargada con Coil).
 *  - Nombre y apellido del usuario.
 *  - Nombre de la compañía asociada.
 *  - Ícono de navegación (ChevronRight).
 *
 * @param user Objeto de tipo [User] que contiene los datos a mostrar.
 * @param onClick Acción a ejecutar cuando el ítem es seleccionado.
 */
@Composable
fun UserListItem(
    user: User,
    onClick: () -> Unit
) {
    // Card que envuelve_todo el item, con sombra ligera y color según el tema
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        // Fila principal del ítem (imagen, texto y flecha a la derecha)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen circular del usuario cargada desde URL
            AsyncImage(
                model = user.image,
                contentDescription = "${user.firstName} ${user.lastName}",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)  // Corta la imagen en forma circular
                    .background(Color(0xFFEDE7F6)) // Fondo gris
            )

            Spacer(Modifier.width(12.dp))

            // Columna con nombre + empresa
            Column(Modifier.weight(1f)) {
                Text(
                    text = "${user.firstName} ${user.lastName}",// Nombre completo
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = user.company.name, // Empresa a la que pertenece
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            // Ícono a la derecha (flechita)
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        }
    }
}
