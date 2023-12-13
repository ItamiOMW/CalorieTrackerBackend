package com.itami

import com.itami.data.database.DatabaseFactory
import com.itami.plugins.*
import io.ktor.server.application.*
import java.util.TimeZone

const val API_VERSION = "/api/v1"

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
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
    configureRateLimit()
    configureRouting()
    configureStatusPages()
    configureRequestValidation()
}
