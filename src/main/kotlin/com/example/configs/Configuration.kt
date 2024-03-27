package com.example.configs

import java.io.File
import java.util.*

data class Configuration(
    val credentialsPath: String = "credentials.json",
    val tokensPath: String = "tokens",
    val author: String,
    val fileTags: String,
    val category: String,
    val email: String,
    val calendarId: String,
) {
    constructor(properties: Properties) : this(
        author = properties["author"].toString(),
        fileTags = properties["fileTags"].toString(),
        category = properties["category"].toString(),
        email = properties["email"].toString(),
        credentialsPath = properties["credentialsPath"].toString(),
        tokensPath = properties["tokensPath"].toString(),
        calendarId = properties["calendarId"].toString()
    )
}

fun configuration(): Configuration {
    val properties = Properties()
    properties.load(
        File(
            System.getProperty("configuration.properties") ?: "configuration.properties"
        ).inputStream()
    )
    return Configuration(properties)
}