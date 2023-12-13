package com.itami.data.dto.response

import com.itami.data.model.user.Gender
import com.itami.data.model.user.Lifestyle
import com.itami.data.model.user.WeightGoal
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val id: Int,
    val email: String,
    val name: String,
    val profilePictureUrl: String?,
    val createdAt: String,
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
