package com.example

import com.example.database.DatabaseSingleton
import com.example.server.plugins.configureRouting
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.jetbrains.exposed.sql.Database

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module).start(wait = true)
}

fun Application.module() {
    DatabaseSingleton.init()
    configureRouting()
}

object AppEnvConfig {

    private val environment: String = System.getenv("ENV") ?: "develop"
    val deployServerUrl: String
    val bsaleApiUrl: String
    val bsaleAccessToken: String
    val clientIdOAuth: String
    val clientSecretOAuth: String

    init {
        if (environment == "release") {
            deployServerUrl = System.getenv("RELEASE_DEPLOY_SERVER_URL")
            bsaleApiUrl = System.getenv("RELEASE_BSALE_API_URL")
        } else {
            deployServerUrl = "http://localhost:8080/"
            bsaleApiUrl = "https://api.bsale.io/v1"
        }

        bsaleAccessToken = System.getenv("BSALE_TOKEN")
        clientIdOAuth = System.getenv("CLIENT_ID_OAUTH")
        clientSecretOAuth = System.getenv("CLIENT_SECRET_OAUTH")
    }
}