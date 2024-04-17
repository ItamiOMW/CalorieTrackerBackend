package com.itami.data.database.entity

import com.itami.data.database.table.Recipes
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RecipeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeEntity>(Recipes)

    var name by Recipes.name
    var recipeText by Recipes.recipeText
    var caloriesPerServing by Recipes.caloriesPerServing
    var proteinsPerServing by Recipes.proteinsPerServing
    var fatsPerServing by Recipes.fatsPerServing
    var carbsPerServing by Recipes.carbsPerServing
    var timeMinutes by Recipes.timeMinutes
    var imageUrl by Recipes.imageUrl
}