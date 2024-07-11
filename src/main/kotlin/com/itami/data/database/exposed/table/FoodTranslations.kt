package com.itami.data.database.exposed.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object FoodTranslations: IntIdTable("foods_translations") {
    val foodId = reference("food_id", Foods.id, onDelete = ReferenceOption.CASCADE)
    val languageCode = varchar("language_code", 2)
    val translatedName = varchar("translated_name", 100)
}