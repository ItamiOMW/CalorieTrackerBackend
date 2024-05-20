package com.itami.service.report

import com.itami.data.dto.request.AddWeightRequest
import com.itami.data.dto.request.EditWeightRequest
import com.itami.data.dto.response.WeightResponse
import com.itami.data.mapper.toWeightResponse
import com.itami.data.repository.user.UserRepository
import com.itami.utils.DateTimeUtil
import io.ktor.util.*

class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override suspend fun getWeights(userId: Int): List<WeightResponse> {
        val weights = userRepository.getWeights(userId = userId)
        return weights.map { it.toWeightResponse() }
    }

    override suspend fun addWeight(userId: Int, addWeightRequest: AddWeightRequest): WeightResponse {
        val localDateTime = DateTimeUtil.stringToDateTime(addWeightRequest.encodedDatetime.decodeBase64String())
        val weightByDate = userRepository.getWeightByDate(userId = userId, date = localDateTime.toLocalDate())
        return if (weightByDate != null) {
            userRepository.editWeight(
                userId = userId,
                weightId = weightByDate.id,
                weightGrams = addWeightRequest.weightGrams
            ).toWeightResponse()
        } else {
            userRepository.addWeight(
                userId = userId,
                weightGrams = addWeightRequest.weightGrams,
                datetime = localDateTime,
            ).toWeightResponse()
        }
    }

    override suspend fun editWeight(userId: Int, weightId: Int, editWeightRequest: EditWeightRequest): WeightResponse {
        return userRepository.editWeight(
            userId = userId,
            weightId = weightId,
            weightGrams = editWeightRequest.weightGrams
        ).toWeightResponse()
    }

}