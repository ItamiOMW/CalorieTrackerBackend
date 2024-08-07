package com.itami.data.repository.recipe

import com.itami.data.database.exposed.DatabaseFactory.dbQuery
import com.itami.data.database.exposed.table.RecipeTranslations
import com.itami.data.database.exposed.table.Recipes
import com.itami.data.model.recipe.CaloriesFilter
import com.itami.data.model.recipe.Recipe
import com.itami.data.model.recipe.TimeFilter
import org.jetbrains.exposed.sql.*

class RecipeRepositoryImpl : RecipeRepository {

    override suspend fun getRecipeByQuery(
        query: String,
        page: Int,
        pageSize: Int,
        languageCode: String,
        timeFilters: List<TimeFilter>?,
        caloriesFilters: List<CaloriesFilter>?,
    ): List<Recipe> {
        return dbQuery {
            Recipes
                .leftJoin(
                    otherTable = RecipeTranslations,
                    onColumn = { Recipes.id },
                    otherColumn = { RecipeTranslations.recipeId }) { RecipeTranslations.languageCode eq languageCode }
                .select {
                    val caloriesCondition = Op.build {
                        if (!caloriesFilters.isNullOrEmpty()) {
                            caloriesFilters.map { range ->
                                when (range) {
                                    CaloriesFilter.LessThan100Cal -> (Recipes.caloriesPerServing greaterEq 0) and (Recipes.caloriesPerServing lessEq 100)
                                    CaloriesFilter.Between100And250Cal -> (Recipes.caloriesPerServing greaterEq 100) and (Recipes.caloriesPerServing lessEq 250)
                                    CaloriesFilter.Between250And500Cal -> (Recipes.caloriesPerServing greaterEq 250) and (Recipes.caloriesPerServing lessEq 500)
                                    CaloriesFilter.MoreThan500Cal -> Recipes.caloriesPerServing greaterEq 500
                                }
                            }.reduce { acc, condition -> acc or condition }
                        } else {
                            Op.TRUE
                        }
                    }

                    val timeCondition = Op.build {
                        if (!timeFilters.isNullOrEmpty()) {
                            timeFilters.map { range ->
                                when (range) {
                                    TimeFilter.LessThan15Min -> (Recipes.timeMinutes greaterEq 0) and (Recipes.timeMinutes lessEq 15)
                                    TimeFilter.Between15And30Min -> (Recipes.timeMinutes greaterEq 15) and (Recipes.timeMinutes lessEq 30)
                                    TimeFilter.Between30And60Min -> (Recipes.timeMinutes greaterEq 30) and (Recipes.timeMinutes lessEq 60)
                                    TimeFilter.MoreThan60Min -> Recipes.timeMinutes greaterEq 60
                                }
                            }.reduce { acc, condition -> acc or condition }
                        } else {
                            Op.TRUE
                        }
                    }

                    val nameMatchCondition = Op.build {
                        (RecipeTranslations.translatedName.trim().lowerCase().like("%$query%")) or
                                (Recipes.name.trim().lowerCase().like("%$query%"))
                    }

                    caloriesCondition and timeCondition and nameMatchCondition
                }.limit(n = pageSize, offset = ((page - 1) * pageSize).toLong())
                .map {
                    Recipe(
                        id = it[Recipes.id].value,
                        name = it.getOrNull(RecipeTranslations.translatedName) ?: it[Recipes.name],
                        recipeText = it.getOrNull(RecipeTranslations.translatedRecipeText) ?: it[Recipes.recipeText],
                        caloriePerServing = it[Recipes.caloriesPerServing],
                        proteinsPerServing = it[Recipes.proteinsPerServing],
                        fatsPerServing = it[Recipes.fatsPerServing],
                        carbsPerServing = it[Recipes.carbsPerServing],
                        timeMinutes = it[Recipes.timeMinutes],
                        imageUrl = it[Recipes.imageUrl]
                    )
                }
        }
    }

    override suspend fun getRecipeById(
        recipeId: Int,
        languageCode: String
    ): Recipe? {
        return dbQuery {
            Recipes
                .leftJoin(
                    otherTable = RecipeTranslations,
                    onColumn = { Recipes.id },
                    otherColumn = { RecipeTranslations.recipeId }) { RecipeTranslations.languageCode eq languageCode }
                .select { Recipes.id eq recipeId }
                .firstOrNull()
                ?.let {
                    Recipe(
                        id = it[Recipes.id].value,
                        name = it.getOrNull(RecipeTranslations.translatedName) ?: it[Recipes.name],
                        recipeText = it.getOrNull(RecipeTranslations.translatedRecipeText) ?: it[Recipes.recipeText],
                        caloriePerServing = it[Recipes.caloriesPerServing],
                        proteinsPerServing = it[Recipes.proteinsPerServing],
                        fatsPerServing = it[Recipes.fatsPerServing],
                        carbsPerServing = it[Recipes.carbsPerServing],
                        timeMinutes = it[Recipes.timeMinutes],
                        imageUrl = it[Recipes.imageUrl]
                    )
                }
        }
    }

}