package com.itami.di

import com.itami.service.auth.AuthService
import com.itami.service.auth.AuthServiceImpl
import com.itami.service.food.FoodService
import com.itami.service.food.FoodServiceImpl
import com.itami.service.meal.MealService
import com.itami.service.meal.MealServiceImpl
import com.itami.service.recipe.RecipeService
import com.itami.service.recipe.RecipeServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single<AuthService> { AuthServiceImpl(get()) }
    single<MealService> { MealServiceImpl(get()) }
    single<FoodService> { FoodServiceImpl(get()) }
    single<RecipeService> { RecipeServiceImpl(get()) }
}