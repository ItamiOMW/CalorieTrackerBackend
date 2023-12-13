package com.itami.data.mapper

import com.itami.data.database.entity.UserEntity
import com.itami.data.model.user.User
import com.itami.data.dto.response.UserResponse
import com.itami.utils.DateTimeUtil


fun UserEntity.toUser() = User(
    id = this.id.value,
    googleId = this.googleId,
    name = this.name,
    email = this.email,
    profilePictureUrl = this.profilePictureUrl,
    createdAt = this.createdAt,
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