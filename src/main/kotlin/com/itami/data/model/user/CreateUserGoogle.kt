package com.itami.data.model.user

data class CreateUserGoogle(
    val googleId: String?,
    val email: String,
    val name: String,
    val profilePictureUrl: String?,
    val age: Int,
    val heightCm: Int,
    val weightGrams: Int,
    val lifestyle: Lifestyle,
    val gender: Gender,
    val weightGoal: WeightGoal,
    val dailyCalories: Int,
    val dailyProteins: Int,
    val dailyFats: Int,
    val dailyCarbs: Int,
    val waterMl: Int,
    val isActive: Boolean,
)
