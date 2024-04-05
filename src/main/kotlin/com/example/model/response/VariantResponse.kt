package com.example.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class VariantResponse(
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: Int,
    @SerializedName("description") val description: String,
    @SerializedName("unlimitedStock") val unlimitedStock: Int,
    @SerializedName("allowNegativeStock") val allowNegativeStock: Int,
    @SerializedName("state") val state: Int,
    @SerializedName("barCode") val barCode: String,
    @SerializedName("code") val code: String,
    @SerializedName("tributaryCode") val tributaryCode: String?,
    @SerializedName("unit") val unit: String?,
    @SerializedName("imagestionCenterCost") val imagestionCenterCost: Int,
    @SerializedName("imagestionAccount") val imagestionAccount: Int,
    @SerializedName("imagestionConceptCod") val imagestionConceptCod: Int,
    @SerializedName("imagestionProyectCod") val imagestionProyectCod: Int,
    @SerializedName("imagestionCategoryCod") val imagestionCategoryCod: Int,
    @SerializedName("imagestionProductId") val imagestionProductId: Int,
    @SerializedName("serialNumber") val serialNumber: Int,
    @SerializedName("isLot") val isLot: Int,
    @SerializedName("prestashopCombinationId") val prestashopCombinationId: Int,
    @SerializedName("prestashopValueId") val prestashopValueId: Int,
    @SerializedName("product") val product: Product,
    @SerializedName("attribute_values") val attributeValues: AttributeValues? = null,
    @SerializedName("costs") val costs: Costs
)

@Serializable
data class Product(
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String
)

@Serializable
data class AttributeValues(
    @SerializedName("href") val href: String
)

@Serializable
data class Costs(
    @SerializedName("href") val href: String
)

