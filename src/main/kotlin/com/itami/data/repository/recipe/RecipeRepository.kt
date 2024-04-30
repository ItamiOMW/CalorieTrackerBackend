package com.itami.data.repository.recipe

import com.itami.data.model.recipe.CaloriesFilter
import com.itami.data.model.recipe.Recipe
import com.itami.data.model.recipe.TimeFilter

interface RecipeRepository {

    suspend fun getRecipeByQuery(
        query: String,
        page: Int,
        pageSize: Int,
        timeFilters: List<TimeFilter>?,
        caloriesFilters: List<CaloriesFilter>?,
    ): List<Recipe>

    suspend fun getRecipeById(recipeId: Int): Recipe?

}