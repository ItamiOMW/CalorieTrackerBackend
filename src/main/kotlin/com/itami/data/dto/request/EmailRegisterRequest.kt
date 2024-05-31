package com.itami.data.dto.request

import com.itami.data.model.user.Gender
import com.itami.data.model.user.Lifestyle
import com.itami.data.model.user.WeightGoal
import kotlinx.serialization.Serializable

@Serializable
data class EmailRegisterRequest(
    val email: String,
    val password: String,
    val name: String,
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
    val waterMl: Int
)
