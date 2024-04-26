package com.dschonfeld.kmp

import com.dschonfeld.kmp.models.Object
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpRedirect
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiClient {

    private var baseUrl: String = "https://api.restful-api.dev/"

    private var httpClient: HttpClient? = null

    init {
        createHttpClient()
    }

    private fun createHttpClient() {
        httpClient = HttpClient {
            install(HttpRedirect) {
                checkHttpMethod = false
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    useAlternativeNames = false
                })
            }
        }
    }

    suspend fun getObjects(): List<Object>? {
        try {
            httpClient?.get(baseUrl + "objects").let {
                return it?.body<List<Object>>()
            }
        } catch (e: Exception) {
            println("ERROR: ${e.message}")
            return null
        }
    }
}