package com.itami.data.dto.request

import com.itami.data.model.user.Gender
import com.itami.data.model.user.Lifestyle
import com.itami.data.model.user.WeightGoal
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserRequest(
    val name: String? = null,
    val age: Int? = null,
    val heightCm: Int? = null,
    val weightGrams: Int? = null,
    val lifestyle: Lifestyle? = null,
    val gender: Gender? = null,
    val weightGoal: WeightGoal? = null,
    val caloriesGoal: Int? = null,
    val proteinsGoal: Int? = null,
    val fatsGoal: Int? = null,
    val carbsGoal: Int? = null,
    val waterMlGoal: Int? = null,
)
