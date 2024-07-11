package com.itami.data.database.exposed.table

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object RecipeTranslations : IntIdTable(name = "recipes_translations") {
    val recipeId = reference("recipe_id", Recipes.id, onDelete = ReferenceOption.CASCADE)
    val languageCode = varchar("language_code", 2)
    val translatedName = varchar("translated_name", 255)
    val translatedRecipeText = largeText("translated_recipe_text")
}