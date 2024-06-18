package com.itami.data.dto.request

import kotlinx.serialization.Serializable

@Serializable
data class FeedbackMessageRequest(
    val message: String
)
