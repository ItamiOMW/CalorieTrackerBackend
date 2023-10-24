package com.itami.data.database.entity

import com.itami.data.database.table.Recipes
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RecipeEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeEntity>(Recipes)

    var name by Recipes.name
    var cookingText by Recipes.cookingText
    var cookingTimeMin by Recipes.cookingTimeMin
    var calories by Recipes.calories
    var proteins by Recipes.proteins
    var fats by Recipes.fats
    var carbs by Recipes.carbs
    var foodPictureUrl by Recipes.foodPictureUrl
}