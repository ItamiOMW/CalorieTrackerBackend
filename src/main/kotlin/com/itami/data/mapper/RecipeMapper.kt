package com.itami.data.mapper

import com.itami.data.database.entity.RecipeEntity
import com.itami.data.database.table.Recipes
import com.itami.data.dto.response.RecipeResponse
import com.itami.data.model.recipe.Recipe
import org.jetbrains.exposed.sql.ResultRow

fun Recipe.toRecipeResponse() = RecipeResponse(
    id = this.id,
    name = this.name,
    recipeText = this.recipeText,
    caloriePerServing = this.caloriePerServing,
    proteinsPerServing = this.proteinsPerServing,
    fatsPerServing = this.fatsPerServing,
    carbsPerServing = this.carbsPerServing,
    timeMinutes = this.timeMinutes,
    imageUrl = this.imageUrl
)

fun RecipeEntity.toRecipe() = Recipe(
    id = this.id.value,
    name = this.name,
    recipeText = this.recipeText,
    caloriePerServing = this.caloriesPerServing,
    proteinsPerServing = this.proteinsPerServing,
    fatsPerServing = this.fatsPerServing,
    carbsPerServing = this.carbsPerServing,
    timeMinutes = this.timeMinutes,
    imageUrl = this.imageUrl
)

fun ResultRow.toRecipe() = Recipe(
    id = this[Recipes.id].value,
    name = this[Recipes.name],
    recipeText = this[Recipes.recipeText],
    caloriePerServing = this[Recipes.caloriesPerServing],
    proteinsPerServing = this[Recipes.proteinsPerServing],
    fatsPerServing = this[Recipes.fatsPerServing],
    carbsPerServing = this[Recipes.carbsPerServing],
    timeMinutes = this[Recipes.timeMinutes],
    imageUrl = this[Recipes.imageUrl]
)