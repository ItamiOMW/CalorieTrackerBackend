package com.itami.di

import com.itami.data.repository.feedback.FeedbackRepository
import com.itami.data.repository.feedback.FeedbackRepositoryImpl
import com.itami.data.repository.food.FoodRepository
import com.itami.data.repository.food.FoodRepositoryImpl
import com.itami.data.repository.meal.MealRepository
import com.itami.data.repository.meal.MealRepositoryImpl
import com.itami.data.repository.recipe.RecipeRepository
import com.itami.data.repository.recipe.RecipeRepositoryImpl
import com.itami.data.repository.user.UserRepository
import com.itami.data.repository.user.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<UserRepository> { UserRepositoryImpl() }
    single<MealRepository> { MealRepositoryImpl() }
    single<FoodRepository> { FoodRepositoryImpl() }
    single<RecipeRepository> { RecipeRepositoryImpl() }
    single<FeedbackRepository> { FeedbackRepositoryImpl() }
}