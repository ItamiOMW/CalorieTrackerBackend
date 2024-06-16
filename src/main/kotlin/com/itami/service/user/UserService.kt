package com.itami.service.user

import com.itami.data.dto.request.AddWeightRequest
import com.itami.data.dto.request.EditWeightRequest
import com.itami.data.dto.request.UpdateUserRequest
import com.itami.data.dto.response.UserResponse
import com.itami.data.dto.response.WeightResponse

interface UserService {

    suspend fun updateUser(
        userId: Int,
        updateUserRequest: UpdateUserRequest,
        profilePictureName: String? = null,
        profilePictureByteArray: ByteArray? = null
    ): UserResponse

    suspend fun getWeights(userId: Int): List<WeightResponse>

    suspend fun addWeight(userId: Int, addWeightRequest: AddWeightRequest): WeightResponse

    suspend fun editWeight(userId: Int, weightId: Int, editWeightRequest: EditWeightRequest): WeightResponse

}