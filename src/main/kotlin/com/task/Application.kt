package com.task

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database


fun initDatabase() {
    val config = HikariConfig("db.properties")
    val dataSource = HikariDataSource(config)
    Flyway.configure().dataSource(dataSource).load().apply { migrate() }
    Database.connect(dataSource)
}

fun main() {
    initDatabase()
    embeddedServer(Netty, port = 8080, module = Application::module).start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
            prettyPrint = true
        })
    }
    userRouting()

}