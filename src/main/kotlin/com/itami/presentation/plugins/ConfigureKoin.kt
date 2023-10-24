package com.itami.presentation.plugins

import com.itami.di.mainModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin

fun Application.configureKoin() {
    install(Koin) {
        modules(mainModule)
    }
}