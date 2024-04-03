package com.example

import com.example.server.plugins.configureRouting
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {
    embeddedServer(
        Netty, port = 8080, host = AppEnvConfig.host, module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureRouting()
}

object AppEnvConfig {

    private val environment: String = System.getenv("ENV") ?: "develop"
    val deployServerUrl: String
    val bsaleApiUrl: String
    val clientIdOAuth: String
    val clientSecretOAuth: String
    val host: String

    init {
        if (environment == "release") {
            deployServerUrl = System.getenv("RELEASE_DEPLOY_SERVER_URL")
            bsaleApiUrl = System.getenv("RELEASE_BSALE_API_URL")
            host = deployServerUrl
        } else {
            deployServerUrl = "http://localhost:8080/"
            bsaleApiUrl = "https://api.bsale.io/v1"
            host = "0.0.0.0"
        }

        clientIdOAuth = System.getenv("CLIENT_ID_OAUTH")
        clientSecretOAuth = System.getenv("CLIENT_SECRET_OAUTH")
    }
}