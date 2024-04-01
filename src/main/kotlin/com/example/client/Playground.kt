package com.example.client

import com.example.configs.GoogleCalendarConfig
import com.example.model.CalendarUiData
import com.example.model.WebhookData
import com.example.model.response.ClientResponse
import com.example.model.response.DocumentResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.serialization.json.Json

val scope = CoroutineScope(Dispatchers.IO)


suspend fun fetchMultipleApisTest(data: WebhookData, calendarService: GoogleCalendarConfig?) {

    val baseUrl = "https://api.bsale.io/v1"
    val accessToken = "75d1e177d764778a0adc6576022dea48675f7e68"
    val headers = mapOf("access_token" to accessToken)

    scope.apply {
        val firstCall = async { fetchApiData(baseUrl + data.resource, headers) }.await()

        val documentGeneralBodyResponse =
            Json.decodeFromString<DocumentResponse>(firstCall.bodyAsText())

        val secondCall =
            async { fetchApiData(documentGeneralBodyResponse.client.href, headers) }.await()

        val clientGeneralBodyResponse =
            Json.decodeFromString<ClientResponse>(secondCall.bodyAsText())

        println(documentGeneralBodyResponse)

        val calendarUiData = CalendarUiData(
            totalAmount = documentGeneralBodyResponse.totalAmount.toString(),
            address = documentGeneralBodyResponse.address,
            clientName = "$clientGeneralBodyResponse.firstName $clientGeneralBodyResponse.lastName",
            email = clientGeneralBodyResponse.email,
            phoneNumber = clientGeneralBodyResponse.phone
            //...
            )

        calendarService?.createSampleEvent()
    }
}

suspend fun fetchApiData(url: String, headers: Map<String, String> = emptyMap()): HttpResponse =
    HttpClient(CIO).use {
        return it.get(url) {
            headers.forEach { (key, value) ->
                this.headers.append(key, value)
            }
        }
    }

