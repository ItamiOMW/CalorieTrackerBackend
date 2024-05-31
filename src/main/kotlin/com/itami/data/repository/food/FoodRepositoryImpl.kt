package com.itami.data.repository.food

import com.itami.data.database.exposed.DatabaseFactory.dbQuery
import com.itami.data.database.exposed.entity.FoodEntity
import com.itami.data.database.exposed.table.Foods
import com.itami.data.mapper.toFood
import com.itami.data.model.meal.Food
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.trim

class FoodRepositoryImpl : FoodRepository {

    override suspend fun getFoodByQuery(query: String, page: Int, pageSize: Int): List<Food> {
        return dbQuery {
            FoodEntity
                .find {
                    Foods.name
                        .trim()
                        .lowerCase()
                        .like("%$query%")
                }
                .limit(n = pageSize, offset = ((page - 1) * pageSize).toLong())
                .map { it.toFood() }
        }
    }

    override suspend fun getFoodsByIds(ids: List<Int>): List<Food> {
        TODO("Not yet implemented")
    }

}