package com.task

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database


fun initDatabase() {
    val config = HikariConfig("db.properties")
    val dataSource = HikariDataSource(config)
    val flyway = Flyway.configure().dataSource(dataSource).load()
    flyway.migrate()

    Database.connect(dataSource)
}
fun main() {
    initDatabase()
}