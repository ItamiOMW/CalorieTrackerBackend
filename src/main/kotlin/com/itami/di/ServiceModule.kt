package com.itami.di

import com.itami.service.auth.AuthService
import com.itami.service.auth.AuthServiceImpl
import com.itami.service.feedback.FeedbackService
import com.itami.service.feedback.FeedbackServiceImpl
import com.itami.service.food.FoodService
import com.itami.service.food.FoodServiceImpl
import com.itami.service.meal.MealService
import com.itami.service.meal.MealServiceImpl
import com.itami.service.recipe.RecipeService
import com.itami.service.recipe.RecipeServiceImpl
import com.itami.service.user.UserService
import com.itami.service.user.UserServiceImpl
import org.koin.dsl.module

val serviceModule = module {
    single<AuthService> { AuthServiceImpl(get(), get()) }
    single<MealService> { MealServiceImpl(get()) }
    single<FoodService> { FoodServiceImpl(get()) }
    single<RecipeService> { RecipeServiceImpl(get()) }
    single<UserService> { UserServiceImpl(get(), get()) }
    single<FeedbackService> { FeedbackServiceImpl(get()) }
}