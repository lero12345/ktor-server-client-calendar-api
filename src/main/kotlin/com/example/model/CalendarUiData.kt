package com.example.model

data class CalendarUiData(
    val clientName: String = "",
    val address: String = "",
    val serviceDetails: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val totalAmount: String = "",
    val clientOrigin: String = "",
    val eventStatus: EventStatus = EventStatus.PENDING
)

enum class EventStatus { PENDING, DONE }


//Nombre
//Maria del Valle indriago
//
//Dirección
//Av el derby 575 condominio elevage, torre 2 int 402. Santiago de surco.
//
//Servicio
//4 sillas
//Mueble seccional
//Banqueta
//5 stores
//
//Datos
//915350739
//mariaindriago1603@gmail.com
//
//Total
//S/498(30 yape)
//
//Procedencia
//Ig( búsqueda)