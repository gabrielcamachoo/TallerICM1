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

@Composable
fun DetailScreen(user: User) {
    val context = LocalContext.current

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
                    val intent = Intent(Intent.ACTION_DIAL).apply {
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
