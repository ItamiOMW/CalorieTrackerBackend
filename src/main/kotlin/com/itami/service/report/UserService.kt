package com.itami.service.report

import com.itami.data.dto.request.AddWeightRequest
import com.itami.data.dto.request.EditWeightRequest
import com.itami.data.dto.response.WeightResponse

interface UserService {

    suspend fun getWeights(userId: Int): List<WeightResponse>

    suspend fun addWeight(userId: Int, addWeightRequest: AddWeightRequest): WeightResponse

    suspend fun editWeight(userId: Int, weightId: Int, editWeightRequest: EditWeightRequest): WeightResponse

}