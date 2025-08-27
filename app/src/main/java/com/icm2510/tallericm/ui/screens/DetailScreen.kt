package com.icm2510.tallericm.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.icm2510.tallericm.data.model.User
import androidx.compose.foundation.clickable

/**
 * Pantalla de detalle que muestra la información completa de un usuario.
 *
 * @param user Objeto [User] con los datos del usuario seleccionado.
 */
@Composable
fun DetailScreen(user: User) {
    val context = LocalContext.current

    // Contenedor principal de la pantalla
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Card con la foto del usuario
            Surface(
                tonalElevation = 1.dp,
                shadowElevation = 1.dp,
                color = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    AsyncImage(
                        model = user.image,
                        contentDescription = "${user.firstName} ${user.lastName}",
                        modifier = Modifier.size(180.dp)
                    )
                }
            }

            // Nombre completo del usuario
            Text(
                text = "${user.firstName} ${user.lastName}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp)
            )

            // Información adicional del usuario
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                Info("Empresa", user.company.name)
                Info(
                    label = "Teléfono",
                    value = user.phone,
                    isLink = true
                ) {
                    // Acción: abrir marcador con el número de teléfono
                    val intent = Intent(Intent.ACTION_DIAL /*abre app de llamadas*/).apply {
                        data = Uri.parse("tel:${user.phone}")
                    }
                    context.startActivity(intent)
                }
                Info("Email", user.email)
                Info("Edad", user.age.toString())
                Info("Género", user.gender)
                Info("Altura", "${user.height}")
                Info("Peso", "${user.weight}")
                Info("Universidad", user.university)
            }
        }
    }
}

/**
 * Composable auxiliar para mostrar una etiqueta + valor.
 *
 * @param label Texto de la etiqueta.
 * @param value Valor a mostrar.
 * @param isLink Indica si debe mostrarse como enlace (color distinto).
 * @param onClick Acción a ejecutar si el texto es clickeable (ej: abrir llamada).
 */
@Composable
private fun Info(
    label: String,
    value: String,
    isLink: Boolean = false,
    onClick: (() -> Unit)? = null
) {
    val text = "$label: $value"
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        color = if (isLink) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .padding(vertical = 2.dp)
            .then(if (onClick != null) Modifier.clickable { onClick() } else Modifier)
    )
}
