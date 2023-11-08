package com.itami

import com.itami.data.database.DatabaseFactory
import com.itami.plugins.*
import io.ktor.server.application.*

const val API_VERSION = "/api/v1"

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    val config = environment.config
    DatabaseFactory.init(config)

    configureKoin()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureResources()
    configureRateLimit()
    configureRouting()
    configureStatusPages()
    configureRequestValidation()
}
