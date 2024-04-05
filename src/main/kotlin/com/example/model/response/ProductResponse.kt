package com.example.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("classification") val classification: Int,
    @SerializedName("ledgerAccount") val ledgerAccount: String?,
    @SerializedName("costCenter") val costCenter: String?,
    @SerializedName("allowDecimal") val allowDecimal: Int,
    @SerializedName("stockControl") val stockControl: Int,
    @SerializedName("printDetailPack") val printDetailPack: Int,
    @SerializedName("state") val state: Int,
    @SerializedName("prestashopProductId") val prestashopProductId: Int,
    @SerializedName("presashopAttributeId") val presashopAttributeId: Int,
    @SerializedName("product_type") val productType: ProductType? = null,
    @SerializedName("variants") val variants: Variants,
    @SerializedName("product_taxes") val productTaxes: ProductTaxes? = null
)

@Serializable
data class ProductType(
    @SerializedName("href") val href: String,
    @SerializedName("id") val id: String
)

@Serializable
data class Variants(
    @SerializedName("href") val href: String
)

@Serializable
data class ProductTaxes(
    @SerializedName("href") val href: String
)
