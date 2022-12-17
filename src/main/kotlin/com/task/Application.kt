package com.task

import com.task.routing.userRouting
import com.task.storage.DatabaseFactory
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json


fun main(args: Array<String>) = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    DatabaseFactory.init()
    install(ContentNegotiation) {
        json(Json {
            isLenient = true
        })
    }
    userRouting()

}