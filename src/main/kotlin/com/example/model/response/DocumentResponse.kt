package com.example.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DocumentResponse(
    val href: String,
    val id: Int,
    val emissionDate: Long,
    val expirationDate: Long,
    val generationDate: Long,
    val number: Int,
    val serialNumber: String,
    val trackingNumber: String,
    val totalAmount: Double,
    val netAmount: Double,
    val taxAmount: Double,
    val exemptAmount: Double,
    val notExemptAmount: Double,
    val exportTotalAmount: Double,
    val exportNetAmount: Double,
    val exportTaxAmount: Double,
    val exportExemptAmount: Double,
    val commissionRate: Double,
    val commissionNetAmount: Double,
    val commissionTaxAmount: Double,
    val commissionTotalAmount: Double,
    val percentageTaxWithheld: Double,
    val purchaseTaxAmount: Double,
    val purchaseTotalAmount: Double,
    val address: String,
    val district: String,
    val city: String,
    val stamp: String? = null,
    val urlPublicView: String,
    val urlPdf: String,
    val urlPublicViewOriginal: String,
    val urlPdfOriginal: String,
    val token: String,
    val state: Int,
    val commercialState: Int,
    val urlXml: String?, // Se desconoce el tipo de dato, puede ser String o null
    val salesId: Int?, // Se desconoce el tipo de dato, puede ser Int o null
    val informed: Int,
    val responseMsg: String?, // Se desconoce el tipo de dato, puede ser String o null
    @SerialName("document_type") val documentType: DocumentType,
    val client: Client,
    val office: Office,
    val user: User,
    val coin: Coin,
    val references: Reference,
    @SerialName("document_taxes") val documentTaxes: DocumentTaxes,
    val details: Detail,
    val sellers: Seller,
    val attributes: Attribute
)

@Serializable
data class DocumentType(
    val href: String, val id: String
)

@Serializable
data class Client(
    val href: String, val id: String
)

@Serializable
data class Office(
    val href: String, val id: String
)

@Serializable
data class User(
    val href: String, val id: String
)

@Serializable
data class Coin(
    val href: String, val id: String
)

@Serializable
data class Reference(
    val href: String
)

@Serializable
data class DocumentTaxes(
    val href: String
)

@Serializable
data class Detail(
    val href: String
)

@Serializable
data class Seller(
    val href: String
)

@Serializable
data class Attribute(
    val href: String
)
