package com.itami.service.feedback

import com.itami.data.dto.request.FeedbackMessageRequest
import com.itami.data.repository.feedback.FeedbackRepository

class FeedbackServiceImpl(
    private val feedbackRepository: FeedbackRepository
) : FeedbackService {

    override suspend fun sendFeedback(
        userId: Int,
        feedbackMessageRequest: FeedbackMessageRequest
    ) {
        feedbackRepository.saveFeedback(
            userId = userId,
            message = feedbackMessageRequest.message
        )
    }

}