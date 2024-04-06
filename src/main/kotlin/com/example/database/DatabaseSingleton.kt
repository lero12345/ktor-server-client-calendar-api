package com.example.database

import com.example.database.model.Events
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    fun init() {
        val database = Database.connect(
            "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver"
        )
        transaction(database) {
            SchemaUtils.create(Events)
        }
    }
}