package com.itami.service.meal

import com.itami.data.dto.request.AddConsumedWaterRequest
import com.itami.data.dto.request.CreateMealRequest
import com.itami.data.dto.request.RemoveConsumedWaterRequest
import com.itami.data.dto.request.UpdateMealRequest
import com.itami.data.dto.response.ConsumedWaterResponse
import com.itami.data.dto.response.MealResponse
import com.itami.data.dto.response.MealsWithConsumedWaterResponse

interface MealService {

    suspend fun getSummary(userId: Int, date: String): MealsWithConsumedWaterResponse

    suspend fun getMeal(userId: Int, mealId: Int): MealResponse

    suspend fun getMeals(userId: Int, date: String): List<MealResponse>

    suspend fun getMealById(userId: Int, mealId: Int): MealResponse

    suspend fun createMeal(userId: Int, createMealRequest: CreateMealRequest): MealResponse

    suspend fun updateMeal(userId: Int, mealId: Int, updateMealRequest: UpdateMealRequest): MealResponse

    suspend fun deleteMeal(userId: Int, mealId: Int)

    suspend fun addConsumedWater(userId: Int, addConsumedWaterRequest: AddConsumedWaterRequest): ConsumedWaterResponse

    suspend fun removeConsumedWater(userId: Int, removeConsumedWaterRequest: RemoveConsumedWaterRequest): ConsumedWaterResponse

}