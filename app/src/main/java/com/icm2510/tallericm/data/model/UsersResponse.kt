package com.icm2510.tallericm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class UsersResponse(
    val users: List<User> = emptyList(),
    val total: Int = 0,
    val skip: Int = 0,
    val limit: Int = 0,
)