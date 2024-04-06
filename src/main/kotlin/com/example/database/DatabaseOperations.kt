package com.example.database

import com.example.database.model.Events
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DatabaseOperations {
    suspend fun addEvent(calendarEventId: String, documentId: String) {
        withContext(Dispatchers.IO) {
            transaction {
                Events.insert {
                    it[Events.calendarEventId] = calendarEventId
                    it[Events.documentId] = documentId
                }
            }
        }
    }

    suspend fun getAllEvents() {
        withContext(Dispatchers.IO) {
            transaction {
                Events.selectAll().forEach {
                    println("Event ID: ${it[Events.id]}, Calendar Event ID: ${it[Events.calendarEventId]}, Document ID: ${it[Events.documentId]}")
                }
            }
        }
    }
}