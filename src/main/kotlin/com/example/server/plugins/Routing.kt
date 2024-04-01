package com.example.server.plugins

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


fun Application.configureRouting() {

//    val calendarConfig = GoogleCalendarConfig()
//    calendarConfig.initializeCalendarService(configuration())

    val localServer = "http://localhost:8080/"
    val remoteServer = "https://ktor-server-client-calendar-api.onrender.com:8080/"

    routing {
        get("/") {
            call.respondText("Hello mathias Worlddd!")
        }

        post("/webhook") {
            val body = call.receiveText()
            println("Webhook recibido: $body")
            val webhookData = Json.decodeFromString<WebhookData>(body)
            call.respond(HttpStatusCode.OK)
            runBlocking { fetchMultipleApisTest(webhookData, null) }
        }
        get("/auth") {
            val url = GoogleAuthorizationCodeRequestUrl(
                "1016868434732-871madp3npoe6m394bfnfmj8dnslo0hq.apps.googleusercontent.com",
                "${remoteServer}oauth2callback", // Asegúrate de cambiar esto por tu dominio real y endpoint en producción
                setOf(CalendarScopes.CALENDAR)
            ).build()
            call.respondRedirect(url)
        }

        get("/oauth2callback") {
            val code = call.request.queryParameters["code"]
            if (code != null) {
                val flow = GoogleAuthorizationCodeFlow.Builder(
                    NetHttpTransport(), GsonFactory.getDefaultInstance(),
                    "1016868434732-871madp3npoe6m394bfnfmj8dnslo0hq.apps.googleusercontent.com", "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",
                    setOf(CalendarScopes.CALENDAR)
                ).setAccessType("offline").build()

                val tokenResponse: GoogleTokenResponse = flow.newTokenRequest(code)
                    .setRedirectUri("${remoteServer}oauth2callback").execute()

                // Aquí deberías guardar el tokenResponse en algún lugar seguro y asociarlo con tu usuario
                call.respondText("Autenticación exitosa")
            } else {
                call.respond(HttpStatusCode.BadRequest, "No se encontró el código de autorización")
            }
        }
    }
}
