package com.example.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ClientAttributesResponse(
    @SerializedName("href") val href: String,
    @SerializedName("count") val count: Int,
    @SerializedName("limit") val limit: Int,
    @SerializedName("offset") val offset: Int,
    @SerializedName("items") val items: List<ClientAttributeItem>
)

@Serializable
data class ClientAttributeItem(
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("value") val value: String
)

data class ItemDetailUI(
    val quantity: String,
    val name: String
)
