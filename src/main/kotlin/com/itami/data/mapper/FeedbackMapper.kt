package com.itami.data.mapper

import com.itami.data.database.exposed.entity.FeedbackEntity
import com.itami.data.model.feedback.Feedback

fun FeedbackEntity.toFeedback() = Feedback(
    user = this.user.toUser(),
    message = this.message,
    datetime = this.datetime
)