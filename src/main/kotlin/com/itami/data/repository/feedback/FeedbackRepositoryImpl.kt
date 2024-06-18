package com.itami.data.repository.feedback

import com.itami.data.database.exposed.DatabaseFactory.dbQuery
import com.itami.data.database.exposed.entity.FeedbackEntity
import com.itami.data.database.exposed.table.Feedbacks
import com.itami.data.mapper.toFeedback
import com.itami.data.model.feedback.Feedback
import com.itami.utils.DateTimeUtil
import org.jetbrains.exposed.sql.insertAndGetId

class FeedbackRepositoryImpl : FeedbackRepository {

    override suspend fun saveFeedback(userId: Int, message: String): Feedback {
        return dbQuery {
            val id = Feedbacks.insertAndGetId { table ->
                table[Feedbacks.userId] = userId
                table[Feedbacks.message] = message
                table[Feedbacks.datetime] = DateTimeUtil.currentDateTime()
            }
            FeedbackEntity[id].toFeedback()
        }
    }

}