package com.itami.data.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object Recipes: IntIdTable("recipes") {
    val name = varchar("name", 100)
    val cookingText = varchar("cooking_text", 1024)
    val cookingTimeMin = integer("cooking_time_min")
    val calories = integer("calories")
    val proteins = integer("proteins")
    val fats = integer("fats")
    val carbs = integer("carbs")
    val foodPictureUrl = varchar("food_picture_url", 255).nullable()
}