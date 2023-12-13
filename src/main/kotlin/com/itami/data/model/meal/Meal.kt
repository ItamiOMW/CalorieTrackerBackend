package com.itami.data.model.meal

import com.itami.data.model.user.User
import java.time.LocalDateTime

data class Meal(
    val id: Int,
    val name: String,
    val user: User,
    val consumedFoods: List<ConsumedFood>,
    val createdAt: LocalDateTime,
)
