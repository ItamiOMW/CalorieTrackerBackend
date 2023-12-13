package com.itami.di

import com.itami.data.repository.food.FoodRepository
import com.itami.data.repository.food.FoodRepositoryImpl
import com.itami.data.repository.meal.MealRepository
import com.itami.data.repository.meal.MealRepositoryImpl
import com.itami.data.repository.user.UserRepository
import com.itami.data.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<MealRepository> { MealRepositoryImpl() }
    single<FoodRepository> { FoodRepositoryImpl() }
}