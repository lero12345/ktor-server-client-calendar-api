package com.example.database

import com.example.AppEnvConfig.dbHostAndName
import com.example.AppEnvConfig.dbPassWord
import com.example.AppEnvConfig.dbUserName
import com.example.database.model.Events
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    fun init() {

        val database = Database.connect(
            url = "jdbc:postgresql://${dbHostAndName}",
            driver = "org.postgresql.Driver",
            user = dbUserName,
            password = dbPassWord
        )
        transaction(database) {
            SchemaUtils.create(Events)
        }
    }
}