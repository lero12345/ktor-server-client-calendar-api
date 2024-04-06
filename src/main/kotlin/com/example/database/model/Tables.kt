package com.example.database.model

import org.jetbrains.exposed.sql.Table

object Events : Table() {
    val id = uuid("id").autoGenerate()
    val calendarEventId = varchar("calendarEventId", 50)
    val documentId = varchar("documentId", length = 50)

    override val primaryKey = PrimaryKey(id)
}