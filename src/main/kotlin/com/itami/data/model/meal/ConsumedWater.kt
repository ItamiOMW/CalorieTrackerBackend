package com.itami.data.model.meal

import com.itami.data.model.user.User
import java.time.LocalDateTime

data class ConsumedWater(
    val id: Int,
    val waterMl: Int,
    val timestamp: LocalDateTime,
    val user: User,
)