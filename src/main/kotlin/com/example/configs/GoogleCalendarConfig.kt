package com.example.configs

import com.example.database.DatabaseOperations
import com.example.model.CalendarUiData
import com.example.model.getEventDescription
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.client.util.DateTime
import com.google.api.services.calendar.Calendar
import com.google.api.services.calendar.model.Event
import com.google.api.services.calendar.model.EventDateTime
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.AccessToken
import com.google.auth.oauth2.GoogleCredentials
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

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

    suspend fun createCustomEvent(calendarData: CalendarUiData) {
        val event = Event()
        event.summary = "${calendarData.clientName} - ${calendarData.district}"
        event.description = calendarData.getEventDescription()

        // Convertir el tiempo Unix a LocalDateTime en la zona horaria de Perú
        val eventDate = LocalDateTime.ofInstant(Instant.ofEpochSecond(calendarData.emissionDate), ZoneId.of("America/Lima"))

        val startDateTime = eventDate.withHour(3).withMinute(0)
        val endDateTime = eventDate.withHour(5).withMinute(0)

        // Formatear las fechas al formato RFC3339 sin convertirlas a UTC
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
        val startDateTimeStr = startDateTime.atZone(ZoneId.of("America/Lima")).format(formatter)
        val endDateTimeStr = endDateTime.atZone(ZoneId.of("America/Lima")).format(formatter)

        val start = EventDateTime().setDateTime(DateTime(startDateTimeStr))
        event.start = start

        val end = EventDateTime().setDateTime(DateTime(endDateTimeStr))
        event.end = end

        // Insertar el evento en el calendario
        val createdEvent = calendarService.events().insert("primary", event).execute()

        DatabaseOperations().addEvent(calendarEventId = createdEvent.id, documentId = calendarData.documentId)
        println("Evento y documento asociado guardado en table")
        DatabaseOperations().getAllEvents()

        println("Evento creado: ${createdEvent.htmlLink}")
    }

    suspend fun initializeCalendarServiceByToken(accessTokenValue: String) {
        val transport = NetHttpTransport()
        val jsonFactory = GsonFactory.getDefaultInstance()
        val accessToken = AccessToken(accessTokenValue, null)
        val credentials = GoogleCredentials.create(accessToken)
        val requestInitializer = HttpCredentialsAdapter(credentials)

        calendarService = Calendar.Builder(transport, jsonFactory, requestInitializer)
            .setApplicationName("Nombre de tu Aplicación")
            .build()
    }
}