package com.example.server.plugins

import com.example.client.fetchMultipleApisTest
import com.example.configs.GoogleCalendarConfig
import com.example.configs.configuration
import com.example.model.WebhookData
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.request.receiveText
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json


fun Application.configureRouting() {

    val calendarConfig = GoogleCalendarConfig()
    calendarConfig.initializeCalendarService(configuration())

    routing {
        get("/") {
            call.respondText("Hello mathias Worlddd!")
        }

        post("/webhook") {
            val body = call.receiveText()
            println("Webhook recibido: $body")

            val webhookData = Json.decodeFromString<WebhookData>(body)

//            calendarEvents.createSampleEvent()

            call.respond(HttpStatusCode.OK)

            runBlocking { fetchMultipleApisTest(webhookData, calendarConfig) }
        }
    }
}
