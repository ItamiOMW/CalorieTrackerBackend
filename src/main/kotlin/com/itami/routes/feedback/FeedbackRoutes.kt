package com.itami.routes.feedback

import com.itami.API_VERSION
import com.itami.data.dto.request.FeedbackMessageRequest
import com.itami.plugins.JWT_AUTH
import com.itami.service.feedback.FeedbackService
import com.itami.utils.userId
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

private const val FEEDBACK = "$API_VERSION/feedback"

fun Route.feedback(
    feedbackService: FeedbackService,
) {
    authenticate(JWT_AUTH) {
        post(FEEDBACK) {
            val userId = context.userId()
            val feedbackRequest = call.receive<FeedbackMessageRequest>()
            feedbackService.sendFeedback(userId, feedbackRequest)
            call.respond(HttpStatusCode.OK, "Feedback has been sent successfully.")
        }
    }
}