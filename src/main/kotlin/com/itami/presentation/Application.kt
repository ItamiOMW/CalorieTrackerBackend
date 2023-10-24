package com.itami.presentation

import com.itami.data.database.DatabaseFactory
import com.itami.presentation.plugins.*
import io.ktor.server.application.*

const val API_VERSION = 1

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    DatabaseFactory.init(environment.config)

    configureKoin()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRouting()
}
