package com.example.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WebhookData(
    val cpnId: Int,
    val resource: String,
    val resourceId: String,
    val topic: String,
    val action: String,
    val officeId: String
)
