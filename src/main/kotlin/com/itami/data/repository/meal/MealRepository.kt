package com.itami.data.repository.meal

import com.itami.data.model.meal.ConsumedWater
import com.itami.data.model.meal.CreateMeal
import com.itami.data.model.meal.Meal
import com.itami.data.model.meal.UpdateMeal
import java.time.LocalDate
import java.time.LocalDateTime

interface MealRepository {

    suspend fun getMealsForUser(
        userId: Int,
        languageCode: String
    ): List<Meal>

    suspend fun getMealsByDate(
        userId: Int,
        date: LocalDate,
        languageCode: String
    ): List<Meal>

    suspend fun getMealById(
        mealId: Int,
        languageCode: String
    ): Meal?

    suspend fun getMealById(
        mealId: Int,
    ): Meal?

    suspend fun createMeal(
        createMeal: CreateMeal,
        languageCode: String
    ): Meal?

    suspend fun updateMeal(
        mealId: Int,
        updateMeal: UpdateMeal,
        languageCode: String
    ): Meal?

    suspend fun deleteMeal(mealId: Int)

    suspend fun getConsumedWater(userId: Int, date: LocalDate): ConsumedWater?

    suspend fun createConsumedWater(userId: Int, timestamp: LocalDateTime, waterMl: Int): ConsumedWater?

    suspend fun updateConsumedWater(userId: Int, timestamp: LocalDateTime, waterMl: Int): ConsumedWater?

}