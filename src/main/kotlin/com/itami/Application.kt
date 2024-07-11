package com.itami

import com.itami.data.database.exposed.DatabaseFactory
import com.itami.data.database.firebase.FirebaseAdmin
import com.itami.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.time.Duration.Companion.minutes

const val API_VERSION = "/api/v1"

fun main(args: Array<String>) {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    val config = environment.config
    DatabaseFactory.init(config)
    FirebaseAdmin.init()
    launchDatabaseCleanupJob()

    configureKoin()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
    configureRateLimit()
    configureRouting()
    configureStatusPages()
    configureRequestValidation()
}

fun Application.launchDatabaseCleanupJob(): Job {
    return CoroutineScope(Dispatchers.IO).launch {
        while (true) {
            DatabaseFactory.cleanupDatabase()
            delay(15.minutes)
        }
    }
}