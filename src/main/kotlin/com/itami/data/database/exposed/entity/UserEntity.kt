package com.itami.data.database.exposed.entity

import com.itami.data.database.exposed.table.Users
import com.itami.data.database.exposed.table.Weights
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var googleId by Users.googleId
    var email by Users.email
    var hashPassword by Users.hashPassword
    var name by Users.name
    var profilePictureUrl by Users.profilePictureUrl
    var age by Users.age
    val weights by WeightEntity referrersOn Weights.userId
    var heightCm by Users.heightCm
    var gender by Users.gender
    var weightGoal by Users.weightGoal
    var lifestyle by Users.lifestyle
    var dailyProteins by Users.dailyProteins
    var dailyFats by Users.dailyFats
    var dailyCarbs by Users.dailyCarbs
    var dailyCalories by Users.dailyCalories
    var waterMl by Users.waterMl
    var createdAt by Users.createdAt
    var isActive by Users.isActive
}