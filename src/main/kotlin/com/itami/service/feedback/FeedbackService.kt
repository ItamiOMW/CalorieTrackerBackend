package com.itami.service.feedback

import com.itami.data.dto.request.FeedbackMessageRequest

interface FeedbackService {

    suspend fun sendFeedback(
        userId: Int,
        feedbackMessageRequest: FeedbackMessageRequest
    )

}