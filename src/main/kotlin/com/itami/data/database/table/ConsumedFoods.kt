package com.itami.data.database.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object ConsumedFoods : IntIdTable("consumed_foods") {
    val mealId = reference("meal_id", Meals.id, onDelete = ReferenceOption.CASCADE)
    val foodId = reference("food_id", Foods.id, onDelete = ReferenceOption.CASCADE)
    val grams = integer("grams")
}