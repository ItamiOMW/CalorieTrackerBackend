package com.itami.data.repository.meal

import com.itami.data.database.exposed.DatabaseFactory.dbQuery
import com.itami.data.database.exposed.entity.ConsumedWaterEntity
import com.itami.data.database.exposed.entity.MealEntity
import com.itami.data.database.exposed.table.ConsumedFoods
import com.itami.data.database.exposed.table.ConsumedWaters
import com.itami.data.database.exposed.table.Meals
import com.itami.data.mapper.toConsumedWater
import com.itami.data.mapper.toMeal
import com.itami.data.model.meal.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate
import java.time.LocalDateTime

class MealRepositoryImpl : MealRepository {

    override suspend fun getMealsForUser(userId: Int, languageCode: String): List<Meal> {
        return dbQuery {
            MealEntity.find { Meals.userId eq userId }
                .orderBy(Meals.createdAt to SortOrder.ASC)
                .map { it.toMeal(languageCode) }
        }
    }

    override suspend fun getMealsByDate(userId: Int, date: LocalDate, languageCode: String): List<Meal> {
        return dbQuery {
            MealEntity.find {
                (Meals.userId eq userId) and (Meals.createdAt.date() eq date)
            }.map { it.toMeal(languageCode) }
        }
    }

    override suspend fun getMealById(mealId: Int, languageCode: String): Meal? {
        return dbQuery {
            MealEntity.findById(id = mealId)?.toMeal(languageCode)
        }
    }

    override suspend fun getMealById(mealId: Int): Meal? {
        return dbQuery {
            MealEntity.findById(id = mealId)?.toMeal()
        }
    }

    override suspend fun createMeal(createMeal: CreateMeal, languageCode: String): Meal? {
        val mealId = dbQuery {
            Meals.insertAndGetId { table ->
                table[name] = createMeal.name
                table[userId] = createMeal.userId
                table[createdAt] = createMeal.createAt
            }.value
        }
        insertConsumedFoods(mealId = mealId, consumedFoods = createMeal.consumedFoods)
        return getMealById(mealId, languageCode)
    }

    override suspend fun updateMeal(mealId: Int, updateMeal: UpdateMeal, languageCode: String): Meal? {
        dbQuery {
            MealEntity.findById(id = mealId)?.apply {
                this.name = updateMeal.name
            }
            ConsumedFoods.deleteWhere { ConsumedFoods.mealId eq mealId }
        }
        updateConsumedFoods(mealId, updateMeal.consumedFoods)
        return getMealById(mealId, languageCode)
    }

    override suspend fun deleteMeal(mealId: Int) {
        dbQuery {
            MealEntity.findById(mealId)?.delete()
        }
    }

    override suspend fun getConsumedWater(userId: Int, date: LocalDate): ConsumedWater? {
        return dbQuery {
            ConsumedWaterEntity.find {
                (ConsumedWaters.userId eq userId) and (ConsumedWaters.timestamp.date() eq date)
            }.firstOrNull()?.toConsumedWater()
        }
    }

    override suspend fun createConsumedWater(userId: Int, timestamp: LocalDateTime, waterMl: Int): ConsumedWater? {
        dbQuery {
            ConsumedWaters.insert { table ->
                table[ConsumedWaters.waterMl] = waterMl
                table[ConsumedWaters.timestamp] = timestamp
                table[ConsumedWaters.userId] = userId
            }
        }
        return getConsumedWater(userId, timestamp.toLocalDate())
    }

    override suspend fun updateConsumedWater(userId: Int, timestamp: LocalDateTime, waterMl: Int): ConsumedWater? {
        return dbQuery {
            ConsumedWaterEntity.find {
                (ConsumedWaters.userId eq userId) and (ConsumedWaters.timestamp.date() eq timestamp.toLocalDate())
            }.firstOrNull()?.apply {
                this.waterMl = waterMl
                this.timestamp = timestamp
            }?.toConsumedWater()
        }
    }

    private suspend fun updateConsumedFoods(mealId: Int, consumedFoods: List<CreateConsumedFood>) {
        dbQuery {
            consumedFoods.forEach { consumedFood ->
                ConsumedFoods.insert { table ->
                    table[ConsumedFoods.mealId] = mealId
                    table[ConsumedFoods.foodId] = consumedFood.foodId
                    table[ConsumedFoods.grams] = consumedFood.grams
                }
            }
        }
    }

    private suspend fun insertConsumedFoods(mealId: Int, consumedFoods: List<CreateConsumedFood>) {
        dbQuery {
            consumedFoods.forEach { consumedFood ->
                ConsumedFoods.insert { table ->
                    table[ConsumedFoods.mealId] = mealId
                    table[ConsumedFoods.foodId] = consumedFood.foodId
                    table[ConsumedFoods.grams] = consumedFood.grams
                }
            }
        }
    }
}