package com.itami.data.repository.meal

import com.itami.data.model.meal.*
import java.time.LocalDate
import java.time.LocalDateTime

interface MealRepository {

    suspend fun getMealsForUser(userId: Int): List<Meal>

    suspend fun getMealsByDate(userId: Int, date: LocalDate): List<Meal>

    suspend fun getMealById(mealId: Int): Meal?

    suspend fun createMeal(createMeal: CreateMeal): Meal?

    suspend fun updateMeal(mealId: Int, updateMeal: UpdateMeal): Meal?

    suspend fun deleteMeal(mealId: Int)

    suspend fun getConsumedWater(userId: Int, date: LocalDate): ConsumedWater?

    suspend fun createConsumedWater(userId: Int, timestamp: LocalDateTime, waterMl: Int): ConsumedWater?

    suspend fun updateConsumedWater(userId: Int, timestamp: LocalDateTime, waterMl: Int): ConsumedWater?

}