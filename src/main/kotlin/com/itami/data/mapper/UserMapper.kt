package com.itami.data.mapper

import com.itami.data.database.exposed.entity.UserEntity
import com.itami.data.dto.request.EmailRegisterRequest
import com.itami.data.dto.request.GoogleRegisterRequest
import com.itami.data.dto.response.UserResponse
import com.itami.data.model.user.CreateUserEmail
import com.itami.data.model.user.CreateUserGoogle
import com.itami.data.model.user.UpdateUser
import com.itami.data.model.user.User
import com.itami.utils.Constants
import com.itami.utils.DateTimeUtil
import com.itami.utils.PasswordUtil

fun UserEntity.toUser() = User(
    id = this.id.value,
    googleId = this.googleId,
    name = this.name,
    email = this.email,
    hashPassword = this.hashPassword,
    profilePictureUrl = this.profilePictureUrl,
    createdAt = this.createdAt,
    age = this.age,
    heightCm = this.heightCm,
    weightGrams = this.weights.lastOrNull()?.weightGrams ?: Constants.DEFAULT_WEIGHT_GRAMS,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    dailyCalories = this.dailyCalories,
    dailyProteins = this.dailyProteins,
    dailyFats = this.dailyFats,
    dailyCarbs = this.dailyCarbs,
    waterMl = this.waterMl,
    isActive = this.isActive,
)

fun User.toUserResponse() = UserResponse(
    id = this.id,
    name = this.name,
    email = this.email,
    profilePictureUrl = this.profilePictureUrl,
    createdAt = DateTimeUtil.datetimeToString(this.createdAt),
    age = this.age,
    heightCm = this.heightCm,
    weightGrams = this.weightGrams,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    dailyCalories = this.dailyCalories,
    dailyProteins = this.dailyProteins,
    dailyFats = this.dailyFats,
    dailyCarbs = this.dailyCarbs,
    waterMl = this.waterMl
)

fun EmailRegisterRequest.toCreateUserEmail(profilePictureUrl: String?, isActive: Boolean) = CreateUserEmail(
    name = this.name,
    profilePictureUrl = profilePictureUrl,
    email = this.email,
    hashPassword = PasswordUtil.hashPassword(this.password),
    age = this.age,
    heightCm = this.heightCm,
    weightGrams = this.weightGrams,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    dailyCalories = this.dailyCalories,
    dailyProteins = this.dailyProteins,
    dailyFats = this.dailyFats,
    dailyCarbs = this.dailyCarbs,
    waterMl = this.waterMl,
    isActive = isActive
)

fun GoogleRegisterRequest.toCreateUserGoogle(
    googleId: String,
    email: String,
    name: String,
    profilePictureUrl: String?,
    isActive: Boolean
) = CreateUserGoogle(
    googleId = googleId,
    email = email,
    name = name,
    profilePictureUrl = profilePictureUrl,
    age = this.age,
    heightCm = this.heightCm,
    weightGoal = this.weightGoal,
    weightGrams = this.weightGrams,
    lifestyle = this.lifestyle,
    gender = this.gender,
    dailyCalories = this.dailyCalories,
    dailyProteins = this.dailyProteins,
    dailyFats = this.dailyFats,
    dailyCarbs = this.dailyCarbs,
    waterMl = this.waterMl,
    isActive = isActive
)

fun User.toUpdateUser() = UpdateUser(
    googleId = this.googleId,
    name = this.name,
    profilePictureUrl = profilePictureUrl,
    email = this.email,
    hashPassword = this.hashPassword,
    age = this.age,
    heightCm = this.heightCm,
    gender = this.gender,
    weightGoal = this.weightGoal,
    lifestyle = this.lifestyle,
    dailyCalories = this.dailyCalories,
    dailyProteins = this.dailyProteins,
    dailyFats = this.dailyFats,
    dailyCarbs = this.dailyCarbs,
    waterMl = this.waterMl,
    isActive = isActive
)