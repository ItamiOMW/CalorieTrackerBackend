package com.itami.data.repository.meal

import com.itami.data.model.Meal

interface MealRepository {

    suspend fun getMealsForUser(userId: Int): List<Meal>

}