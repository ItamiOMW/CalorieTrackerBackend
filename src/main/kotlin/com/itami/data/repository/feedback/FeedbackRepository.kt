package com.itami.data.repository.feedback

import com.itami.data.model.feedback.Feedback

interface FeedbackRepository {

    suspend fun saveFeedback(userId: Int, message: String): Feedback

}