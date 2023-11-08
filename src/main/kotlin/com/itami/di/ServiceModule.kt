package com.itami.di

import com.itami.service.auth.AuthService
import com.itami.service.auth.AuthServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single<AuthService> { AuthServiceImpl(get(), ) }
}