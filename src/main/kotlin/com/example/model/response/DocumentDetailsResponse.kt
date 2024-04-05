package com.example.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class DocumentDetailsResponse(
    @SerializedName("href") val href: String,
    @SerializedName("count") val count: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("items") val items: List<DocumentDetailItem>
)

@Serializable
data class DocumentDetailItem(
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: Int,
    @SerializedName("lineNumber") val lineNumber: Int,
    @SerializedName("quantity") val quantity: Double,
    @SerializedName("netUnitValue") val netUnitValue: Double,
    @SerializedName("netUnitValueRaw") val netUnitValueRaw: Double,
    @SerializedName("totalUnitValue") val totalUnitValue: Double,
    @SerializedName("netAmount") val netAmount: Double,
    @SerializedName("taxAmount") val taxAmount: Double,
    @SerializedName("totalAmount") val totalAmount: Double,
    @SerializedName("netDiscount") val netDiscount: Double,
    @SerializedName("totalDiscount") val totalDiscount: Double,
    @SerializedName("variant") val variant: Variant,
    @SerializedName("note") val note: String,
    @SerializedName("relatedDetailId") val relatedDetailId: Int
)

@Serializable
data class Variant(
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String,
    @SerializedName("code") val code: String
)
