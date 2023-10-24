package com.itami.domain.model

import java.time.LocalDateTime

data class Meal(
    val id: Int,
    val name: String,
    val user: User,
    val consumedFoods: List<ConsumedFood>,
    val createdAt: LocalDateTime,
)