package com.itami.service.user

import com.google.cloud.storage.Bucket
import com.itami.data.database.firebase.FirebaseStorageUrl
import com.itami.data.database.firebase.FirebaseStorageUrl.getDownloadUrl
import com.itami.data.database.firebase.FirebaseStorageUrl.reference
import com.itami.data.dto.request.AddWeightRequest
import com.itami.data.dto.request.EditWeightRequest
import com.itami.data.dto.request.UpdateUserRequest
import com.itami.data.dto.response.UserResponse
import com.itami.data.dto.response.WeightResponse
import com.itami.data.mapper.toUpdateUser
import com.itami.data.mapper.toUserResponse
import com.itami.data.mapper.toWeightResponse
import com.itami.data.repository.user.UserRepository
import com.itami.utils.AppException
import com.itami.utils.DateTimeUtil
import io.ktor.util.*

class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bucket: Bucket
) : UserService {

    override suspend fun updateUser(
        userId: Int,
        updateUserRequest: UpdateUserRequest,
        profilePictureName: String?,
        profilePictureByteArray: ByteArray?
    ): UserResponse {
        val userById = userRepository.getUserById(userId)
            ?: throw AppException.UnauthorizedException("User does not exist.")

        if (userById.profilePictureUrl != null && profilePictureByteArray != null) {
            try {
                val fileName = FirebaseStorageUrl.extractFileNameFromUrl(userById.profilePictureUrl)
                val blob = bucket.get(fileName)
                blob.delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val profilePictureUrl = if (profilePictureName != null && profilePictureByteArray != null) {
            bucket.create(
                "${FirebaseStorageUrl.PROFILE_PICTURES_PATH}/$profilePictureName",
                profilePictureByteArray,
                "image/png"
            )
            FirebaseStorageUrl.BASE_PATH reference FirebaseStorageUrl.PROFILE_PICTURES_PATH getDownloadUrl profilePictureName
        } else {
            null
        }

        if (updateUserRequest.weightGrams != null && userById.weightGrams != updateUserRequest.weightGrams) {
            userRepository.addWeight(userId, updateUserRequest.weightGrams, DateTimeUtil.currentDateTime())
        }

        val updateUser = userById.copy(
            name = updateUserRequest.name ?: userById.name,
            profilePictureUrl = profilePictureUrl ?: userById.profilePictureUrl,
            age = updateUserRequest.age ?: userById.age,
            heightCm = updateUserRequest.heightCm ?: userById.heightCm,
            gender = updateUserRequest.gender ?: userById.gender,
            weightGoal = updateUserRequest.weightGoal ?: userById.weightGoal,
            lifestyle = updateUserRequest.lifestyle ?: userById.lifestyle,
            dailyCalories = updateUserRequest.caloriesGoal ?: userById.dailyCalories,
            dailyProteins = updateUserRequest.proteinsGoal ?: userById.dailyProteins,
            dailyFats = updateUserRequest.fatsGoal ?: userById.dailyFats,
            dailyCarbs = updateUserRequest.carbsGoal ?: userById.dailyCarbs,
            waterMl = updateUserRequest.waterMlGoal ?: userById.waterMl,
        ).toUpdateUser()

        val updatedUser = userRepository.updateUser(userId = userId, updateUser)
        return updatedUser.toUserResponse()
    }

    override suspend fun deleteUser(userId: Int) {
        val userById = userRepository.getUserById(userId)
            ?: throw AppException.UnauthorizedException("User does not exist.")

        if (userById.profilePictureUrl != null) {
            try {
                val fileName = FirebaseStorageUrl.extractFileNameFromUrl(userById.profilePictureUrl)
                val blob = bucket.get(fileName)
                blob.delete()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        userRepository.deleteUser(userId)
    }

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