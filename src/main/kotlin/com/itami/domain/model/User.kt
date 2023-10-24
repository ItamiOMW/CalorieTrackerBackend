package com.itami.domain.model

import java.time.LocalDateTime

data class User(
    val id: Int,
    val googleId: String?,
    val email: String,
    val name: String,
    val profilePictureUrl: String?,
    val createdAt: LocalDateTime,
    val age: Int,
    val heightCm: Int,
    val weightGrams: Int,
    val gender: Gender,
    val weightGoal: WeightGoal,
    val lifestyle: Lifestyle,
    val dailyCalories: Int,
    val dailyProteins: Int,
    val dailyFats: Int,
    val dailyCarbs: Int,
)
