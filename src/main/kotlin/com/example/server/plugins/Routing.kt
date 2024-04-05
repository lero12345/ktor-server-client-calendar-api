package com.example.server.plugins

import com.example.AppEnvConfig.clientIdOAuth
import com.example.AppEnvConfig.clientSecretOAuth
import com.example.AppEnvConfig.deployServerUrl
import com.example.client.fetchMultipleApisTest
import com.example.configs.GoogleCalendarConfig
import com.example.configs.configuration
import com.example.model.WebhookData
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.calendar.CalendarScopes
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json


private const val OAUTH_2_CALLBACK = "oauth2callback"

fun Application.configureRouting() {

    val calendarConfig = GoogleCalendarConfig()

    routing {
        get("/") {
            call.respondRedirect("/auth", permanent = false)
        }

        post("/webhook") {
            val body = call.receiveText()
            println("Webhook recibido: $body")
            val webhookData = Json.decodeFromString<WebhookData>(body)
            call.respond(HttpStatusCode.OK)
            runBlocking { fetchMultipleApisTest(webhookData, calendarConfig) }
        }
        get("/auth") {
            val url = GoogleAuthorizationCodeRequestUrl(
                clientIdOAuth,
                "${deployServerUrl}$OAUTH_2_CALLBACK",
                setOf(CalendarScopes.CALENDAR)
            ).build()
            call.respondRedirect(url)
        }

        get("/$OAUTH_2_CALLBACK") {
            val code = call.request.queryParameters["code"]
            if (code != null) {

                val flow = GoogleAuthorizationCodeFlow.Builder(
                    NetHttpTransport(), GsonFactory.getDefaultInstance(),
                    clientIdOAuth, clientSecretOAuth,
                    setOf(CalendarScopes.CALENDAR)
                ).setAccessType("offline").build()

                val tokenResponse: GoogleTokenResponse = flow.newTokenRequest(code)
                    .setRedirectUri("${deployServerUrl}$OAUTH_2_CALLBACK").execute()

                calendarConfig.initializeCalendarServiceByToken(tokenResponse.accessToken)

                call.respondText("Authenticated")

            } else {
                call.respond(HttpStatusCode.BadRequest, "No se encontró el código de autorización")
            }
        }
    }
}
