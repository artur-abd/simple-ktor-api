package com.task.storage

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {
    fun init() {
        val config = HikariConfig("db.properties")
        val dataSource = HikariDataSource(config)
        Flyway.configure().dataSource(dataSource).load().apply { migrate() }
        Database.connect(dataSource)

    }
}