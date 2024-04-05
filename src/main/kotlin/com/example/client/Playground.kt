package com.example.client

import com.example.AppEnvConfig.bsaleAccessToken
import com.example.AppEnvConfig.bsaleApiUrl
import com.example.configs.GoogleCalendarConfig
import com.example.model.CalendarUiData
import com.example.model.WebhookData
import com.example.model.response.ClientAttributesResponse
import com.example.model.response.ClientResponse
import com.example.model.response.DocumentDetailsResponse
import com.example.model.response.DocumentResponse
import com.example.model.response.ItemDetailUI
import com.example.model.response.ProductResponse
import com.example.model.response.VariantResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

val httpClient = HttpClient(CIO)
val json = Json { ignoreUnknownKeys = true }

suspend fun fetchMultipleApisTest(data: WebhookData, calendarService: GoogleCalendarConfig?) {
    val headers = mapOf("access_token" to bsaleAccessToken)

    coroutineScope {
        val documentResponse = async { fetchAndDecode<DocumentResponse>(bsaleApiUrl + data.resource, headers) }
        val clientResponse = async { fetchAndDecode<ClientResponse>(documentResponse.await().client.href, headers) }
        val clientAttributesResponse = async { fetchAndDecode<ClientAttributesResponse>(clientResponse.await().attributes.href, headers) }
        val documentDetailsResponse = async { fetchAndDecode<DocumentDetailsResponse>(documentResponse.await().details.href, headers) }

        val itemsInDocumentUI = documentDetailsResponse.await().items.map { item ->
            async {
                val variantResponse = fetchAndDecode<VariantResponse>(item.variant.href, headers)
                val productResponse = fetchAndDecode<ProductResponse>(variantResponse.product.href, headers)
                ItemDetailUI(quantity = item.quantity.toInt().toString(), name = productResponse.name)
            }
        }.awaitAll()

        val calendarUiData = CalendarUiData(
            clientName = "${clientResponse.await().firstName} ${clientResponse.await().lastName}",
            address = documentResponse.await().address,
            district = documentResponse.await().district,
            addressReference = clientAttributesResponse.await().items.firstOrNull { it.name == "Referencia" }?.value ?: "",
            serviceItems = formatItemList(itemsInDocumentUI),
            phoneNumber = clientResponse.await().phone,
            email = clientResponse.await().email,
            totalAmount = documentResponse.await().totalAmount.toString(),
            emissionDate = documentResponse.await().emissionDate
        )

        println(calendarUiData)

        calendarService?.createCustomEvent(calendarUiData)
    }
}

private suspend inline fun <reified T> fetchAndDecode(url: String, headers: Map<String, String>): T = withContext(
    Dispatchers.IO) {
    val response = httpClient.get(url) {
        headers.forEach { (key, value) ->
            this.headers.append(key, value)
        }
    }.bodyAsText()

    json.decodeFromString(response)
}

private fun formatItemList(itemList: List<ItemDetailUI>): String =
    itemList.joinToString(separator = "\n") { "${it.quantity} ${it.name}" }