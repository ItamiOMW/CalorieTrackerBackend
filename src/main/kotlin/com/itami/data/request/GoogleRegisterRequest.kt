package com.itami.data.request

import com.itami.data.model.Gender
import com.itami.data.model.Lifestyle
import com.itami.data.model.WeightGoal
import kotlinx.serialization.Serializable

@Serializable
data class GoogleRegisterRequest(
    val googleIdToken: String,
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
