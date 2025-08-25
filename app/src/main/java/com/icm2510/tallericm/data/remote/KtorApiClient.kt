// com/icm2510/tallericm/data/remote/KtorApiClient.kt
package com.icm2510.tallericm.data.remote

import com.icm2510.tallericm.data.model.User
import com.icm2510.tallericm.data.model.UsersResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.call.body
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object KtorApiClient {
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) { level = LogLevel.INFO }
        install(DefaultRequest) {
            url("https://dummyjson.com/")
        }
    }

    /** Trae una página de usuarios */
    private suspend fun getUsersPage(skip: Int, limit: Int): UsersResponse =
        client.get("users") {
            parameter("skip", skip)
            parameter("limit", limit)
        }.body()

    /** Trae TODOS los usuarios (sin límite) */
    suspend fun getAllUsers(): List<User> {
        val allUsers = mutableListOf<User>()
        var skip = 0
        val limit = 208 // máximo permitido por request

        // primera petición
        val first = getUsersPage(skip, limit)
        allUsers.addAll(first.users)

        // si hay más, seguir pidiendo
        var total = first.total
        skip += limit
        while (skip < total) {
            val page = getUsersPage(skip, limit)
            allUsers.addAll(page.users)
            skip += limit
        }

        return allUsers
    }
}
