package com.itami.data.database.table

import org.jetbrains.exposed.dao.id.IntIdTable

object Foods: IntIdTable("foods") {
    val name = varchar("name", 100)
    val caloriesIn100Grams = integer("calories_in_100g")
    val proteinsIn100Grams = integer("proteins_in_100g")
    val fatsIn100Grams = integer("fats_in_100g")
    val carbsIn100Grams = integer("carbs_in_100g")
}