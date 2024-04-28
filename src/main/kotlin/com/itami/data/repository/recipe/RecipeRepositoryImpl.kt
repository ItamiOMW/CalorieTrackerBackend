package com.itami.data.repository.recipe

import com.itami.data.database.DatabaseFactory.dbQuery
import com.itami.data.database.entity.RecipeEntity
import com.itami.data.database.table.Recipes
import com.itami.data.mapper.toRecipe
import com.itami.data.model.recipe.Recipe
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.trim

class RecipeRepositoryImpl : RecipeRepository {

    override suspend fun getRecipeByQuery(
        query: String,
        page: Int,
        pageSize: Int,
        caloriesPerServingFrom: Int,
        caloriesPerServingTo: Int,
        timeCookingFromMin: Int,
        timeCookingToMin: Int
    ): List<Recipe> {
        return dbQuery {
            RecipeEntity
                .find {
                    (Recipes.name.trim().lowerCase().like("%$query%")) and
                            (Recipes.caloriesPerServing.between(caloriesPerServingFrom - 1, caloriesPerServingTo + 1)) and
                            (Recipes.timeMinutes.between(timeCookingFromMin - 1, timeCookingToMin + 1))
                }
                .limit(n = pageSize, offset = ((page - 1) * pageSize).toLong())
                .map { it.toRecipe() }
        }
    }

    override suspend fun getRecipeById(recipeId: Int): Recipe? {
        return dbQuery {
            RecipeEntity.findById(recipeId)?.toRecipe()
        }
    }

}