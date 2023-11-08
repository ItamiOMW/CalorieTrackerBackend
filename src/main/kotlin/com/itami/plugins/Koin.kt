package com.itami.plugins

import com.itami.di.mainModule
import com.itami.di.repositoryModule
import com.itami.di.serviceModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(mainModule, serviceModule, repositoryModule)
    }
}