package com.itami.data.database.exposed.entity

import com.itami.data.database.exposed.table.RecipeTranslations
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class RecipeTranslationEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RecipeTranslationEntity>(RecipeTranslations)

    val recipe by RecipeEntity referencedOn RecipeTranslations.recipeId
    var recipeId by RecipeTranslations.recipeId
    var languageCode by RecipeTranslations.languageCode
    var translatedName by RecipeTranslations.translatedName
    var translatedRecipeText by RecipeTranslations.translatedRecipeText
}