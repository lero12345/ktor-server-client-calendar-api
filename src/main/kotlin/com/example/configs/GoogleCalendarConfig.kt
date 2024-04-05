package com.example.configs

import com.example.model.CalendarUiData
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.auth.oauth2.TokenResponse
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.client.util.store.FileDataStoreFactory
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.CalendarScopes
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import com.google.api.services.calendar.model.Events
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader

class GoogleCalendarConfig(val calendarId: String? = null) {

    lateinit var calendarService: Calendar

    fun createSampleEvent() {
        val event = Event()
        event.summary = "Evento de Ejemplo"
        event.location = "800 Howard St., San Francisco, CA 94103"
        event.description = "Descripción del evento de ejemplo."

        // Configurar la hora de inicio del evento
        val startDateTime =
            DateTime("2024-04-01T09:00:00-07:00") // Formato: "AAAA-MM-DDTHH:mm:ss-07:00" para PST
        val start = EventDateTime()
        start.dateTime = startDateTime
        event.start = start

        // Configurar la hora de finalización del evento
        val endDateTime = DateTime("2024-04-01T17:00:00-07:00")
        val end = EventDateTime()
        end.dateTime = endDateTime
        event.end = end

        val createdEvent = calendarService.events().insert("primary", event).execute()

        // Imprimir detalles del evento creado
        println("Evento creado: ${createdEvent.htmlLink}")
    }

    fun createCustomEvent(calendarData: CalendarUiData) {



    }

    suspend fun initializeCalendarServiceByToken(accessTokenValue: String) {
        val transport = NetHttpTransport()
        val jsonFactory = GsonFactory.getDefaultInstance()

        // Crea las credenciales de Google utilizando el token de acceso
        val accessToken = AccessToken(accessTokenValue, null) // Puedes especificar la fecha de expiración si la conoces
        val credentials = GoogleCredentials.create(accessToken)

        // Adapta las credenciales de Google para su uso en Calendar.Builder
        val requestInitializer = HttpCredentialsAdapter(credentials)

        // Construir el cliente de la API de Google Calendar
        calendarService = Calendar.Builder(transport, jsonFactory, requestInitializer)
            .setApplicationName("Nombre de tu Aplicación")
            .build()
    }
}