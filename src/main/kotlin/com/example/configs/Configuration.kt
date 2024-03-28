package com.example.configs

import java.io.FileNotFoundException
import java.util.Properties

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
    val inputStream =
        Configuration::class.java.classLoader.getResourceAsStream("configuration.properties")
            ?: throw FileNotFoundException("property file 'configuration.properties' not found in the classpath")

    properties.load(inputStream)
    return Configuration(properties)
}