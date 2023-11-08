package com.itami.data.response

import com.itami.data.model.Gender
import com.itami.data.model.Lifestyle
import com.itami.data.model.WeightGoal
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val email: String,
    val name: String,
    val profilePictureUrl: String?,
    val createdAt: Long,
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
    val waterMl: Int,
)