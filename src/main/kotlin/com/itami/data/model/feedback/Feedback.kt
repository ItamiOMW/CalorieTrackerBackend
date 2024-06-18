package com.itami.data.model.feedback

import com.itami.data.model.user.User
import java.time.LocalDateTime

data class Feedback(
    val user: User,
    val message: String,
    val datetime: LocalDateTime,
)
