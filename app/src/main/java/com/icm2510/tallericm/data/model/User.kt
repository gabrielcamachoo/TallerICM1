package com.icm2510.tallericm.data.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val image: String,
    val phone: String,
    val email: String,
    val age: Int,
    val gender: String,
    val height: Double,
    val weight: Double,
    val university: String,
    val company: Company
)

@Serializable
data class Company(
    val name: String
)