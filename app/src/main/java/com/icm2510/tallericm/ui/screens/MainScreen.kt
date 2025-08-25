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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    users: List<User>,
    onUserClick: (User) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        stickyHeader {
            Surface(
                color = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Text(
                    text = "Total usuarios: ${users.size}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 14.dp)
                )
            }
        }

        items(users, key = { it.id }) { u ->
            UserListItem(user = u) { onUserClick(u) }
        }

        item { Spacer(Modifier.height(12.dp)) }
    }
}
