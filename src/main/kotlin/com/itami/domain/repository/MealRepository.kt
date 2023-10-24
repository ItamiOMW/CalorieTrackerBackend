package com.itami.domain.repository

import com.itami.domain.model.Meal

interface MealRepository {

    suspend fun getMealsForUser(userId: Int): List<Meal>

}