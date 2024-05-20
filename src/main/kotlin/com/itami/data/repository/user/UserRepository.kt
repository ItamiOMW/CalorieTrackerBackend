package com.itami.data.repository.user

import com.itami.data.model.user.CreateUser
import com.itami.data.model.user.User
import com.itami.data.model.user.Weight
import java.time.LocalDate
import java.time.LocalDateTime

interface UserRepository {

    suspend fun getUserById(userId: Int): User?

    suspend fun getUserByGoogleId(googleId: String): User?

    suspend fun getUserByEmail(email: String): User?

    suspend fun createUser(createUser: CreateUser): User

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