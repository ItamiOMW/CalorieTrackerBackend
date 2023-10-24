package com.itami.di

import com.itami.data.repository.MealRepositoryImpl
import com.itami.data.repository.UserRepositoryImpl
import com.itami.domain.repository.MealRepository
import com.itami.domain.repository.UserRepository
import org.koin.dsl.module

val mainModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<MealRepository> { MealRepositoryImpl() }
}