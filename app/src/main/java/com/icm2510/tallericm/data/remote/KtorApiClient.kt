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

/**
 * Cliente HTTP configurado con Ktor para consumir la API de usuarios.
 *
 * Base URL: https://dummyjson.com/
 */
object KtorApiClient {
    /* Cliente HTTP con configuración:
        - OkHttp como motor de red.
        - ContentNegotiation para serializar/deserializar JSON.
        - Logging para registrar peticiones y respuestas.
        - DefaultRequest para aplicar la URL base a todos los requests. */
    private val client = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            // Ignora campos desconocidos en la respuesta JSON.
            json(Json { ignoreUnknownKeys = true })
        }
        install(Logging) { level = LogLevel.INFO }
        install(DefaultRequest) {
            url("https://dummyjson.com/")  // Base URL
        }
    }

    /** Trae una página de usuarios
     *
     * @param skip Número de registros a saltar (paginación).
     * @param limit Cantidad máxima de usuarios a devolver en una sola petición.
     * @return Un objeto [UsersResponse] que contiene la lista de usuarios y datos de paginación.
     */
    private suspend fun getUsersPage(skip: Int, limit: Int): UsersResponse =
        client.get("users") {
            parameter("skip", skip)
            parameter("limit", limit) // Límite por petición
        }.body()

    /** Trae TODOS los usuarios (sin límite)
     *
     * Internamente hace varias peticiones usando la paginación hasta obtener el total.
     *
     * @return Lista completa de usuarios.
     * */
    suspend fun getAllUsers(): List<User> {
        val allUsers = mutableListOf<User>() // acumulador de usuarios
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

        return allUsers // lista completa de usuarios
    }
}
