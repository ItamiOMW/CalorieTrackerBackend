package com.itami.data.database.exposed.table

import org.jetbrains.exposed.dao.id.IntIdTable

object Recipes: IntIdTable("recipes") {
    val name = varchar("name", 100)
    val recipeText = largeText("recipe_text")
    val timeMinutes = integer("time_minutes")
    val caloriesPerServing = integer("calories_per_serving")
    val proteinsPerServing = integer("proteins_per_serving")
    val fatsPerServing = integer("fats_per_serving")
    val carbsPerServing = integer("carbs_per_serving")
    val imageUrl = varchar("image_url", 512)
}