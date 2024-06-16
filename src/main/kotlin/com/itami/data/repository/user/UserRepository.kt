package com.itami.data.repository.user

import com.itami.data.model.user.*
import java.time.LocalDate
import java.time.LocalDateTime

interface UserRepository {

    suspend fun getUserById(userId: Int): User?

    suspend fun getUserByGoogleId(googleId: String): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun createUserEmail(createUserEmail: CreateUserEmail): User

    suspend fun createUserGoogle(createUserGoogle: CreateUserGoogle): User

    suspend fun updateUser(userId: Int, updateUser: UpdateUser): User

    suspend fun getActivationToken(token: String): ActivationToken?

    suspend fun saveActivationToken(activationToken: ActivationToken): ActivationToken

    suspend fun deleteActivationToken(token: String)

    suspend fun getPasswordResetCode(email: String, code: Int): PasswordResetCode?

    suspend fun savePasswordResetCode(passwordResetCode: PasswordResetCode): PasswordResetCode

    suspend fun deletePasswordResetCode(code: Int, email: String)

    suspend fun deleteUser(userId: Int)

    suspend fun addWeight(userId: Int, weightGrams: Int, datetime: LocalDateTime): Weight

    suspend fun editWeight(userId: Int, weightId: Int, weightGrams: Int): Weight

    suspend fun getWeights(userId: Int): List<Weight>

    suspend fun getWeightByDate(userId: Int, date: LocalDate): Weight?

    suspend fun getWeightsBetweenDates(
        userId: Int,
        startDate: LocalDate,
        endDate: LocalDate
    ): List<Weight>

}