package com.itami.data.model.user

import java.time.LocalDateTime

data class Weight(
    val id: Int,
    val userId: Int,
    val weightGrams: Int,
    val datetime: LocalDateTime,
)
