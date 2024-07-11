package com.itami.data.repository.food

import com.itami.data.database.exposed.DatabaseFactory.dbQuery
import com.itami.data.database.exposed.table.FoodTranslations
import com.itami.data.database.exposed.table.Foods
import com.itami.data.model.meal.Food
import org.jetbrains.exposed.sql.*

class FoodRepositoryImpl : FoodRepository {

    override suspend fun getFoodByQuery(query: String, page: Int, pageSize: Int, languageCode: String): List<Food> {
        return dbQuery {
            Foods
                .leftJoin(
                    otherTable = FoodTranslations,
                    onColumn = { Foods.id },
                    otherColumn = { FoodTranslations.foodId }
                ) { FoodTranslations.languageCode eq languageCode }
                .select {
                    (FoodTranslations.translatedName.trim().lowerCase().like("%$query%")) or
                            (Foods.name.trim().lowerCase().like("%$query%"))
                }
                .limit(n = pageSize, offset = ((page - 1) * pageSize).toLong())
                .map {
                    Food(
                        id = it[Foods.id].value,
                        name = it.getOrNull(FoodTranslations.translatedName) ?: it[Foods.name],
                        caloriesIn100Grams = it[Foods.caloriesIn100Grams],
                        proteinsIn100Grams = it[Foods.proteinsIn100Grams],
                        fatsIn100Grams = it[Foods.fatsIn100Grams],
                        carbsIn100Grams = it[Foods.carbsIn100Grams]
                    )
                }
        }
    }

    override suspend fun getFoodsByIds(ids: List<Int>): List<Food> {
        TODO("Not yet implemented")
    }

}