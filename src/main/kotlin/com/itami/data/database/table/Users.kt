package com.itami.data.database.table

import com.itami.domain.model.Gender
import com.itami.domain.model.WeightGoal
import com.itami.domain.model.Lifestyle
import com.itami.utils.currentDateTime
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object Users : IntIdTable(name = "users") {
    val googleId = varchar("google_id", 255).nullable().uniqueIndex()
    val email = varchar("email", 255).uniqueIndex()
    val name = varchar("name", 100)
    val profilePictureUrl = varchar("profile_picture_url", 255).nullable()
    val age = integer("age")
    val weightGrams = integer("weight_grams")
    val heightCm = integer("height_cm")
    val gender = enumerationByName("gender", 20, Gender::class)
    val weightGoal = enumerationByName("weight_goal", 20, WeightGoal::class)
    val lifestyle = enumerationByName("lifestyle", 20, Lifestyle::class)
    val dailyProteins = integer("daily_proteins")
    val dailyFats = integer("daily_fats")
    val dailyCarbs = integer("daily_carbs")
    val dailyCalories = integer("daily_calories")
    val createdAt = datetime("created_at").default(currentDateTime())
}