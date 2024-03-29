package com.example.configs

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
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
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader

class GoogleCalendarConfig {

    lateinit var calendarService: Calendar
    private lateinit var calendarId: String

    private fun fetchCredentials(
        configuration: Configuration,
        jsonFactory: GsonFactory,
        transport: NetHttpTransport
    ): Credential {
        val tokensPath = File(configuration.tokensPath)
        val clientSecrets = GoogleClientSecrets.load(jsonFactory, InputStreamReader(
            this::class.java.classLoader.getResourceAsStream(Companion.CREDENTIALS_RESOURCE)
                ?: throw FileNotFoundException("Resource not found: credentials.json")
        ))
        val scopes = listOf(CalendarScopes.CALENDAR)
        val flow =
            GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(
                    FileDataStoreFactory(tokensPath)
                ).setAccessType("offline").build()
        val receiver = LocalServerReceiver.Builder().setPort(8080).build()
        return AuthorizationCodeInstalledApp(flow, receiver).authorize("user")
    }

    fun initializeCalendarService(configuration: Configuration) {
        val jsonFactory = GsonFactory.getDefaultInstance()
        val transport = GoogleNetHttpTransport.newTrustedTransport()
        val credentials = fetchCredentials(configuration, jsonFactory, transport)
        calendarId = configuration.calendarId
        calendarService = Calendar.Builder(transport, jsonFactory, credentials)
            .setApplicationName("calendar reader").build()
    }

    fun fetchEvents(): Events {
        val now = DateTime(System.currentTimeMillis())
        return calendarService.events().list(calendarId).setMaxResults(100).setTimeMin(now)
            .setOrderBy("startTime").setSingleEvents(true).execute()
    }

    fun createSampleEvent() {
        val event = Event()
        event.summary = "Evento de Ejemplo"
        event.location = "800 Howard St., San Francisco, CA 94103"
        event.description = "Descripción del evento de ejemplo."

        // Configurar la hora de inicio del evento
        val startDateTime =
            DateTime("2024-03-27T09:00:00-07:00") // Formato: "AAAA-MM-DDTHH:mm:ss-07:00" para PST
        val start = EventDateTime()
        start.dateTime = startDateTime
        event.start = start

        // Configurar la hora de finalización del evento
        val endDateTime = DateTime("2024-03-27T17:00:00-07:00")
        val end = EventDateTime()
        end.dateTime = endDateTime
        event.end = end

        val createdEvent = calendarService.events().insert(calendarId, event).execute()

        // Imprimir detalles del evento creado
        println("Evento creado: ${createdEvent.htmlLink}")
    }

    companion object {
        const val CREDENTIALS_RESOURCE = "credentials.json"
    }

}