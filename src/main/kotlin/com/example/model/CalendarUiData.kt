package com.example.model

data class CalendarUiData(
    val clientName: String = "",
    val address: String = "",
    val district: String = "",
    val addressReference: String = "",
    val serviceItems: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val totalAmount: String = "",
    val clientOrigin: String = "",
    val emissionDate: Long = 0L,
    val eventStatus: EventStatus = EventStatus.PENDING,
    val documentId: String = ""
)

enum class EventStatus { PENDING, DONE }

fun CalendarUiData.getEventDescription(): String {
    val builder = StringBuilder()

    builder.append("Nombre:\n$clientName\n\n")
    builder.append("Dirección:\n$address\n\n")
    builder.append("Referencia:\n$addressReference\n\n")
    builder.append("Servicio:\n$serviceItems\n\n")
    builder.append("Datos:\n$phoneNumber\n$email\n\n")
    builder.append("Total:\n$totalAmount\n\n")
    builder.append("Procedencia:\n$clientOrigin")

    return builder.toString()
}