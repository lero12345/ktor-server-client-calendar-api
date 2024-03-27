package com.example.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ClientResponse(
    val href: String,
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val code: String,
    val phone: String,
    val company: String,
    val note: String,
    val facebook: String,
    val twitter: String,
    val hasCredit: Int,
    val maxCredit: Double?, // Puede ser Double o null
    val state: Int,
    val activity: String,
    val city: String,
    val commerciallyBlocked: Int,
    val district: String,
    val address: String,
    val companyOrPerson: Int,
    val accumulatePoints: Int,
    val points: Double,
    val pointsUpdated: String,
    val sendDte: Int,
    val isForeigner: Int,
    val prestashopClienId: Int,
    val createdAt: Long,
    val updatedAt: Long,
    val contacts: Contact,
    val attributes: Attribute,
    val addresses: Address
)

@Serializable
data class Contact(
    val href: String
)

@Serializable
data class Address(
    val href: String
)

