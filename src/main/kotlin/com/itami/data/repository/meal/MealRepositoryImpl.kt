package com.itami.data.repository.meal

import com.itami.data.database.DatabaseFactory.dbQuery
import com.itami.data.database.entity.MealEntity
import com.itami.data.database.table.Meals
import com.itami.data.mapper.toMeal
import com.itami.data.model.Meal
import org.jetbrains.exposed.sql.SortOrder

class MealRepositoryImpl: MealRepository {

    override suspend fun getMealsForUser(userId: Int): List<Meal> {
        return dbQuery {
            MealEntity.find { Meals.userId eq userId }
                .orderBy(Meals.createdAt to SortOrder.ASC)
                .map { it.toMeal() }
        }
    }
}